package com.irdaislakhuafa.kopmart.models.entities.converters;

import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryConverter extends AbstractBeanField<Category, String> {
    @Autowired
    private CategoryService categoryService;

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Category category = new Category("id", value, "description");
        System.out.println("\033\143");

        System.out.println(categoryService);
        // System.out.println(applicationContext);
        System.out.println(value.trim());
        try {
            category = categoryService.findByNameIgnoreCase(value).get();
            // System.out.println(category);
        } catch (Exception e) {
            UserHelper.errorLog("tidak ada kategori dengan nama \"" + value + "\"!");
        }
        return category;
    }

}
