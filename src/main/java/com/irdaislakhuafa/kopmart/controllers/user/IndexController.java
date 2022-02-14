package com.irdaislakhuafa.kopmart.controllers.user;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String redirectIndex() {
        try {
            UserHelper.print(UserHelper.getCurrentUser());
        } catch (Exception e) {
            UserHelper.errorLog("gagal redirect", this);
            // e.printStackTrace();
        }
        return "redirect:/kopmart/home/";
    }

    // index
    @GetMapping(value = { "/kopmart", "/kopmart/", "/kopmart/home", "/kopmart/home/" })
    public String index(Model model) {
        try {
            model.addAttribute("productService", productService);
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("usedCategories", productService.findAllUsedCategories());
            model.addAttribute("currentUser", UserHelper.getCurrentUser().get().getEmail());
            model.addAttribute("categorySearchUrl", "/kopmart/produk/search");
        } catch (Exception e) {
            UserHelper.errorLog("terjadi error halaman index", this);
            // e.printStackTrace();
        }
        return "index";
    }

}
