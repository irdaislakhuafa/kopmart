package com.irdaislakhuafa.kopmart.controllers.admin;

import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            e.printStackTrace();
        }
        return "admin/category/new";
    }

    @PostMapping("/new")
    public String newCategory(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("description") String description) {

        try {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);

            categoryService.save(category);

            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/admin/kategori/new";
    }

    // get list
    @GetMapping("/list")
    public String listCategory(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("listCategory", categoryService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/category/list";
    }

    // edit
    @PostMapping("/edit")
    public String editCategory(Model model,
            @RequestParam("categoryId") String categoryId,
            RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findById(categoryId).get();
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            // model.addAttribute("category", category);

            redirectAttributes.addFlashAttribute("category", category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/kopmart/admin/kategori/edit";
    }

    @GetMapping("/edit")
    public String editCategory(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/kategori/edit";
    }
}