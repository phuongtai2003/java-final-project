package com.example.finalproject.controller;

import com.example.finalproject.repository.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pos")
public class PointOfSaleController {
    private CustomerService customerService;
    public PointOfSaleController(@Autowired CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("")
    public String test(Model model){
        model.addAttribute("customers", customerService.findAll());
        return "/pos/pos";
    }
}

