set schema bookstore;

INSERT INTO categories (id, name)
VALUES (1, 'Children'),
       (2, 'Fiction'),
       (3, 'Self Help');


INSERT INTO books (id, isbn, name, author_name)
VALUES (1, 'ISBN-121231231231', 'Harry Potter book 1', 'J.K. Rowling');

INSERT INTO book_categories(book_id, category_id)
VALUES (1, 1),
       (1, 2);

