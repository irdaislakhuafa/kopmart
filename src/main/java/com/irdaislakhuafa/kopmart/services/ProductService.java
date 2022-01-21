package com.irdaislakhuafa.kopmart.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private CategoryService categoryService;

    private static List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product("1", "foto1", "Produk 1", Double.valueOf(1000.0), "Simple Descriprion",
                            "Full Description", CategoryService.categories.get(0),
                            Integer.valueOf(12))));

    public List<Product> getAll() {
        return ProductService.products;
    }
}
