package no.kristiania.exam.dao;

import java.util.List;

public class Book {
    private long id;
    private String book_name;
    private String book_desc;
    private String new_name;
    private String new_desc;
    private String new_author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", book_name='" + book_name + '\'' +
                ", book_desc='" + book_desc + '\'' +
                ", new_name='" + new_name + '\'' +
                ", new_desc='" + new_desc + '\'' +
                ", new_author='" + new_author + '\'' +
                ", book_authors='" + book_authors + '\'' +
                '}';
    }


    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getNew_desc() {
        return new_desc;
    }

    public void setNew_desc(String new_desc) {
        this.new_desc = new_desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook_authors() {
        return book_authors;
    }

    public void setBook_authors(String book_authors) {
        this.book_authors = book_authors;
    }

    private String book_authors;

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_desc() {
        return book_desc;
    }

    public void setBook_desc(String book_desc) {
        this.book_desc = book_desc;
    }

    public String getNew_author() {
        return new_author;
    }

    public void setNew_author(String new_author) {
        this.new_author = new_author;
    }
}
