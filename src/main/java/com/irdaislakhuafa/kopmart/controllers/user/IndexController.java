package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.List;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kopmart")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // index
    @GetMapping(value = { "/", "/home" })
    public String index(Model model) {
        try {
            // productService.fin
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("categories", categoryService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

}