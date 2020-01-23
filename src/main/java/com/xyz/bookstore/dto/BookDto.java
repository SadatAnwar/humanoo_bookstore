package com.xyz.bookstore.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookDto
{
    public Long id;

    public String isbn;

    public String name;

    public String authorName;

    public List<String> categories;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;
}
