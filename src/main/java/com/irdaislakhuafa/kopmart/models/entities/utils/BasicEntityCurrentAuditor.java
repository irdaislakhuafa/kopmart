package com.irdaislakhuafa.kopmart.models.entities.utils;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;

import org.springframework.data.domain.AuditorAware;

public class BasicEntityCurrentAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(
                UserHelper
                        .getCurrentUser()
                        .get()
                        .getEmail());
    }

}
