package com.irdaislakhuafa.kopmart.models.repositories;

import java.util.List;
import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByNamaContains(String nama);

    Page<User> findAll(Pageable pageable);
}
