package com.xyz.bookstore.controller;

import com.xyz.bookstore.dto.ErrorDto;
import com.xyz.bookstore.exception.BookStoreBaseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookStoreBaseException.class)
    public ResponseEntity<Object> handleEntityNotFound(BookStoreBaseException e, HttpServletRequest request) {

        ErrorDto dto = new ErrorDto();
        dto.code = e.getCode();
        dto.httpStatus = e.getHttpStatus();
        dto.message = e.getMessage();
        dto.path = request.getServletPath();
        dto.httpMethod = request.getMethod();

        return new ResponseEntity<>(dto, dto.httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAuthenticationException(AccessDeniedException ex, HttpServletRequest request) {

        ErrorDto dto = new ErrorDto();
        dto.code = "UNAUTHORISED";
        dto.httpStatus = HttpStatus.UNAUTHORIZED;
        dto.message = ex.getMessage();
        dto.path = request.getServletPath();
        dto.httpMethod = request.getMethod();

        return new ResponseEntity<>(dto, dto.httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {

        ErrorDto dto = new ErrorDto();
        dto.code = "UNKNOWN_EXCEPTION";
        dto.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        dto.message = ex.getMessage();
        dto.path = request.getServletPath();
        dto.httpMethod = request.getMethod();

        return new ResponseEntity<>(dto, dto.httpStatus);
    }

}
