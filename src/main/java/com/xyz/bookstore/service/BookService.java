package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.exception.BookNotFoundException;
import com.xyz.bookstore.repository.BooksRepository;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class BookService
{
    private static final Logger LOGGER = getLogger(BookService.class);

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository)
    {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks()
    {
        List<Book> books = booksRepository.findAll();
        LOGGER.debug("Found {} books", books.size());
        return books;
    }

    public Book findBookById(Long id)
    {
        return booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public Book addNewBook(Book book)
    {
        return booksRepository.save(book);
    }
}
