package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    // private List<Product> products = new ArrayList<>(
    // Arrays.asList(
    // new Product("1", "foto1", "Produk 1", Double.valueOf(1000.0), "Simple
    // Descriprion",
    // "Full Description", categoryService.findAll().get(0),
    // Integer.valueOf(12))));

    // public List<Product> getAll() {
    // return products;
    // }
}
