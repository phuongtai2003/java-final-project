package com.example.demo.controller;

import com.example.demo.model.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SalespersonService {

    private final SalespersonRepository repo;

    @Autowired
    public SalespersonService(SalespersonRepository repo) {
        this.repo = repo;
    }

    public List<Salesperson> getAllSalesPerson() {
        return (List<Salesperson>) repo.findAll();
    }

    @Transactional
    public void save(Salesperson salesperson) {
        repo.save(salesperson);
    }

    public Optional<Salesperson> getSalespersonById(int id) {
        return repo.findById(id);
    }

    @Transactional
    public void updateSalesperson(Salesperson updatedSalesperson) {
        Optional<Salesperson> optionalExistingSalesperson = repo.findById(updatedSalesperson.getId());

        if (optionalExistingSalesperson.isPresent()) {
            Salesperson existingSalesperson = optionalExistingSalesperson.get();
            existingSalesperson.setStatus(updatedSalesperson.isStatus());
            repo.save(existingSalesperson);
        }
    }

    public void deleteSalespersonById(int id) {
        repo.deleteById(id);
    }
}

