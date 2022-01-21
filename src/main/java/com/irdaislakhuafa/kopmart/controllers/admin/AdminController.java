package com.irdaislakhuafa.kopmart.controllers.admin;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
