package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.services.KeranjangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private KeranjangService keranjangService;

    @GetMapping
    public String name() {
        return "Hello World";
    }
}
