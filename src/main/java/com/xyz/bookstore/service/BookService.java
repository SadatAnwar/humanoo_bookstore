package com.xyz.bookstore.service;

import com.xyz.bookstore.domain.Book;
import com.xyz.bookstore.exception.BookNotFoundException;
import com.xyz.bookstore.repository.BooksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class BookService {
    private static final Logger LOGGER = getLogger(BookService.class);

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        Page<Book> books = booksRepository.findAll(pageable);
        LOGGER.debug("Found {} books", books.getTotalElements());

        return books;
    }

    @Transactional
    public Book addNewBook(Book book) {
        return booksRepository.save(book);
    }

    public Book loadBookById(Long id) {
        return findBookById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public void deleteBookById(Long id) {
        Book book = loadBookById(id);

        booksRepository.delete(book);
    }

    private Optional<Book> findBookById(Long id) {
        return booksRepository.findById(id);
    }
}
