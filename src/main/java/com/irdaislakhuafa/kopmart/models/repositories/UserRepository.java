package com.irdaislakhuafa.kopmart.models.repositories;

import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
