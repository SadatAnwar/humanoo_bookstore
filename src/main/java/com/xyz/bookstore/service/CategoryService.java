package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.exception.CategoryNotEmptyException;
import com.xyz.bookstore.exception.CategoryNotFoundException;
import com.xyz.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category loadByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }

    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void safeDeleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        if (category.getBooks().size() > 0) {
            throw new CategoryNotEmptyException(id);
        }

        categoryRepository.deleteById(id);
    }
}
