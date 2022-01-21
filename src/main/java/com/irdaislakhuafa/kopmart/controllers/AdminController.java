package com.irdaislakhuafa.kopmart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kopmart/admin")
public class AdminController {
    @GetMapping
    public String index(Model model) {
        return "admin/index";
    }
}
