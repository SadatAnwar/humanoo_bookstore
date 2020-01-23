package com.xyz.bookstore.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "books", catalog = "bookstore")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column
    private String authorName;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_categories",
        catalog = "bookstore",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;


    protected Book()
    {

    }


    public Book(String isbn, String name, String authorName, List<Category> categories)
    {
        this.isbn = isbn;
        this.name = name;
        this.authorName = authorName;
        this.categories = categories;
    }


    public Long getId()
    {
        return id;
    }


    public String getIsbn()
    {
        return isbn;
    }


    public String getName()
    {
        return name;
    }


    public String getAuthorName()
    {
        return authorName;
    }


    public List<Category> getCategories()
    {
        return categories;
    }


    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }
}
