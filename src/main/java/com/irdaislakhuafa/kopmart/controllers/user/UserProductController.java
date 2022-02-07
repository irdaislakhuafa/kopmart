package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kopmart/produk")
public class UserProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // produk
    @GetMapping
    public String produk(Model model) {
        try {
            // System.out.println(productService.findAll());
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("products", productService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("searchActionUrl", "/kopmart/produk");
            // model.addAttribute("categoryValueId", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "produk";
    }

    // product details
    @GetMapping("/details/{id}")
    public String productDetails(Model model, @PathVariable(value = "id", required = true) String productId) {
        try {
            Product product = productService.findById(productId).get();
            model.addAttribute("product", product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "details";
    }

    // search by category
    @PostMapping
    public String searchProducts(
            Model model,
            @RequestParam("categoryIdName") Optional<String> categoryIdName,
            @RequestParam("keyword") Optional<String> keyword) {

        try {
            boolean isAllCategories = categoryIdName.get().equalsIgnoreCase("semua kategori");

            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("products",
                    // is all categories?
                    (isAllCategories) ?

                    // true
                            productService.findByNameContains(keyword.orElse("")) :
                            // false
                            productService.findByNameAndCategory(keyword.orElse(""), categoryIdName.get()));
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("searchActionUrl", "/kopmart/produk");
            model.addAttribute("keyword", keyword.get());

            Category categoryValue = categoryService.findByName(categoryIdName.get())
                    .orElse(
                            new Category(
                                    null,
                                    "Kategori tidak valid",
                                    "Deskripsi tidak valid",
                                    null));
            model.addAttribute("categoryValueId", categoryValue.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "produk";
    }
}
