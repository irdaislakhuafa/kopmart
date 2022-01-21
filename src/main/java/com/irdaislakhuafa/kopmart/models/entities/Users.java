package com.irdaislakhuafa.kopmart.models.entities;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
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

    @OneToOne(cascade = { CascadeType.REMOVE })
    private Keranjang keranjangId;
}
