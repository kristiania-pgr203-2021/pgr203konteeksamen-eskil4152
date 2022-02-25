create table books
(
    book_id serial primary key,
    book_name varchar(1000),
    book_description varchar(1000),
    book_authors varchar(10000)
);