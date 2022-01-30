package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({
        "/kopmart"
})
public class LoginController {
    @GetMapping("/user/login")
    public String index(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("registerUrl", "/kopmart/user/register");
            model.addAttribute("loginUrl", "/kopmart/user/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    @GetMapping("/user/register")
    public String register(Model model) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "register";
    }

    @PostMapping("/user/register")
    public String register(
            Model model,
            @RequestParam("npm") String npm,
            @RequestParam("nama") String name,
            @RequestParam("email") String email,
            @RequestParam("email") String password,
            @RequestParam("noTelegram") String noTelegram) {

        try {
            System.out.println("\033\143");
            System.out.println(npm + " => " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/user/login";
    }
}
