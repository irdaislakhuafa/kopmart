package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements BasicService<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public void removeById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findByNameContains(String name) {
        return categoryRepository.findByNameContains(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> saveAll(List<Category> entities) {
        return categoryRepository.saveAll(entities);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findByNameIgnoreCase(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

}
