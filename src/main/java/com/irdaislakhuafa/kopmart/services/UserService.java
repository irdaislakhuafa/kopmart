package com.irdaislakhuafa.kopmart.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.irdaislakhuafa.kopmart.models.entities.User;
import com.irdaislakhuafa.kopmart.models.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService, BasicService<User> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).orElse(
                new User() {
                    {
                        setNama("Anonymouse User");
                        setEmail("anonymouse@user.com");
                    }
                });
    }

    @Override
    public User save(User entity) {
        entity.setPassword(
                passwordEncoder.encode(
                        entity.getPassword()));
        return userRepository.save(entity);
    }

    @Override
    public void removeById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByNameContains(String name) {
        return userRepository.findByNamaContains(name);
    }

    @Override
    public Optional<User> findByName(String name) {
        return null;
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

}
