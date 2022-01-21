package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kopmart")
public class IndexController {

    private static final String APP_TITLE = "KOPMART | Koperasi Mahasiswa Mart";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // index
    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("title", APP_TITLE);
            model.addAttribute("categories", categoryService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    // produk
    @GetMapping("/produk")
    public String produk(Model model) {
        try {
            model.addAttribute("title", APP_TITLE);
            model.addAttribute("products", productService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "produk";
    }

    // product details
    @GetMapping("/produk/details/{id}")
    public String productDetails(Model model, @PathVariable(value = "id", required = true) String productId) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "details";
    }
}
