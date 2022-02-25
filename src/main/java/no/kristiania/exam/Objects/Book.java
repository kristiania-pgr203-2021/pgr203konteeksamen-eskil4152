package no.kristiania.exam.Objects;

public class Book {
    private long id;
    private String bookName;
    private String bookDesc;
    private String newName;
    private String newDesc;

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    private String bookGenre;

    @Override
    public String toString() {
        return bookName;
    }

    private String newAuthor;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewDesc() {
        return newDesc;
    }

    public void setNewDesc(String newDesc) {
        this.newDesc = newDesc;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(String newAuthor) {
        this.newAuthor = newAuthor;
    }

    public String getBookGenre() {
        return bookGenre;
    }
}
