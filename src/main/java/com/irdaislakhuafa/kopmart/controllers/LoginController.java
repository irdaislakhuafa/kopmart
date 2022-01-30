package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({
        "/kopmart/login"
})
public class LoginController {
    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }
}
