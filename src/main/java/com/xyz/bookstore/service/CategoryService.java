package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.exception.CategoryNotFoundException;
import com.xyz.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }


    public Category findByName(String categoryName)
    {
        return categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }
}
