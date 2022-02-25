package no.kristiania.exam.dao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AuthorDao extends AbstractDao<Author>{
    private final String saveBookToAuth = "insert into authors (author_books, book_id) values (?,?)";
    private final String retrieveBookIdAuth = "select * from authors where book_id = ?";
    private final String retrieveAllFromAuth = "select * from authors";
    private final String updateBookInAuth = "update authors set author_books = ? where id = ?";

    public AuthorDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String getSaveBookToAuth() {
        return saveBookToAuth;
    }

    @Override
    public void setSaveColumns(Author author, PreparedStatement statement) throws SQLException {
        statement.setString(1, author.getAuthorId());
        statement.setLong(2, author.getBookId());
    }

    @Override
    public void setUpdateColumns(Author author, PreparedStatement statement) throws SQLException {
        // NEVER USED
        statement.setString(1, author.getAuthorBooks());
        statement.setString(2, Long.toString(author.getId()));
    }

    @Override
    public String getUpdateBookInAuth() {
        // NEVER USED
        return updateBookInAuth;
    }

    @Override
    public String getRetrieveByQuestionIdString() {
        return retrieveBookIdAuth;
    }

    @Override
    public String getRetrieveAllFromAuth() {
        // NEVER USED
        return retrieveAllFromAuth;
    }



    @Override
    protected Author mapFromResultSet(ResultSet rs) throws SQLException {
        Author a = new Author(rs.getString("author_books"), rs.getLong("book_id"));
        a.setId(rs.getLong("id"));
        return a;
    }

    public List<Author> retrieveByQuestionId(long questionId, String retrieveByQuestionIdString) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    retrieveByQuestionIdString, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, questionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return mapAllFromResultSet(resultSet);
                }
            }
        }
    }
}
