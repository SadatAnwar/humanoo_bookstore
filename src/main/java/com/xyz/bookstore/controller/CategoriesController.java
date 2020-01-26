package com.xyz.bookstore.controller;

import com.xyz.bookstore.builder.CategoryBuilder;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.CategoryDto;
import com.xyz.bookstore.mapper.CategoryMapper;
import com.xyz.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    private final CategoryBuilder categoryBuilder;

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoriesController(CategoryService categoryService, CategoryBuilder categoryBuilder, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryBuilder = categoryBuilder;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createNewCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryBuilder.buildNewCategoryByName(categoryDto.name);

        return categoryMapper.toDto(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.safeDeleteCategory(id);
    }
}
