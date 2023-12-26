package com.example.finalproject.repository;

import com.example.finalproject.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionService {
    private TransactionRepository transactionRepository;
    public TransactionService(@Autowired TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
}
