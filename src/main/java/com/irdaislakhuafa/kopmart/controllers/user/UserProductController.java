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
            @RequestParam("categoryId") Optional<String> categoryId,
            @RequestParam("keyword") Optional<String> keyword) {

        try {
            Category categoryValue = categoryService.findById(categoryId.orElse("")).get();
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("products",
                    productService.findByNameAndCategory(keyword.orElse(""), categoryId.orElse("")));
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("searchActionUrl", "/kopmart/produk");
            model.addAttribute("categoryValueId", categoryValue.getId());
            model.addAttribute("keyword", keyword.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "produk";
    }
}
