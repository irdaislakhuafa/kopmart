package com.irdaislakhuafa.kopmart.helpers;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.models.entities.User;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {
    public static Optional<User> getCurrentUser() {
        User currentUser = null;
        try {
            currentUser = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            currentUser = new User() {
                {
                    setNama("Anonymouse User");
                    setEmail("anonymouse@user.com");
                }
            };
        }

        return Optional.of(currentUser);
    }
}
