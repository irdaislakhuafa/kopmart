package com.irdaislakhuafa.kopmart.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity https) throws Exception {
        https
                .authorizeRequests()
                .antMatchers(
                        // webjars
                        "/webjars/**",
                        // style
                        "/style/**",
                        // assets
                        "/assets/**",
                        // kopmart
                        "/kopmart/home/**",
                        "/kopmart/produk",
                        "/kopmart/produk/",
                        "/kopmart/produk/details/**",
                        "/spring-boot.png"
                //
                )
                .permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                // login page
                .formLogin()
                .loginPage("/kopmart/login")
                .permitAll();
    }

}
