package com.irdaislakhuafa.kopmart.models.repositories;

import com.irdaislakhuafa.kopmart.models.entities.Keranjang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeranjangRepository extends JpaRepository<Keranjang, String> {
    // public List<Keranjang> findByNameContainsIgnoreCase(String name);

    // public List<Product> findAllProductsId();
    // @Query(nativeQuery = true, value = "select products_id from
    // keranjang_products where keranjang_id = ':keranjang_id'")
    // public List<Product> findAllProductsByKeranjangId(@PathParam("keranjang_id")
    // String keranjangId);

    // public Keranjang findByUserId(String id);

}
