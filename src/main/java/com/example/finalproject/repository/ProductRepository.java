package com.example.finalproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.finalproject.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    public Product findByName(String name);
    @Query("SELECT p FROM Product p JOIN p.inventories i ON p.id = i.product.id WHERE i.retailStore.id = :retailStoreId")
    public List<Product> findAllByInventories(int retailStoreId);
}
