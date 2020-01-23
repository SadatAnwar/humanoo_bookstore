create schema bookstore;

create table bookstore.books (
    id IDENTITY NOT NULL PRIMARY KEY,
    isbn VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    author_name VARCHAR,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

create table bookstore.categories (
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);


create table bookstore.book_categories (
    book_id NUMBER,
    category_id NUMBER,

    CONSTRAINT FK_BOOK_ID FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id) REFERENCES categories(id)
);
