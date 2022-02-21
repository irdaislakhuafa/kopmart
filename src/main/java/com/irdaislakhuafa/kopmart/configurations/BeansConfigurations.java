package com.irdaislakhuafa.kopmart.configurations;

import com.irdaislakhuafa.kopmart.models.entities.utils.BasicEntityCurrentAuditor;
import com.irdaislakhuafa.kopmart.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// this is file configuration
@Configuration
// enable auditing
@EnableJpaAuditing(
        // set auditor referensi
        auditorAwareRef = "auditorAware")
public class BeansConfigurations {
    @Autowired
    private UserService userService;

    // bcrypt password encoder
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // dao authentication provider
    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(encoder());
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }

    // auditor aware
    @Bean
    public AuditorAware<String> auditorAware() {
        return new BasicEntityCurrentAuditor();
    }
}
