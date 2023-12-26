package com.example.finalproject.repository;

import com.example.finalproject.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;
    public ManagerService(@Autowired ManagerRepository managerRepository, @Autowired PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Manager getManagerById(Integer id) {
        return managerRepository.findById(id).orElse(null);
    }

    public Manager updateManager(Manager manager) {
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        return managerRepository.save(manager);
    }
}
