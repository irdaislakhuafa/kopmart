package com.irdaislakhuafa.kopmart.controllers.user;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.entities.User;
import com.irdaislakhuafa.kopmart.services.KeranjangService;
import com.irdaislakhuafa.kopmart.services.ProductService;
import com.irdaislakhuafa.kopmart.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kopmart/produk/keranjang")
public class UserKeranjangController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeranjangService keranjangService;

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("listProducts",
                    userService.findByEmail(
                            // get current user
                            UserHelper
                                    .getCurrentUser()
                                    .get()
                                    .getEmail())
                            .get()
                            .getKeranjang()
                            .getProducts());
            System.out.println(UserHelper.getCurrentUser());
        } catch (Exception e) {
            UserHelper.errorLog("gagal memuat halaman UserKeranjangController");
            // e.printStackTrace();
        }
        return "cart";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @RequestParam("id") String id) {
        try {
            // get product by id
            Product product = productService.findById(id).orElse(null);
            // get current keranjang
            Keranjang keranjang = keranjangService.findById(
                    UserHelper
                            .getCurrentUser()
                            .get()
                            .getKeranjang()
                            .getId())
                    .get();

            // add new product to keranjang
            keranjang.getProducts().add(product);
            // save keranjang
            keranjangService.save(keranjang);
            // print message
            UserHelper.print("Success add product (" + product.getId() + ") to cart (" + keranjang.getId() + ")");
        } catch (Exception e) {
            UserHelper.errorLog("Gagal menampah ke keranjang", this);
            // e.printStackTrace();
        }
        return "redirect:/kopmart/produk";
    }
}
