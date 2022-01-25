package com.irdaislakhuafa.kopmart.controllers.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kopmart/admin/produk")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // GET product new
    @GetMapping("/new")
    public String newProduct(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", new Product());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/produk/new";
    }

    // POST product new
    @PostMapping("/new")
    public String newProduct(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("harga") Double harga,
            @RequestParam("simpleDesc") String simpleDesc,
            @RequestParam("fullDesc") String fullDesc,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("stok") Integer stok,
            RedirectAttributes redirectAttributes) {

        Product product = new Product();
        model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        model.addAttribute("categories", categoryService.findAll());

        try {

            Path path = Paths.get(System.getProperty("user.home") + "/.cache/" + foto.getOriginalFilename());
            byte[] bytes = foto.getBytes();

            Files.write(path, bytes);

            product.setName(name);
            product.setHarga(harga);
            product.setSimpleDesc(simpleDesc);
            product.setFullDesc(fullDesc);
            product.setFotoUrl(foto.getOriginalFilename());
            product.setCategoryId(categoryService.findById(categoryId).get());
            product.setStok(stok);

            productService.save(product);

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage",
                    e.getMessage().contains("ConstraintViolationException") ? "Maaf nama foto tidak boleh sama!" : "");

            product.setName(name);
            product.setHarga(harga);
            product.setSimpleDesc(simpleDesc);
            product.setFullDesc(fullDesc);
            product.setFotoUrl(foto.getOriginalFilename());
            product.setCategoryId(categoryService.findById(categoryId).get());
            product.setStok(stok);

            model.addAttribute("product", product);
            return "admin/produk/new";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/admin/produk/new";
    }

    // GET list product
    @GetMapping("/list")
    public String listProducts(
            Model model,
            @RequestParam("requestPage") Optional<Integer> requestPage,
            @RequestParam("requestData") Optional<Integer> requestData,
            @RequestParam("requestSort") Optional<String> requestSort) {

        try {
            // get pageable of products
            Page<Product> productPages = productService.findAll(
                    PageRequest.of(
                            // request page number
                            requestPage.orElse(0),
                            // request size of data
                            requestData.orElse(10),
                            // request sorting data
                            (requestSort.orElse("asc").equalsIgnoreCase("asc")
                                    || requestSort.orElse("asc").equalsIgnoreCase("ascending")
                                    || requestSort.orElse("asc").startsWith("asc")) ? // true/false
                                            Sort.by("name").ascending() : // if true
                                            Sort.by("name").descending())); // if false

            // list numbers of page
            List<Integer> productPageNumbers = IntStream.rangeClosed( // get integer range
                    // from 1
                    1,
                    // to total page numbers
                    productPages.getTotalPages())
                    // convert to Stream<Integer>
                    .boxed()
                    // convert to list
                    .collect(Collectors.toList());

            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("productPages", productPages);
            model.addAttribute("productPageNumbers", productPageNumbers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/produk/list";
    }
}
