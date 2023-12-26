package com.example.finalproject.repository;

import com.example.finalproject.model.TransactionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailsService {
    private TransactionDetailsRepository transactionDetailsRepository;
    public TransactionDetailsService(@Autowired TransactionDetailsRepository transactionDetailsRepository){
        this.transactionDetailsRepository = transactionDetailsRepository;
    }

    public TransactionDetail addTransactionDetails(TransactionDetail transactionDetails){
        return transactionDetailsRepository.save(transactionDetails);
    }

    public Iterable<TransactionDetail> getTransactionDetailsByProduct(int productId){
        return transactionDetailsRepository.findAllByProductId(productId);
    }
}
