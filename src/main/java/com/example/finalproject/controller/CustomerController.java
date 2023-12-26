package com.example.finalproject.controller;

import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.model.request.AddCustomerRequest;
import com.example.finalproject.repository.CustomerService;
import com.example.finalproject.repository.EmployeeService;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    private EmployeeService employeeService;
    public CustomerController(@Autowired CustomerService customerService, @Autowired EmployeeService employeeService){
        this.employeeService = employeeService;
        this.customerService = customerService;
    }
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("customers", customerService.findAll());
        return "customer/customer";
    }
    @PostMapping("/add")
    public String add(AddCustomerRequest request, Model model){
        if(request.getCustomerName().isEmpty() || request.getCustomerAddress().isEmpty() || request.getCustomerPhone().isEmpty()){
            model.addAttribute("errorMessage", "Please fill in all fields");
            return "customer/customer";
        }
        Salesperson salesperson = employeeService.getSalespersonById(request.getSalespersonId());
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setAddress(request.getCustomerAddress());
        customer.setPhone(request.getCustomerPhone());
        customer.setTransactions(new ArrayList<>());
        customer.setSalesperson(salesperson);
        customerService.add(customer);
        return "redirect:/customer";
    }

    @PostMapping("/addForPurchase")
    public String addForPurchase(AddCustomerRequest request, Model model){
        if(request.getCustomerName().isEmpty() || request.getCustomerAddress().isEmpty() || request.getCustomerPhone().isEmpty()){
            model.addAttribute("errorMessage", "Please fill in all fields");
            return "pos/pos";
        }
        Salesperson salesperson = employeeService.getSalespersonById(request.getSalespersonId());
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setAddress(request.getCustomerAddress());
        customer.setPhone(request.getCustomerPhone());
        customer.setTransactions(new ArrayList<>());
        customer.setSalesperson(salesperson);
        customerService.add(customer);
        return "redirect:/pos";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,AddCustomerRequest request, Model model){
        if(request.getCustomerName().isEmpty() || request.getCustomerAddress().isEmpty() || request.getCustomerPhone().isEmpty()){
            model.addAttribute("errorMessage", "Please fill in all fields");
            return "customer/customer";
        }
        Customer customer = customerService.getCustomerById(id);
        customer.setName(request.getCustomerName());
        customer.setAddress(request.getCustomerAddress());
        customer.setPhone(request.getCustomerPhone());
        customerService.update(customer);
        return "redirect:/customer";
    }
}

