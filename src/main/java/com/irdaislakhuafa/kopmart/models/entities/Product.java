package com.irdaislakhuafa.kopmart.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BasicEntity<String> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true, nullable = true, length = 1000)
    private String fotoUrl;

    @Column(length = 100)
    private String name;

    @Column(nullable = false)
    private Double harga;

    @Column(length = 500)
    private String simpleDesc;

    @Column(length = 1500)
    private String fullDesc;

    @ManyToOne
    private Category categoryId;

    @Column(nullable = false)
    private Integer stok;

    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Keranjang keranjang;
}
