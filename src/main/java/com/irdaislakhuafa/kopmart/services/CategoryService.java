package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // save
    public Category save(Category category) {
        category = categoryRepository.save(category);
        return category;
    }

    // find by id
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }
}
