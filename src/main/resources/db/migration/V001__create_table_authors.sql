create table authors
(
    author_id serial primary key,
    author_name varchar(100),
    author_age integer,
    author_books varchar(1000)
);

insert into authors (author_name, author_age) values
                                                          ('Lars Bjornbak', 25),
                                                          ('Benedict Cumberbatch', 38),
                                                          ('Batman the First', 62);