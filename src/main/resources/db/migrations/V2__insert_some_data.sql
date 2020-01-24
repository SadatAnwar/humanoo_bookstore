INSERT INTO bookstore.categories (name)
VALUES ('Children'),
       ('Fiction'),
       ('Self Help');


INSERT INTO bookstore.books (isbn, name, author_name)
VALUES ('ISBN-121231231231', 'Harry Potter book 1', 'J.K. Rowling');

INSERT INTO bookstore.book_categories(book_id, category_id)
select b.id, c.id
from bookstore.books b
         cross join bookstore.categories c
where b.name = 'Harry Potter book 1'
  and (c.name = 'Children' OR c.name = 'Fiction');
