package com.irdaislakhuafa.kopmart.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irdaislakhuafa.kopmart.helpers.DataToCsvHelper;
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
    public String name() throws Exception {
        return "Hello World";
    }
}
