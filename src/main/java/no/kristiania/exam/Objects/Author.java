package no.kristiania.exam.Objects;


public class Author {

    private long id;
    private String name;
    private int age;
    private String books;
    private String newName;
    private int newAge;
    private String newBooks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getNewBooks() {
        return newBooks;
    }

    public void setNewBooks(String newBooks) {
        this.newBooks = newBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public int getNewAge() {
        return newAge;
    }

    public void setNewAge(int newAge) {
        this.newAge = newAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}
