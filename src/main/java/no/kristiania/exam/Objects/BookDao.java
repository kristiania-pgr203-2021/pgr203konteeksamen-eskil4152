package no.kristiania.exam.Objects;

import no.kristiania.exam.Http.Datasource;
import no.kristiania.exam.dao.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final DataSource dataSource;

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
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
                    "insert into books (book_name, book_description, book_authors) VALUES (?, ?, ?);" +
                            "insert into authors (author_books) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, book.getBook_name());
                statement.setString(2, book.getBook_desc());
                statement.setString(3, String.valueOf(book.getBook_authors()));
                statement.setString(4, book.getBook_name());

                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()){
                    resultSet.next();
                    book.setId(resultSet.getLong("id"));
                }
            }
        }
    }

    private Book readResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setBook_name(rs.getString("book_name"));
        book.setBook_desc(rs.getString("book_description"));
        book.setBook_authors(rs.getString("book_author"));
        return book;
    }
}
