package com.example.finalproject.repository;

import com.example.finalproject.model.Manager;
import com.example.finalproject.model.Salesperson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalespersonRepository extends CrudRepository<Salesperson, Integer> {
    public Salesperson findSalespersonByEmail(String email);
    public Salesperson findSalespersonByEmailAndRetailStoreId(String email, int retailStoreId);
    public List<Salesperson> findSalespersonByRetailStoreId(int retailStoreId);
}
