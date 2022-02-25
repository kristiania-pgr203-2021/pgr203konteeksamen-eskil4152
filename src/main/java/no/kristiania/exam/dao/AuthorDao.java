package no.kristiania.exam.dao;

import no.kristiania.exam.Objects.Author;

import javax.sql.DataSource;
import java.sql.*;

public class AuthorDao extends AbstractDao<Author>{

    private final String saveAuthor = "insert into authors (author_id, author_firstname, author_lastname, author_age, author_books) values (?, ?, ?, ?, ?)";
    private final String retrieveByAuthorId = "select * from authors where id = ?";
    private final String retrieveAllA = "select * from authors";
    private final String updateAuthor = "update authors set author_firstname = ?, author_lastname = ?, author_age = ?, author_books = ? where id = ?";

    public AuthorDao(DataSource dataSource) {
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

    public void setSaveColumns(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getFirst_name());
        statement.setString(2, author.getLast_name());
        statement.setInt(3, author.getAge());
        statement.setString(4, author.getBooks());
    }

    public void setUpdateColumns(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getFirst_name());
        statement.setString(2, author.getLast_name());
        statement.setInt(3, author.getAge());
        statement.setString(4, author.getBooks());
        statement.setLong(5, author.getId());
    }

    @Override
    public Author mapFromResultSet(ResultSet rs) throws SQLException {
        Author a = new Author(rs.getLong("author_id"), rs.getString("author_books"));
        a.setId(rs.getLong("author_id"));
        a.setBooks(rs.getString("author_books"));
        return a;
    }

}

