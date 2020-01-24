package com.xyz.bookstore.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categories", schema = "bookstore")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Book> books;

    protected Category()
    {
    }

    public String getName()
    {
        return name;
    }
}
