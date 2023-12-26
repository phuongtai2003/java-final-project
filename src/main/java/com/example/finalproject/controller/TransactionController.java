package com.example.finalproject.controller;

import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.model.Transaction;
import com.example.finalproject.model.request.CartItem;
import com.example.finalproject.model.request.CreateTransactionRequest;
import com.example.finalproject.repository.*;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/invoice")
public class TransactionController {
    private TransactionDetailsService transactionDetailsService;
    private TransactionService transactionService;
    private CustomerService customerService;
    private ProductService productService;
    private EmployeeService employeeService;
    private TransactionController(@Autowired EmployeeService employeeService,@Autowired ProductService productService,@Autowired CustomerService customerService,@Autowired TransactionService transactionService,@Autowired TransactionDetailsService transactionDetailsService){
        this.transactionDetailsService = transactionDetailsService;
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.productService = productService;
        this.employeeService = employeeService;
    }
    @GetMapping("")
    public String test(){
        return "transaction/transaction";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseData add(@RequestBody CreateTransactionRequest request){
        if(request.getProducts() == null || request.getCustomerId() == null || request.getSalespersonId() == null || request.getCustomerPayment() == null){
            return new ResponseData("Bad Request", null, false, 400, null, null);
        }
        int total = 0;
        for (CartItem item : request.getProducts()) {
            total += item.getProductPrice() * item.getQuantity();
        }
        if(request.getCustomerPayment() < total){
            return new ResponseData("Bad Request", null, false, 400, null, null);
        }
        Transaction transaction = new Transaction();
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        transaction.setCustomer(customer);
        Salesperson salesperson = employeeService.getSalespersonById(request.getSalespersonId());
        transaction.setSalesperson(salesperson);
        transaction.setCustomerPaid(total);
        transaction.setTransactionChange(request.getCustomerPayment() - total);
        transactionService.addTransaction(transaction);
        return new ResponseData("Success", null, true, 200, null, null);
    }
}

