package no.kristiania.exam.dao;

import no.kristiania.exam.Objects.Author;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao extends AbstractDao<Author>{

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


    public void save(Author author) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String saveAuthor = "insert into authors (author_name, author_age, author_books) values (?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(
                    saveAuthor,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, author.getName());
                statement.setInt(2, author.getAge());
                statement.setString(3, author.getBooks());
                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    author.setId(resultSet.getLong("author_id"));
                }
            }
        }
    }

    public List<Author> listAll() throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from authors"
            )) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    ArrayList<Author> authors = new ArrayList<>();
                    while (resultSet.next()){
                        authors.add(mapFromResultSet(resultSet));
                    }
                    return authors;
                }
            }
        }
    }

    public void alter(Author author) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(
                    "UPDATE authors set author_name = (?) where author_name = (?);" +
                            "UPDATE authors set author_age = (?) where author_name = (?);"
            )) {
                statement.setString(1, author.getNewName());
                statement.setString(2, author.getName());
                statement.setInt(3, author.getNewAge());
                statement.setString(4, author.getNewName());

                statement.executeUpdate();
            }
        }
    }

    public void setSaveColumns(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getName());
        statement.setInt(2, author.getAge());
        statement.setString(3, author.getBooks());
    }

    public void setUpdateColumns(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getName());
        statement.setInt(2, author.getAge());
        statement.setString(3, author.getBooks());
    }

    @Override
    public Author mapFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setAge(rs.getInt("author_age"));
        author.setName(rs.getString("author_name"));
        author.setBooks(rs.getString("author_books"));
        return author;
    }

}

