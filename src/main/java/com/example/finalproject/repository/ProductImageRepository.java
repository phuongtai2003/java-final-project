package com.example.finalproject.repository;

import com.example.finalproject.model.ProductImage;
import org.springframework.data.repository.CrudRepository;

public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
    public void deleteProductImageByProductId(int productId);
}
