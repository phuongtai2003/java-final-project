package com.example.finalproject.repository;

import com.example.finalproject.model.RetailStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetailStoreService {
    private RetailStoreRepository retailStoreRepository;

    public RetailStoreService(@Autowired RetailStoreRepository retailStoreRepository){
        this.retailStoreRepository = retailStoreRepository;
    }

    public RetailStore getStoreById(int id){
        return retailStoreRepository.findById(id).orElse(null);
    }
}
