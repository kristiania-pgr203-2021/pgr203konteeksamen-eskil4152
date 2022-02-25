package no.kristiania.exam.Objects;

import java.util.List;

public class Book {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String book_name;
    private String book_desc;
    private List<Author> book_authors;

    @Override
    public String toString() {
        return "Book{" +
                "book_name='" + book_name + '\'' +
                ", book_desc='" + book_desc + '\'' +
                ", book_authors=" + book_authors +
                '}';
    }

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

    public List<Author> getBook_authors() {
        return book_authors;
    }

    public void setBook_authors(List<Author> book_authors) {
        this.book_authors = book_authors;
    }
}
