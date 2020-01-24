create schema bookstore;

create table bookstore.books (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    author_name VARCHAR,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

create table bookstore.categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);


create table bookstore.book_categories (
    book_id BIGINT,
    category_id BIGINT,

    CONSTRAINT FK_BOOK_ID FOREIGN KEY (book_id) REFERENCES bookstore.books(id),
    CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id) REFERENCES bookstore.categories(id)
);
