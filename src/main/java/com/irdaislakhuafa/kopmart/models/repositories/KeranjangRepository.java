package com.irdaislakhuafa.kopmart.models.repositories;

import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeranjangRepository extends JpaRepository<Keranjang, String> {
    // public List<Keranjang> findByNameContainsIgnoreCase(String name);

    // public List<Product> findAllProductsId();
    @Query(nativeQuery = true, value = "select products_id from keranjang_products")
    public List<Product> findAllProducts();
}
