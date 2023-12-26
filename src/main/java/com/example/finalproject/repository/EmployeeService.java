package com.example.finalproject.repository;

import com.example.finalproject.model.Manager;
import com.example.finalproject.model.RetailStore;
import com.example.finalproject.model.Salesperson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private ManagerRepository managerRepository;
    private SalespersonRepository salespersonRepository;
    private RetailStoreRepository retailStoreRepository;
    private PasswordEncoder encoder;
    public EmployeeService(@Autowired ManagerRepository managerRepository, @Autowired PasswordEncoder encoder, @Autowired SalespersonRepository salespersonRepository, @Autowired RetailStoreRepository retailStoreRepository){
        this.salespersonRepository = salespersonRepository;
        this.managerRepository = managerRepository;
        this.encoder = encoder;
        this.retailStoreRepository = retailStoreRepository;
    }

    public Manager loginManager(String email, String password){
        Manager manager = managerRepository.findManagerByEmail(email);
        if(manager != null && encoder.matches(password, manager.getPassword())){
            return manager;
        }
        return null;
    }

    public Salesperson loginSalesperson(String email, String password){
        Salesperson salesperson = salespersonRepository.findSalespersonByEmail(email);
        if(salesperson != null && encoder.matches(password, salesperson.getPassword())){
            return salesperson;
        }
        return null;
    }

    public Manager loginManagerByStore(String email, String password, int storeId){
        RetailStore retailStore = retailStoreRepository.findById(storeId).orElse(null);
        if(retailStore == null){
            return null;
        }
        Manager manager = managerRepository.findManagerByEmailAndStoreId(email, retailStore.getId());
        if(manager != null && encoder.matches(password, manager.getPassword())){
            return manager;
        }
        return null;
    }

    public Salesperson loginSalespersonByStore(String email, String password, int storeId){
        RetailStore retailStore = retailStoreRepository.findById(storeId).orElse(null);
        if(retailStore == null){
            return null;
        }
        Salesperson salesperson = salespersonRepository.findSalespersonByEmailAndRetailStoreId(email, retailStore.getId());
        if(salesperson != null && encoder.matches(password, salesperson.getPassword())){
            return salesperson;
        }
        return null;
    }
    public Salesperson addSalesperson(Salesperson salesperson){
        salesperson.setPassword(encoder.encode(salesperson.getPassword()));
        return salespersonRepository.save(salesperson);
    }
    public List<Salesperson> getSalespersonByStoreId(int storeId){
        return salespersonRepository.findSalespersonByRetailStoreId(storeId);
    }
    public Salesperson getSalespersonById(int id){
        return salespersonRepository.findById(id).orElse(null);
    }

    public Salesperson updateSalesperson(Salesperson salesperson){
        salesperson.setPassword(encoder.encode(salesperson.getPassword()));
        return salespersonRepository.save(salesperson);
    }
    public Salesperson updateSalespersonStatus(Salesperson salesperson){
        return salespersonRepository.save(salesperson);
    }
    public Salesperson deleteEmployee(int id){
        Salesperson salesperson = salespersonRepository.findById(id).orElse(null);
        if(salesperson == null){
            return null;
        }
        salespersonRepository.delete(salesperson);
        return salesperson;
    }
}
