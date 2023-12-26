package com.example.finalproject.repository;

import com.example.finalproject.model.HeadquarterStaff;
import com.example.finalproject.model.Manager;
import com.example.finalproject.model.RetailStore;
import com.example.finalproject.utils.JsonTokenUtil;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadquarterService {
    private PasswordEncoder passwordEncoder;
    private HeadquarterRepository headquarterRepository;
    private ManagerRepository managerRepository;
    private RetailStoreRepository retailStoreRepository;

    public HeadquarterService(@Autowired PasswordEncoder passwordEncoder, @Autowired ManagerRepository managerRepository, @Autowired RetailStoreRepository retailStoreRepository ,@Autowired HeadquarterRepository headquarterRepository){
        this.passwordEncoder = passwordEncoder;
        this.headquarterRepository = headquarterRepository;
        this.managerRepository = managerRepository;
        this.retailStoreRepository = retailStoreRepository;
    }

    public HeadquarterStaff registerHeadquarter(String email, String password, String name, String gender){
        String encodedPassword = passwordEncoder.encode(password);
        HeadquarterStaff headquarterStaff = new HeadquarterStaff(0,name ,email, encodedPassword, gender, null, new ArrayList<>(), new ArrayList<>());
        return headquarterRepository.save(headquarterStaff);
    }

    public HeadquarterStaff loginHeadquarter(String email, String password){
        HeadquarterStaff headquarterStaff = headquarterRepository.findByEmail(email);
        if(headquarterStaff == null){
            return null;
        }
        if(passwordEncoder.matches(password, headquarterStaff.getPassword())){
            return headquarterStaff;
        }
        return null;
    }

    public void addNewStore(RetailStore retailStore, Manager manager){
        RetailStore rs = retailStoreRepository.save(retailStore);
        manager.setStore(rs);
        String hashedPassword = passwordEncoder.encode(manager.getPassword());
        manager.setPassword(hashedPassword);
        managerRepository.save(manager);
    }
    public RetailStore getStoreByManager(Manager manager){
        return retailStoreRepository.findById(manager.getStore().getId()).orElse(null);
    }

    public List<Manager> getAllManagers(){
        return (List<Manager>) managerRepository.findAll();
    }
    public Manager getManagerById(int id){
        return managerRepository.findById(id).orElse(null);
    }

    public void updateStore(Manager manager, RetailStore retailStore){
        RetailStore rs = retailStoreRepository.save(retailStore);
        manager.setStore(rs);
        managerRepository.save(manager);
    }

    public boolean deleteStore(int id){
        Manager manager = managerRepository.findById(id).orElse(null);
        if(manager == null){
            return false;
        }
        RetailStore retailStore = retailStoreRepository.findById(manager.getStore().getId()).orElse(null);
        if(retailStore == null){
            return false;
        }
        managerRepository.delete(manager);
        retailStoreRepository.delete(retailStore);
        return true;
    }
}
