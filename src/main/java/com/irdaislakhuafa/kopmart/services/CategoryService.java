package com.irdaislakhuafa.kopmart.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    public static List<Category> categories = new ArrayList<>(
            Arrays.asList(
                    new Category("1", "Snack", "snack describtion"),
                    new Category("2", "ATK", "ATK describtion"),
                    new Category("3", "Craft", "Craft describtion")));

    public List<Category> getAll() {
        return CategoryService.categories;
    }

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
}
