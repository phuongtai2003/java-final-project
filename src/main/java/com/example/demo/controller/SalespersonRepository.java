package com.example.demo.controller;

import com.example.demo.model.Salesperson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalespersonRepository extends CrudRepository<Salesperson,Integer> {

}
