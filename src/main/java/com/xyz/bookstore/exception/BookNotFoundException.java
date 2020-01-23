package com.xyz.bookstore.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException
{
    public BookNotFoundException(Long id)
    {
        super(String.format("Book with ID:[%s] not found", id));
    }
}
