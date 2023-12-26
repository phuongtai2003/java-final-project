package com.example.finalproject.controller;

import com.example.finalproject.model.Customer;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.model.Transaction;
import com.example.finalproject.model.TransactionDetail;
import com.example.finalproject.model.request.CartItem;
import com.example.finalproject.model.request.CreateTransactionRequest;
import com.example.finalproject.repository.*;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public String index(){
        return "transaction/transaction";
    }

    @GetMapping("/{storeId}")
    @ResponseBody
    public ResponseData getTransactionsByStoreId(@PathVariable int storeId){
        return new ResponseData("Success", transactionService.getTransactionsByStoreId(storeId), true, 200, null, null);
    }

    @GetMapping("/detail/{transactionId}")
    public String getTransactionDetail(@PathVariable int transactionId, Model model){
        Transaction transaction = transactionService.getTransactionById(transactionId);
        List<TransactionDetail> transactionDetailList = (List<TransactionDetail>) transactionDetailsService.getTransactionDetailsByTransactionId(transactionId);
        model.addAttribute("transaction", transaction);
        model.addAttribute("transactionDetailList", transactionDetailList);
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        Transaction transaction = new Transaction();
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        transaction.setCustomer(customer);
        Salesperson salesperson = employeeService.getSalespersonById(request.getSalespersonId());
        transaction.setSalesperson(salesperson);
        transaction.setCustomerPaid(request.getCustomerPayment());
        transaction.setTotalPrice(total);
        transaction.setDateCreated(formattedDate);
        transaction.setTransactionChange(request.getCustomerPayment() - total);
        transaction.setTransactionDetails(new ArrayList<>());
        Transaction insertedTransaction = transactionService.addTransaction(transaction);
        for (CartItem item : request.getProducts()) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(insertedTransaction);
            transactionDetail.setProduct(productService.getProductById(item.getProductId()));
            transactionDetail.setQuantity(item.getQuantity());
            TransactionDetail td= transactionDetailsService.addTransactionDetails(transactionDetail);
            insertedTransaction.getTransactionDetails().add(td);
        }
        transactionService.addTransaction(insertedTransaction);
        return new ResponseData("Success", null, true, 200, null, null);
    }
}

