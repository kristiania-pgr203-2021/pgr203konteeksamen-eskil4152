package no.kristiania.exam.dao;

import no.kristiania.exam.dao.model.AbstractModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao <T extends AbstractModel> {
    protected final DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    public abstract String getSaveBookToAuth();
    public abstract String getRetrieveByQuestionIdString();
    public abstract String getRetrieveAllFromAuth();
    public abstract String getUpdateBookInAuth();

    public void save(T model, String statementString) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    statementString,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                setSaveColumns(model, statement);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    model.setId(resultSet.getLong("id"));
                }
            }
        }
    }

    public abstract void setSaveColumns(T model, PreparedStatement statement) throws SQLException;
    public abstract void setUpdateColumns(T model, PreparedStatement statement) throws SQLException;

    public void update(T model, String updateString) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    updateString,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                setUpdateColumns(model, statement);
                statement.executeUpdate();
            }
        }
    }

    public List<T> retrieveAll(String retrieveAllStatement) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    retrieveAllStatement, Statement.RETURN_GENERATED_KEYS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return mapAllFromResultSet(resultSet);
                }
            }
        }
    }

    public T retrieveById(long id, String retrieveStatement) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    retrieveStatement, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return mapFromResultSet(resultSet);
                }
            }
        }
    }

    public List<T> retrieveAllById(long id, String retrieveStatement) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    retrieveStatement, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return mapAllFromResultSet(resultSet);
                }
            }
        }
    }

    protected List<T> mapAllFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<T> resultList = new ArrayList<>();
        while (rs.next()) {
            resultList.add(mapFromResultSet(rs));
        }
        return resultList;
    }

    protected abstract T mapFromResultSet(ResultSet resultSet) throws SQLException;
}
