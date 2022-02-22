package com.irdaislakhuafa.kopmart.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.opencsv.bean.CsvBindByName;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BasicEntity<String> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, length = 100, unique = true)
    @Size(max = 100, message = "nama kategori maksimal 100 karakter")
    @CsvBindByName(column = "nama", required = true)
    private String name;

    @Column(length = 500)
    @Size(max = 500, message = "deskripsi maksimal 500 karakter")
    @CsvBindByName(column = "deskripsi", required = true)
    private String description;
}
