package com.xyz.bookstore.exception;

import javax.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException
{
    public CategoryNotFoundException(String categoryName)
    {
        super(String.format("Unable to find category with name:[%s]", categoryName));
    }
}
