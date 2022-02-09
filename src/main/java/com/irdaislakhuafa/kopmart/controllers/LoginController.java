package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.User;
import com.irdaislakhuafa.kopmart.models.entities.utils.UserRole;
import com.irdaislakhuafa.kopmart.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public String index(Model model) {
        // System.out.println(UserHelper.getCurrentUser());
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
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("user", new User());
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
            @RequestParam("password") String password,
            @RequestParam("noTelegram") String noTelegram,
            // RedirectAttributes redirectAttributes,
            User user) {

        try {
            model.addAttribute("title", ViewHelper.APP_TITLE);

            // npm
            userService.findByNpm(npm).ifPresent((valueNpm) -> {
                model.addAttribute("npmError",
                        String.format("npm \"%s\" sudah ada!", npm));
            });

            // email
            userService.findByEmail(email).ifPresent((valueEmail) -> {
                model.addAttribute("emailError",
                        String.format("Email \"%s\" sudah ada!", email));
            });

            // no telegram
            userService.findByNoTelegram(noTelegram).ifPresent((valueNoTelegram) -> {
                model.addAttribute("noTelegramError",
                        String.format("no telegram \"%s\" sudah ada!", noTelegram));
            });

            if (userService.existsByNpmOrEmailOrNoTelegram(npm, email, noTelegram)) {
                model.addAttribute("user", user);
                return "register";
            }

            user.setNoTelegram(noTelegram);
            user.setRole(UserRole.MAHASISWA);
            user.setKeranjang(new Keranjang());

            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/user/login";
    }
}
