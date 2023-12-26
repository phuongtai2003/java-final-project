package com.example.finalproject.repository;

import com.example.finalproject.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    public ProductImageService(@Autowired ProductImageRepository productImageRepository){
        this.productImageRepository = productImageRepository;
    }
    public ProductImage saveProductImage(ProductImage productImage){
        return productImageRepository.save(productImage);
    }

    public void deleteProductImageByProductId(int productId){
        productImageRepository.deleteProductImageByProductId(productId);
    }
}
