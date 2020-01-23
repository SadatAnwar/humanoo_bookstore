package com.xyz.bookstore.controller;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.dto.BookDto;
import com.xyz.bookstore.mapper.BookMapper;
import com.xyz.bookstore.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController
{
    private final BookService bookService;

    private final BookMapper bookMapper;


    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper)
    {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }


    @GetMapping
    public List<BookDto> getAllBooks()
    {
        return bookMapper.toDto(bookService.getAllBooks());
    }


    @PostMapping
    public BookDto addBook(@RequestBody BookDto bookDto)
    {
        Book book = bookMapper.toEntity(bookDto);

        return bookMapper.toDto(bookService.addNewBook(book));
    }
}
