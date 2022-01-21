package com.irdaislakhuafa.kopmart.models.repositories;

import com.irdaislakhuafa.kopmart.models.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
