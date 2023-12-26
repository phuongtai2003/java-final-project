package com.example.finalproject.repository;

import com.example.finalproject.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    public TransactionService(@Autowired TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public Iterable<Transaction> getTransactionsByStoreId(int storeId){
        return transactionRepository.findAllByStoreId(storeId);
    }

    public Transaction getTransactionById(int transactionId){
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public Transaction addTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
}
