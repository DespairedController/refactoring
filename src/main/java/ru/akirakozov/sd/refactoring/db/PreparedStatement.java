package ru.akirakozov.sd.refactoring.db;

import java.sql.SQLException;
import java.util.List;

public interface PreparedStatement extends AutoCloseable {

    void executeUpdate() throws SQLException;

    <T> T executeQueryOne(ResultConsumer<T> consumer) throws SQLException;

    <T> List<T> executeQueryAll(ResultConsumer<T> consumer) throws SQLException;

    @Override
    void close() throws SQLException;
}
