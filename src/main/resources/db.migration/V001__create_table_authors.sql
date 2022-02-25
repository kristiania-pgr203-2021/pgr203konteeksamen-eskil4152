create table authors
(
    author_id serial primary key,
    author_firstname varchar(50),
    author_lastname varchar(50),
    author_age integer,
    author_books varchar(1000)
);

insert into authors (author_firstname, author_lastname, author_age) values
                                                          ('Lars', 'Bjornbak', 25),
                                                          ('Benedict', 'Cumberbatch', 38),
                                                          ('Batman', 'the First', 62);