package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.KeranjangService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kopmart/produk")
public class UserProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private KeranjangService keranjangService;

    // produk
    @GetMapping
    public String produk(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("products", productService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("searchActionUrl", "/kopmart/produk");
            model.addAttribute("currentUser", UserHelper.getCurrentUser().get().getEmail());

            // if user is login
            if (!UserHelper.getCurrentUser().get().getEmail().equalsIgnoreCase("anonymouse@gmail.com")) {
                model.addAttribute("cartLength", keranjangService.findById(
                        UserHelper
                                .getCurrentUser()
                                .get()
                                .getKeranjang()
                                .getId())
                        .get()
                        .getProducts().size());
            }

        } catch (Exception e) {
            UserHelper.errorLog("terjadi error tidak diketahui", this);
            // e.printStackTrace();
        }
        return "produk";
    }

    // product details
    @GetMapping("/details/{id}")
    public String productDetails(Model model, @PathVariable(value = "id", required = true) String productId) {
        try {
            Product product = productService.findById(productId).get();
            model.addAttribute("product", product);
            model.addAttribute("currentUser", UserHelper.getCurrentUser().get().getEmail());

            // if user is login
            if (!UserHelper.getCurrentUser().get().getEmail().equalsIgnoreCase("anonymouse@gmail.com")) {
                model.addAttribute("cartLength", keranjangService.findById(
                        UserHelper
                                .getCurrentUser()
                                .get()
                                .getKeranjang()
                                .getId())
                        .get()
                        .getProducts().size());
            }
        } catch (Exception e) {
            UserHelper.errorLog("gagal mendapatkan details produk dengan id \"" + productId + "\"", this);
            // e.printStackTrace();
        }
        return "details";
    }

    // search by category
    @GetMapping("/search")
    public String searchProducts(
            Model model,
            @RequestParam("categoryId") Optional<String> categoryId,
            @RequestParam("keyword") Optional<String> keyword) {

        try {
            boolean isAllCategories = categoryId.get().equalsIgnoreCase("semua kategori");

            model.addAttribute("title", ViewHelper.APP_TITLE);
            model.addAttribute("products",
                    // is all categories?
                    (isAllCategories) ?

                    // true
                            productService.findByNameContains(
                                    keyword.orElse(""))
                            :
                            // false
                            productService.findByNameAndCategory(
                                    keyword.orElse(""),
                                    categoryId.get()));

            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("searchActionUrl", "/kopmart/produk/search");
            model.addAttribute("keyword", keyword.orElse(""));
            model.addAttribute("currentUser", UserHelper.getCurrentUser().get().getEmail());

            Category categoryValue = categoryService.findById(categoryId.get())
                    .orElse(
                            new Category(
                                    null,
                                    "Kategori tidak valid",
                                    "Deskripsi tidak valid",
                                    null));
            model.addAttribute("categoryValueId", categoryValue.getId());

            // if user is login
            if (!UserHelper.getCurrentUser().get().getEmail().equalsIgnoreCase("anonymouse@gmail.com")) {
                model.addAttribute("cartLength", keranjangService.findById(
                        UserHelper
                                .getCurrentUser()
                                .get()
                                .getKeranjang()
                                .getId())
                        .get()
                        .getProducts().size());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("error saat search by category or keyword", this);
        }
        return "produk";
    }
}
