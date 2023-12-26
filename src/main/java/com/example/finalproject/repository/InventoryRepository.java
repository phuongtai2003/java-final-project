package com.example.finalproject.repository;

import com.example.finalproject.model.Inventory;
import com.example.finalproject.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    @Transactional
    public Inventory deleteInventoryByProduct(Product product);
    public Inventory getInventoryByProductId(int productId);
}
