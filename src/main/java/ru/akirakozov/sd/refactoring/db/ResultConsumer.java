package ru.akirakozov.sd.refactoring.db;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultConsumer<R> {
    R apply(ResultSet rs) throws SQLException;
}
