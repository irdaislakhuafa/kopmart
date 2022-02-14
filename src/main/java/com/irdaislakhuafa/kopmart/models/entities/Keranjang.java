package com.irdaislakhuafa.kopmart.models.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keranjang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keranjang extends BasicEntity<String> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    @OneToOne(cascade = { CascadeType.ALL })
    private User user;
}
