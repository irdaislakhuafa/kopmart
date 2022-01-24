package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasicService<A> {
    public A save(A entity);

    public void removeById(String id);

    public Optional<A> findById(String id);

    public List<A> findByNameContains(String name);

    public A findByName(String name);

    public List<A> saveAll(List<A> entities);

    public List<A> findAll();

    public Page<A> findAll(Pageable pageable);
}
