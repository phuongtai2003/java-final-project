package com.example.finalproject.controller;

import com.example.finalproject.repository.TransactionDetailsService;
import com.example.finalproject.repository.TransactionService;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private TransactionService transactionService;
    private TransactionDetailsService transactionDetailsService;
    private DashboardController(@Autowired TransactionService transactionService, @Autowired TransactionDetailsService transactionDetailsService){
        this.transactionDetailsService = transactionDetailsService;
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public String index(){
        return "dashboard";
    }

    @GetMapping("/transaction/{storeId}")
    @ResponseBody
    public ResponseData transaction(@PathVariable(name = "storeId") int storeId){
        return new ResponseData("Success", transactionService.getTransactionsByStoreId(storeId), true, 200, null, null);
    }
}

