package com.irdaislakhuafa.kopmart.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Category;

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
}
