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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            RedirectAttributes redirectAttributes) {

        try {
            // npm
            userService.findByNpm(npm).ifPresent((valueNpm) -> {
                redirectAttributes.addFlashAttribute("npmError",
                        String.format("npm \"%s\" already exists!", npm));
            });

            // email
            userService.findByEmail(email).ifPresent((valueEmail) -> {
                redirectAttributes.addFlashAttribute("emailError",
                        String.format("Email \"%s\" already exists!", email));
            });

            // no telegram
            userService.findByNoTelegram(noTelegram).ifPresent((valueNoTelegram) -> {
                redirectAttributes.addFlashAttribute("noTelegramError",
                        String.format("no telegram \"%s\" already exists!", noTelegram));
            });

            if (userService.existsByNpmOrEmailOrNoTelegram(npm, email, noTelegram)) {
                return "redirect:/kopmart/user/login";
            }

            User userRegister = new User();
            userRegister.setNpm(npm);
            userRegister.setNama(name);
            userRegister.setEmail(email);
            userRegister.setPassword(password);
            userRegister.setNoTelegram(noTelegram);
            userRegister.setRole(UserRole.MAHASISWA);
            userRegister.setKeranjang(new Keranjang());

            userService.save(userRegister);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/user/login";
    }
}
