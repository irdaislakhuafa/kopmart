package com.irdaislakhuafa.kopmart.controllers;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
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
            @RequestParam("noTelegram") String noTelegram) {

        try {
            // System.out.println("\033\143");
            // System.out.println(npm + " => " + userRole);
            User userRegister = new User();
            userRegister.setNpm(npm);
            userRegister.setNama(name);
            userRegister.setEmail(email);
            userRegister.setPassword(password);
            userRegister.setNoTelegram(noTelegram);
            userRegister.setRole(UserRole.MAHASISWA);

            userService.save(userRegister);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/user/login";
    }
}
