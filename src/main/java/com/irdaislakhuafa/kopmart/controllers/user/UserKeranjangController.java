package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.ArrayList;
import java.util.List;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kopmart/produk/keranjang")
public class UserKeranjangController {
    @GetMapping
    public String index(Model model) {
        try {
            List<Product> productExampleList = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                productExampleList.add(
                        new Product(
                                "id ke ".replaceAll(" ", "_") + i,
                                "url foto ke ".replaceAll(" ", "_") + i,
                                "nama produk ke ".replaceAll(" ", "_"),
                                Double.valueOf(i + "200"),
                                "simple description product".replaceAll(" ", "_"),
                                "Lorem ipsum dolor sit amet consectetur adipisicing elit. Incidunt in libero, sit est odit vel quaerat autem assumenda tenetur quas eos, cum voluptates. Obcaecati iure ipsa, fugit omnis sunt quisquam.",
                                new Category() {
                                    {
                                        setName("category id");
                                    }
                                },
                                Integer.valueOf(12 + i)));
            }

            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("listProducts", productExampleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cart";
    }
}
