package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.ArrayList;
import java.util.List;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.entities.User;
import com.irdaislakhuafa.kopmart.services.ProductService;

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

    @GetMapping
    public String index(Model model) {
        try {
            /*
             * List<Product> productExampleList = new ArrayList<>();
             * 
             * for (int i = 0; i < 10; i++) {
             * productExampleList.add(
             * new Product(
             * "id ke ".replaceAll(" ", "_") + i,
             * "url foto ke ".replaceAll(" ", "_") + i,
             * "nama produk ke ".replaceAll(" ", "_"),
             * Double.valueOf(i + "200"),
             * "simple description product".replaceAll(" ", "_"),
             * "Lorem ipsum dolor sit amet consectetur adipisicing elit. Incidunt in libero, sit est odit vel quaerat autem assumenda tenetur quas eos, cum voluptates. Obcaecati iure ipsa, fugit omnis sunt quisquam."
             * ,
             * new Category() {
             * {
             * setName("category id");
             * }
             * },
             * Integer.valueOf(12 + i)));
             * }
             */

            model.addAttribute("title", ViewHelper.APP_TITLE);
            // model.addAttribute("listProducts", productExampleList);
            model.addAttribute("listProducts", productService.findAll());
            // model.addAttribute("listProducts",
            // UserHelper.getCurrentUser().get().getKeranjang().getProducts());
            System.out.println(UserHelper.getCurrentUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cart";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @RequestParam("id") String id) {
        try {
            Product product = productService.findById(id).orElse(null);
            UserHelper.getCurrentKeranjang().getProducts().add(product);
        } catch (Exception e) {
            System.out.println("Gagal menampah ke keranjang!");
        }

        /*
         * System.out.println("\033\143");
         * System.out.println(productService.findById(id));
         * 
         * try {
         * Thread.sleep(5000);
         * } catch (Exception e) {
         * // TODO: handle exception
         * }
         */
        return "redirect:/kopmart/produk";
    }
}
