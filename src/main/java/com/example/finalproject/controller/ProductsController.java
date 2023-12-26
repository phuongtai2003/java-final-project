package com.example.finalproject.controller;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import com.example.finalproject.utils.BarcodeService;
import com.example.finalproject.utils.ResponseData;
import com.google.zxing.MultiFormatWriter;
import org.springframework.http.HttpHeaders;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.example.finalproject.model.request.CreateProductRequest;
import com.google.zxing.common.BitMatrix;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private ProductService productService;
    private CategoryService categoryService;
    private BarcodeService barcodeService;
    private Cloudinary cloudinary;
    private ProductImageService productImageService;
    private InventoryService inventoryService;
    private RetailStoreService retailStoreService;
    private TransactionDetailsService transactionDetailsService;

    public ProductsController(@Autowired TransactionDetailsService transactionDetailsService,@Autowired RetailStoreService retailStoreService,@Autowired ProductImageService productImageService,@Autowired InventoryService inventoryService,@Autowired Cloudinary cloudinary,@Autowired BarcodeService barcodeService,@Autowired CategoryService categoryService,@Autowired ProductService productService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.barcodeService = barcodeService;
        this.cloudinary = cloudinary;
        this.productImageService = productImageService;
        this.inventoryService = inventoryService;
        this.retailStoreService = retailStoreService;
        this.transactionDetailsService = transactionDetailsService;
    }
    @GetMapping("")
    public String showProducts() {
        return "products/products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "products/add";
    }

    @Transactional
    @PostMapping("/add")
    public String addProduct(CreateProductRequest productRequest,Model model) throws IOException, WriterException {
        System.out.println(productRequest);
        if (productRequest.getName().isEmpty() || productRequest.getDescription().isEmpty() || productRequest.getSku().isEmpty() || productRequest.getImportPrice() == null || productRequest.getRetailPrice() == null || productRequest.getCategories()==null || productRequest.getImages()==null) {
            model.addAttribute("errorMessage", "Please fill all fields");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("request", productRequest);
            return "products/add";
        }
        RetailStore rs = retailStoreService.getStoreById(productRequest.getStoreId());
        if(rs == null){
            model.addAttribute("errorMessage", "Store not found");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("request", productRequest);
            return "products/add";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        List<MultipartFile> imagesFile = productRequest.getImages();
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile image : imagesFile) {
            File uploadedFile = convertMultiPartToFile(image);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            String imageUrl = (String) uploadResult.get("secure_url");
            images.add(imageUrl);
        }

        Product product = new Product(-1, productRequest.getName(), productRequest.getDescription(), productRequest.getSku(), null, productRequest.getImportPrice(), productRequest.getRetailPrice(), formattedDate, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Product insertedProduct = productService.addProduct(product);
        File barcodeFile = barcodeService.generateBarcodeFile(String.valueOf(insertedProduct.getId()), "barcode_" + insertedProduct.getId() + ".png");
        Map uploadResult = cloudinary.uploader().upload(barcodeFile, ObjectUtils.emptyMap());
        insertedProduct.setBarcode((String) uploadResult.get("secure_url"));
        barcodeFile.delete();
        List<Category> categories = (List<Category>) categoryService.getCategoriesByIds(productRequest.getCategories());
        insertedProduct.setCategories(categories);
        for (String image : images) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image);
            productImage.setProduct(insertedProduct);
            productImageService.saveProductImage(productImage);
            insertedProduct.getImages().add(productImage);
        }
        Product res = productService.updateProduct(insertedProduct);
        inventoryService.save(new Inventory(-1, rs, res, 0));
        if(res == null){
            model.addAttribute("errorMessage", "Something went wrong");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("request", productRequest);
            return "products/add";
        }
        model.addAttribute("successMessage", "Product added successfully!");
        model.addAttribute("categories", categoryService.findAll());
        return "products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "products/add";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, CreateProductRequest productRequest, Model model) {
        if (id <= 0) {
            model.addAttribute("errorMessage", "Invalid Product ID");
            model.addAttribute("product", productService.getProductById(id));
            model.addAttribute("categories", categoryService.findAll());
            return "products/add";
        }

        if (productRequest.getName() == null || productRequest.getDescription().isEmpty() ||
                productRequest.getSku().isEmpty() || productRequest.getImportPrice() == null ||
                productRequest.getRetailPrice() == null ||
                productRequest.getCategories()==null || productRequest.getImages() == null) {
            model.addAttribute("errorMessage", "Product name and date created are required");
            model.addAttribute("product", productService.getProductById(id));
            model.addAttribute("categories", categoryService.findAll());
            return "products/add";
        }

        Product existingProduct = productService.getProductById(id);

        if (existingProduct == null) {
            model.addAttribute("errorMessage", "Product not found");
            model.addAttribute("categories", categoryService.findAll());
            return "products/add";
        }

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setSku(productRequest.getSku());
        existingProduct.setImportPrice(productRequest.getImportPrice());
        existingProduct.setRetailPrice(productRequest.getRetailPrice());
        List<Category> categories = (List<Category>) categoryService.getCategoriesByIds(productRequest.getCategories());
        existingProduct.setCategories(categories);
        List<MultipartFile> imagesFile = productRequest.getImages();
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile image : imagesFile) {
            try {
                File uploadedFile = convertMultiPartToFile(image);
                Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
                uploadedFile.delete();
                String imageUrl = (String) uploadResult.get("secure_url");
                images.add(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!images.isEmpty()){
            productImageService.deleteProductImageByProductId(id);
        }
        for (String image : images) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image);
            productImage.setProduct(existingProduct);
            productImageService.saveProductImage(productImage);
            existingProduct.getImages().add(productImage);
        }
        productService.updateProduct(existingProduct);
        model.addAttribute("successMessage", "Product updated successfully!");
        return "redirect:/products";
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseData deleteProduct(@PathVariable("id") int id, Model model) {

        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return new ResponseData("Product Not Found", null, false, 404, null , null);
        }

        List<TransactionDetail> transactionDetails = (List<TransactionDetail>) transactionDetailsService.getTransactionDetailsByProduct(id);
        if(!transactionDetails.isEmpty()){
            return new ResponseData("Product has already been sold", null, false, 404, null , null);
        }
        productImageService.deleteProductImageByProductId(id);
        Inventory inventory = inventoryService.getInventoryByProductId(existingProduct.getId());
        inventoryService.deleteInventoryByInventoryId(inventory.getId());
        productService.deleteProductById(id);
        return new ResponseData("Delete product success", null, true, 200, null , null);
    }

    @GetMapping("/{storeId}")
    @ResponseBody
    public ResponseData showProductsByStoreId(@PathVariable("storeId") int storeId) {
        List<Product> products = productService.getProductByStoreId(storeId);
        return new ResponseData("Success", products, true, 200, null, null);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

