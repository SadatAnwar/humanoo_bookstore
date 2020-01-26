package com.xyz.bookstore.dto;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    public String message;

    public String code;

    public String path;

    public String httpMethod;

    public HttpStatus httpStatus;
}
