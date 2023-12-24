package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.controller.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    // Add other methods like update, delete, etc., similar to the SalespersonService
    @Transactional
    public void updateProduct(Product updatedProduct) {
        Optional<Product> optionalExistingProduct = productRepository.findById(updatedProduct.getId());

        if (optionalExistingProduct.isPresent()) {
            Product existingProduct = optionalExistingProduct.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setSku(updatedProduct.getSku());
            existingProduct.setBarcode(updatedProduct.getBarcode());
            existingProduct.setImportPrice(updatedProduct.getImportPrice());
            existingProduct.setRetailPrice(updatedProduct.getRetailPrice());
            // Update other fields accordingly

            productRepository.save(existingProduct);
        }
    }

    @Transactional
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
