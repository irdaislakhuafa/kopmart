package com.irdaislakhuafa.kopmart.models.entities;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.irdaislakhuafa.kopmart.models.entities.utils.UserRole;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasicEntity<String> implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull(message = "NPM cannot be null!")
    @Column(nullable = false, unique = true, length = 10)
    private String npm;

    @NotNull(message = "Name cannot be null!")
    @Column(nullable = false, length = 100)
    private String nama;

    @NotNull(message = "Password cannot be null")
    @Column(length = 100, nullable = false)
    private String password;

    @Email(message = "Your email is not valid!")
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Telegram number cannot be null!")
    @Column(length = 50, nullable = false)
    private String noTelegram;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "User role cannot be null!")
    @Column(nullable = false)
    private UserRole role;

    @Column(length = 1000)
    private String fotoUrl;

    @OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Keranjang keranjang;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
