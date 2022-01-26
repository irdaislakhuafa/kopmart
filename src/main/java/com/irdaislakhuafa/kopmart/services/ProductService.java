package com.irdaislakhuafa.kopmart.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements BasicService<Product> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void removeById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByNameContains(String name) {
        return productRepository.findByNameContainsIgnoreCase(name);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Product> saveAll(List<Product> entities) {
        return productRepository.saveAll(entities);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findByCategoryId(String id) {
        return productRepository.findByCategoryId(
                categoryService.findById(id).get());
    }

    public Set<Category> findAllUsedCategories() {
        Set<Category> usedCategory = new HashSet<>();

        this.findAll().forEach((p) -> {
            usedCategory.add(p.getCategoryId());
        });
        return usedCategory;
    }

    public List<Product> findByNameAndCategory(String name, String categoryIdName) {
        return productRepository.findByNameContainsIgnoreCaseAndCategoryIdNameIgnoreCase(
                name,
                categoryIdName);
    }

    public List<Product> findByCategoryName(String name) {
        return productRepository.findByCategoryIdNameIgnoreCase(name);
    }
}
