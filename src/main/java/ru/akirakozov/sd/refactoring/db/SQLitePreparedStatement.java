package ru.akirakozov.sd.refactoring.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLitePreparedStatement implements PreparedStatement {
    private final Connection connection;
    private final java.sql.PreparedStatement preparedStatement;

    SQLitePreparedStatement(String fileName, String query) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        this.preparedStatement = connection.prepareStatement(query);
    }


    @Override
    public void executeUpdate() throws SQLException {
        preparedStatement.executeUpdate();
    }

    @Override
    public <T> T executeQueryOne(ResultConsumer<T> consumer) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return consumer.apply(rs);
            } else {
                return null;
            }
        }
    }

    @Override
    public <T> List<T> executeQueryAll(ResultConsumer<T> consumer) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            List<T> objects = new ArrayList<>();
            while (rs.next()) {
                objects.add(consumer.apply(rs));
            }
            return objects;
        }
    }

    @Override
    public void close() throws SQLException {
        preparedStatement.close();
        connection.close();
    }
}
