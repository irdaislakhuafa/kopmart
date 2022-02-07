package com.irdaislakhuafa.kopmart.models.repositories;

import java.util.List;

import com.irdaislakhuafa.kopmart.models.entities.Keranjang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeranjangRepository extends JpaRepository<Keranjang, String> {
    public List<Keranjang> findByNameContainsIgnoreCase(String name);
}
