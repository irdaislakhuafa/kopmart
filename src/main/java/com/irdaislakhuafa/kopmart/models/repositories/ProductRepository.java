package com.irdaislakhuafa.kopmart.models.repositories;

import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findByNameContainsIgnoreCase(String name);

    public Product findByNameIgnoreCase(String name);

    public Page<Product> findAll(Pageable pageable);

    public List<Product> findByCategoryId(Category categoryId);

    public List<Category> findByCategoryIdIsNotNull();

    public List<Product> findByNameContainsIgnoreCaseAndCategoryIdNameIgnoreCase(String name, String categoryIdName);

    public List<Product> findByCategoryIdNameIgnoreCase(String name);
}
