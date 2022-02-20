package com.irdaislakhuafa.kopmart.models.entities.converters;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class ProductDto {
    @CsvBindByName(column = "nama")
    private String name;

    @CsvBindByName(column = "harga")
    private Double harga;

    @CsvBindByName(column = "deskripsi singkat")
    private String simpleDesc;

    @CsvBindByName(column = "deskripsi lengkap")
    private String fullDesc;

    @CsvBindByName(column = "kategori")
    private String category;

    @CsvBindByName(column = "stok")
    private Integer stok;
}
