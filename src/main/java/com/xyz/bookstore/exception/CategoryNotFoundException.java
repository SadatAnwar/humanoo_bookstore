package com.xyz.bookstore.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BookStoreBaseException {

    private static final String MESSAGE_TEMPLATE_CATEGORY_NAME = "Unable to find category with name:[%s]";

    private static final String MESSAGE_TEMPLATE_CATEGORY_ID = "Unable to find category with Id:[%s]";

    private static final String CODE = "CATEGORY_NOT_FOUND";

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public CategoryNotFoundException(String categoryName) {
        super(String.format(MESSAGE_TEMPLATE_CATEGORY_NAME, categoryName), CODE, HTTP_STATUS);
    }

    public CategoryNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE_CATEGORY_ID, id), CODE, HTTP_STATUS);
    }
}
