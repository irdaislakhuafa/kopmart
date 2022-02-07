package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.repositories.KeranjangRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class KeranjangService implements BasicService<Keranjang> {
    @Autowired
    private KeranjangRepository keranjangRepository;

    @Override
    public Keranjang save(Keranjang entity) {
        return keranjangRepository.save(entity);
    }

    @Override
    public void removeById(String id) {
        keranjangRepository.deleteById(id);
    }

    @Override
    public Optional<Keranjang> findById(String id) {
        return keranjangRepository.findById(id);
    }

    @Override
    public List<Keranjang> findByNameContains(String name) {
        // return keranjangRepository.findByNameContainsIgnoreCase(name);
        return null;
    }

    @Override
    public Optional<Keranjang> findByName(String name) {
        return null;
    }

    @Override
    public List<Keranjang> saveAll(List<Keranjang> entities) {
        return null;
    }

    @Override
    public List<Keranjang> findAll() {
        return null;
    }

    @Override
    public Page<Keranjang> findAll(Pageable pageable) {
        return null;
    }

    public List<Product> findAllProduct() {
        return keranjangRepository.findAllProducts();
    }

}
