package com.irdaislakhuafa.kopmart.controllers.admin;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kopmart/admin/kategori")
public class CategoryController {
    @GetMapping("/new")
    public String newCategory(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/kategory/new";
    }
}
