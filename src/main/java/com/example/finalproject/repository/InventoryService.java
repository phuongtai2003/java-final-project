package com.example.finalproject.repository;

import com.example.finalproject.model.Inventory;
import com.example.finalproject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public InventoryService(@Autowired InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory deleteInventoryByProductId(Product product) {
        return inventoryRepository.deleteInventoryByProduct(product);
    }
    public Inventory getInventoryByProductId(int productId){
        return inventoryRepository.getInventoryByProductId(productId);
    }
    public void deleteInventoryByInventoryId(int id) {
        inventoryRepository.deleteById(id);
    }
}
