package com.xyz.bookstore.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotEmptyException extends BookStoreBaseException {

    private static final String MESSAGE_TEMPLATE = "Unable to delete category Id:[%s] as there are books that belong to this category";

    private static final String CODE = "CATEGORY_NOT_EMPTY";

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public CategoryNotEmptyException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id), CODE, HTTP_STATUS);
    }
}
