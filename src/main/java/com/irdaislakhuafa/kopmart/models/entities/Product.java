package com.irdaislakhuafa.kopmart.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.irdaislakhuafa.kopmart.models.entities.converters.ProductCategoryConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Column(length = 100, nullable = false)
    @NotNull(message = "nama tidak boleh kosong!")
    @Size(max = 100, message = "nama maksimal 100 karakter!")
    @CsvBindByName(column = "nama")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "harga tidak boleh kosong!")
    @CsvBindByName(column = "harga")
    private Double harga;

    @Column(length = 500)
    @Max(value = 500, message = "deskripsi singkat maksimal 500 karakter!")
    @CsvBindByName(column = "deskripsi singkat")
    private String simpleDesc;

    @Column(length = 1500)
    @Max(value = 1500, message = "deskripsi lengkap maksimal 1500 karakter!")
    @CsvBindByName(column = "deskripsi lengkap")
    private String fullDesc;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @CsvCustomBindByName(column = "kategori", converter = ProductCategoryConverter.class)
    private Category categoryId;

    @Column(nullable = false)
    @CsvBindByName(column = "stok")
    private Integer stok;
}
