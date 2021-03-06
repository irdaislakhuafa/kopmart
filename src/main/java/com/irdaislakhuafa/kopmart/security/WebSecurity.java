package com.irdaislakhuafa.kopmart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private DaoAuthenticationProvider provider;

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
                        // scripts
                        "/scripts/**",
                        // kopmart
                        "/",
                        "/kopmart",
                        "/kopmart/",
                        "/kopmart/home/**",
                        "/kopmart/produk",
                        "/kopmart/produk/",
                        "/kopmart/produk/search",
                        // "/kopmart/produk/details/**",
                        // "/kopmart/produk/keranjang",
                        "/spring-boot.png",

                        // admin
                        "/admin/**",
                        "/kopmart/admin/**",

                        // register
                        "/kopmart/user/register",

                        // test
                        "/test"
                //
                )
                .permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                // login page
                .formLogin()
                .loginPage("/kopmart/user/login")
                // on success
                // .defaultSuccessUrl("/kopmart/")
                // .failureUrl("/kopmart/user/login/failed")
                .permitAll()

                .and().httpBasic();
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}
