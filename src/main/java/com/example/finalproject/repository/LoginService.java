package com.example.finalproject.repository;

import com.example.finalproject.model.RetailStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private RetailStoreRepository retailStoreRepository;

    public LoginService(@Autowired RetailStoreRepository retailStoreRepository){
        this.retailStoreRepository = retailStoreRepository;
    }

    public List<RetailStore> getAllRetailStores(){
        return (List<RetailStore>) retailStoreRepository.findAll();
    }
}
