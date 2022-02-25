create table books
(
    book_id          serial primary key,
    book_name        varchar(1000),
    book_genre       varchar(1000),
    book_description varchar(1000),
    book_author      varchar(10000)
);

insert into books (book_name, book_genre, book_description, book_author) values
                                                                                ('The old man and the sea', 'Thriller', 'A classic', 'Hemingway'),
                                                                                ('The lord of the rings', 'Action', 'Good movie as well', 'J.R.R Tolkien')