package no.kristiania.exam.dao;

import no.kristiania.exam.Objects.Author;
import no.kristiania.exam.Objects.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends AbstractDao<Book>{

    private final String retrieveByBookName = "select * from books where book_name = ?";
    private final String retrieveAllB = "select * from books";
    private final String updateBook = "update books set book_name = ?, book_genre = ?, book_description = ?, book_author = ? where book_id = ?";

    public BookDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String getSaveBookToAuth() {
        return null;
    }

    @Override
    public String getRetrieveByBookIdString() {
        return null;
    }

    @Override
    public String getRetrieveAllFromAuth() {
        return null;
    }

    @Override
    public String getUpdateBookInAuth() {
        return null;
    }

    @Override
    public void setSaveColumns(Book model, PreparedStatement statement) throws SQLException {

    }

    @Override
    public void setUpdateColumns(Book model, PreparedStatement statement) throws SQLException {

    }

    @Override
    protected Book mapFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    public List<Book> listBooks() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from books"
            )){
                try (ResultSet rs = statement.executeQuery()){
                    ArrayList<Book> books = new ArrayList<>();
                    while (rs.next()){
                        books.add(readResultSet(rs));
                    }
                    return books;
                }
            }
        }
    }

    public void save (Book book) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(
                    "insert into books (book_name, book_genre, book_description, book_author) VALUES (?, ?, ?, ?) ON CONFLICT do nothing;" +
                            "update authors set author_books = (?) where author_name = (?)"
            )) {
                statement.setString(1, book.getBookName());
                statement.setString(2, book.getBookGenre());
                statement.setString(3, book.getBookDesc());
                statement.setString(4, book.getBook_authors());
                statement.setString(5, book.getBookName());
                statement.setString(6, book.getBook_authors());

                statement.execute();
            }
        }
    }

    public List<Book> authorBook(Author author) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(
                    "select book_name from books where book_author = (?)"
            )) {
                statement.setString(1, author.getName());

                statement.execute();

                try (ResultSet rs = statement.executeQuery()){
                    ArrayList<Book> books = new ArrayList<>();
                    while (rs.next()){
                        books.add(read(rs));
                    }
                    return books;
                }
            }
        }
    }

    private Book readResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookName(rs.getString("book_name"));
        book.setBookGenre(rs.getString("book_genre"));
        book.setBookDesc(rs.getString("book_description"));
        book.setBook_authors(rs.getString("book_author"));
        return book;
    }

    private Book read(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setBookName(rs.getString("book_name"));
        return b;
    }

    public void alter(Book book) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(
                    "UPDATE books set book_name = (?) where book_name = (?);" +
                            "UPDATE books set book_description = (?) where book_name = (?); " +
                            "UPDATE books set book_author = (?) where book_name = (?);" +
                            "UPDATE books set book_genre = (?) where book_name = (?)"
            )) {
                statement.setString(1, book.getNewName());
                statement.setString(2, book.getBookName());
                statement.setString(3, book.getNewDesc());
                statement.setString(4, book.getNewName());
                statement.setString(5, book.getNewAuthor());
                statement.setString(6, book.getNewName());
                statement.setString(7, book.getNewGenre());
                statement.setString(8, book.getNewName());

                statement.executeUpdate();
            }
        }
    }

    public void addAuthorToBook(Book book) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE books set book_author = (?) where book_name = (?)"
            )) {
                statement.setString(1, book.getNewAuthor());
                statement.setString(2, book.getBookName());

                statement.executeUpdate();
            }
        }
    }
}
