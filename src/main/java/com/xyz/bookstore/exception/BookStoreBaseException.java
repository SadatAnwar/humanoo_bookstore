package com.xyz.bookstore.exception;

import org.springframework.http.HttpStatus;

public abstract class BookStoreBaseException extends RuntimeException {

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    public BookStoreBaseException(String message, String code, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
