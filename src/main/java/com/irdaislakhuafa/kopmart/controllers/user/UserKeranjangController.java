package com.irdaislakhuafa.kopmart.controllers.user;

import java.util.Optional;

import com.irdaislakhuafa.kopmart.helpers.ClassHelper;
import com.irdaislakhuafa.kopmart.helpers.FieldsTextMode;
import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Keranjang;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.entities.utils.KeranjangViewMode;
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
            model.addAttribute("currentUser", UserHelper.getCurrentUser().get());

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

            // delete url
            model.addAttribute("deleteUrl", "/kopmart/produk/keranjang/delete");
            model.addAttribute("viewModes", KeranjangViewMode.values());
            model.addAttribute("viewMode", KeranjangViewMode.class);
            model.addAttribute("editViewModeUrl", "/kopmart/produk/keranjang/edit/viewMode");
            model.addAttribute("currentCartViewMode", userService.findById(
                    UserHelper.getCurrentUser().get().getId()).get().getKeranjang().getViewMode());
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

    @PostMapping("/delete")
    public String deleteById(Model model, @RequestParam("id") Optional<String> id) {
        try {

            Keranjang keranjang = keranjangService.findById(
                    UserHelper.getCurrentUser()
                            .get()
                            .getKeranjang()
                            .getId())
                    .get();

            keranjang.getProducts().removeIf((product) -> product.getId().equals(id.get()));
            keranjangService.save(keranjang);
        } catch (Exception e) {
            UserHelper.errorLog("gagal menghapus product di keranjang", this);
        }
        return "redirect:/kopmart/produk/keranjang";
    }

    @PostMapping("/edit/viewMode")
    public String editViewMode(
            Model model,
            @RequestParam(name = "cartView") Optional<String> viewMode,
            @RequestParam(name = "currentUrl") Optional<String> requestUrl,
            @RequestParam(name = "keranjangId") Optional<String> id) {
        System.out.println("\033\143");
        try {
            Keranjang keranjang = keranjangService.findById(id.get()).get();
            keranjang.setViewMode(KeranjangViewMode.valueOf(viewMode.get().toUpperCase()));
            UserHelper.print(keranjang);
            keranjangService.save(keranjang);
        } catch (Exception e) {
            e.printStackTrace();
            UserHelper.errorLog("failed to edit view mode for keranjang");
        }
        return "redirect:" + requestUrl.get();
    }
}
