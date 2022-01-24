package com.irdaislakhuafa.kopmart.models.repositories;

import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    public List<Category> findByNameContains(String name);

    public Category findByName(String name);
}
