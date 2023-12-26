package com.example.finalproject.repository;

import com.example.finalproject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryService(@Autowired CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> getCategoriesByIds(Iterable<Integer> ids) {
        return categoryRepository.findAllById(ids);
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
