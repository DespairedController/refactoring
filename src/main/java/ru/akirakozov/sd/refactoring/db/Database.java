package ru.akirakozov.sd.refactoring.db;

import java.sql.SQLException;

public class Database {
    private final String name;

    public Database(String name) {
        this.name = name;
    }

    public void create() throws SQLException {
        try (PreparedStatement createStatement =
                     getPreparedStatement("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)")) {
            createStatement.executeUpdate();
        }
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return new SQLitePreparedStatement(name, query);
    }
}
