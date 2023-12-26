package com.example.finalproject.repository;

import com.example.finalproject.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(@Autowired CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Iterable<Customer> findAll(){
        return customerRepository.findAll();
    }
    public Customer add(Customer customer){
        return customerRepository.save(customer);
    }
    public Customer getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }
    public Customer update(Customer customer){
        return customerRepository.save(customer);
    }
}
