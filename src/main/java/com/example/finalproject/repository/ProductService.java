package com.example.finalproject.repository;

import com.example.finalproject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(@Autowired ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return (List<Product>) productRepository.findAll();
    }
    public Product getProductById(int id){
        return productRepository.findById(id).orElse(null);
    }
    public Product getProductByName(String name){
        return productRepository.findByName(name);
    }
    public void deleteProductById(int id){
        productRepository.deleteById(id);
    }
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getProductByStoreId(int storeId){
        return productRepository.findAllByInventories(storeId);
    }
}
