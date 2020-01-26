package com.xyz.bookstore.mapper;

import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final BookMapper bookMapper;

    @Autowired
    public CategoryMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.id = category.getId();
        dto.name = category.getName();

        if (category.getBooks() != null) {
            dto.books = bookMapper.toDto(category.getBooks());
        }

        return dto;
    }
}
