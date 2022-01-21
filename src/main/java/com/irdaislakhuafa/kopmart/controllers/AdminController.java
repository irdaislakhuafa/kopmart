package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/kopmart/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/index";
    }

    // GET product new
    @GetMapping("/produk/new")
    public String newProduct(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("categories", categoryService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/produk/new";
    }

    // POST product new
    @PostMapping
    public String newProduct(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("harga") Double harga,
            @RequestParam("simpleDesc") String simpleDesc,
            @RequestParam("fullDesc") String fullDesc,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("categoryId") String categoryId) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/product/new";
    }
}
