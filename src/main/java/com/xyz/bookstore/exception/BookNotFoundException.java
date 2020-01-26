package com.xyz.bookstore.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BookStoreBaseException {

    private static final String MESSAGE_TEMPLATE = "Book with ID:[%s] not found";

    private static final String CODE = "BOOK_NOT_FOUND";

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public BookNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id), CODE, HTTP_STATUS);
    }
}
