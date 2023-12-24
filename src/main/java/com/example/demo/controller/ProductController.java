package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.controller.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String productPage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "Product/product_manager";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("newProduct", new Product());
        return "Product/add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(null));
        return "Product/product_detail";
    }

    // Add other methods like update, delete, etc., similar to the SalespersonController
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable int id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(null));
        return "Product/edit_product";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable int id, @ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "Product/delete_product_confirmation";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, @RequestParam("confirm") boolean confirm, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (confirm) {
                productService.deleteProductById(product.getId());
                model.addAttribute("confirmationMessage", "Product deleted successfully.");
            } else {
                model.addAttribute("confirmationMessage", "Delete canceled.");
            }
        }

        return "redirect:/product";
    }
}
