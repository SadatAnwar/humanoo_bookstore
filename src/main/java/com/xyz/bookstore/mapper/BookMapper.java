package com.xyz.bookstore.mapper;

import com.xyz.bookstore.builder.CategoryBuilder;
import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.domain.Category;
import com.xyz.bookstore.dto.BookDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper
{
    private final CategoryBuilder categoryBuilder;

    @Autowired
    public BookMapper(CategoryBuilder categoryBuilder)
    {
        this.categoryBuilder = categoryBuilder;
    }

    public List<BookDto> toDto(List<Book> books)
    {
        return books.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public BookDto toDto(Book book)
    {
        BookDto dto = new BookDto();
        dto.id = book.getId();
        dto.isbn = book.getIsbn();
        dto.name = book.getName();
        dto.authorName = book.getAuthorName();
        dto.createdAt = book.getCreatedAt();
        dto.updatedAt = book.getUpdatedAt();
        dto.categories = new ArrayList<>();
        book.getCategories().forEach(c -> dto.categories.add(c.getName()));

        return dto;
    }

    public Book toEntity(BookDto bookDto)
    {
        List<Category> categories = bookDto.categories.stream()
            .map(categoryBuilder::fromName)
            .collect(Collectors.toList());

        return new Book(bookDto.isbn, bookDto.name, bookDto.authorName, categories);
    }
}
