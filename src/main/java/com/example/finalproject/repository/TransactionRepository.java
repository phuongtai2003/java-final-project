package com.example.finalproject.repository;

import com.example.finalproject.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.salesperson.retailStore.id = ?1")
    Iterable<Transaction> findAllByStoreId(int storeId);
}
