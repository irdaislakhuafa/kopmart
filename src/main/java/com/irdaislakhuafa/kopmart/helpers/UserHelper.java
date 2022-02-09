package com.irdaislakhuafa.kopmart.helpers;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
    public static Optional<User> getCurrentUser() {
        User currentUser = null;

        currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return Optional.of(currentUser);
    }
}
