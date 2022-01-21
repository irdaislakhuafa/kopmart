package com.irdaislakhuafa.kopmart.controllers.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/kopmart/admin/produk")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // GET product new
    @GetMapping("/new")
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
    @PostMapping("/new")
    public String newProduct(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("harga") Double harga,
            @RequestParam("simpleDesc") String simpleDesc,
            @RequestParam("fullDesc") String fullDesc,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("stok") Integer stok) {
        try {

            Path path = Paths.get(System.getProperty("user.home") + "/.cache/" + foto.getOriginalFilename());
            byte[] bytes = foto.getBytes();

            Files.write(path, bytes);

            Product product = new Product();
            product.setName(name);
            product.setHarga(harga);
            product.setSimpleDesc(simpleDesc);
            product.setFullDesc(fullDesc);
            product.setFotoUrl(foto.getOriginalFilename());
            product.setCategoryId(categoryService.findById(categoryId).get());
            product.setStok(stok);

            productService.save(product);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/admin/produk/new";
    }
}
