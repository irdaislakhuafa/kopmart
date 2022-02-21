package com.irdaislakhuafa.kopmart.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// TODO create endpoint upload CSV for this class
@Controller
@RequestMapping("/kopmart/admin/kategori")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/new")
    public String newCategory(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi error di halaman new category di admin", this);
        }
        return "admin/category/new";
    }

    @PostMapping("/new")
    public String newCategory(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("description") String description) {

        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);

            // create new category
            Category category = new Category();
            // set category name
            category.setName(name);
            // set category description
            category.setDescription(description);
            // save new category
            categoryService.save(category);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi error saat membuat category baru", this);
        }
        return "redirect:/kopmart/admin/kategori/list";
    }

    // get list
    @GetMapping({ "/list", "/" })
    public String listCategories(
            Model model,
            @RequestParam("requestPage") Optional<Integer> requestPage,
            @RequestParam("requestData") Optional<Integer> requestData,
            @RequestParam(value = "requestSort", required = false) Optional<String> requestSort) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);

            // get pages of category
            Page<Category> categoryPages = categoryService.findAll(
                    PageRequest.of(
                            // request page
                            requestPage.orElse(0),
                            // request data
                            requestData.orElse(10),
                            // sorting request
                            (requestSort.orElse("asc").contains("asc")) ?
                            // ascending
                                    Sort.by("name").ascending() :
                                    // descending
                                    Sort.by("name").descending()));

            // build list of page numbers
            List<Integer> categoryPageNumbers = IntStream.rangeClosed(
                    // get number range from 1 to total pages
                    1, categoryPages.getTotalPages())
                    // convert IntStream to Stream<Integer>
                    .boxed()
                    // convert Stream<Integer> to List or ArrayList
                    .collect(Collectors.toList());

            model.addAttribute("categoryPages", categoryPages);
            model.addAttribute("categoryPageNumbers", categoryPageNumbers);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("error saat mencoba mengambil list category di admin", this);
        }
        return "admin/category/list";
    }

    // edit
    @PostMapping("/edit")
    public String editCategory(Model model, Category category) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            categoryService.save(category);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi kesalahan saat mengedit category", this);
        }
        return "redirect:/kopmart/admin/kategori/list";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(Model model, @PathVariable("id") String categoryId) {
        try {
            Category category = categoryService.findById(categoryId).get();

            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("category", category);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi error saat memuat halaman edit category", this);
        }
        return "admin/category/edit";
    }

    // delete
    @PostMapping("/delete")
    public String deleteCategory(
            Model model,
            @RequestParam("categoryId") String categoryId,
            RedirectAttributes redirectAttributes) {
        try {
            categoryService.removeById(categoryId);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("categoryDeleteError",
                    "gagal menghapus kategori! pastikan kategori tidak sedang dipakai oleh produk!");
        } catch (Exception e) {
            e.printStackTrace();
            UserHelper.errorLog("gagal menghapus category", this);
        }
        return "redirect:/kopmart/admin/kategori/list";
    }

    // upload csv
    @GetMapping("/upload/csv")
    public String uploadCsv(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("uploadCsvUrl", "/kopmart/admin/kategori/upload/csv");
            model.addAttribute("backActionUrl", "/kopmart/admin/kategori/list");
        } catch (Exception e) {
            UserHelper.errorLog("terjadi kesalahan saat memuat halaman upload csv!");
        }
        return "admin/category/upload/csv";
    }
}
