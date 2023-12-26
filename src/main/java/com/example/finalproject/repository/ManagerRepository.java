package com.example.finalproject.repository;

import com.example.finalproject.model.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    public Manager findManagerByEmail(String email);
    public Manager findManagerByEmailAndStoreId(String email, int retailStoreId);
}
