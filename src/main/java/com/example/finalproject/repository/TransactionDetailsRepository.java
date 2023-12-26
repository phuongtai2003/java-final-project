package com.example.finalproject.repository;

import com.example.finalproject.model.TransactionDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionDetailsRepository extends CrudRepository<TransactionDetail, Integer> {
    public List<TransactionDetail> findAllByProductId(int productId);
}
