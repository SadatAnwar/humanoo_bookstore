package com.xyz.bookstore.builder;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    private final CategoryService categoryService;

    @Autowired
    public CategoryBuilder(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category loadCategoryByName(String categoryName) {
        return categoryService.loadByName(categoryName);
    }

    public Category buildNewCategoryByName(String categoryName) {
        return new Category(categoryName);
    }
}
