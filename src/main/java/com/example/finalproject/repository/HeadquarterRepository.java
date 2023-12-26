package com.example.finalproject.repository;

import com.example.finalproject.model.HeadquarterStaff;
import org.springframework.data.repository.CrudRepository;

public interface HeadquarterRepository extends CrudRepository<HeadquarterStaff, Integer> {
    public HeadquarterStaff findByEmail(String email);
}
