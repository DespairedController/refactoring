package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.db.Database;
import ru.akirakozov.sd.refactoring.db.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

public class ProductDAOTestDB implements ProductDAO {
    private final Database DB = new Database("test.db");

    public ProductDAOTestDB() throws SQLException {
        DB.create();
    }

    @Override
    public void insert(String name, long price) throws SQLException {
        try (PreparedStatement preparedStatement =
                     DB.getPreparedStatement(MessageFormat.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"{0}\", \"{1}\");", name, price))) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("SELECT * FROM PRODUCT")) {
            return preparedStatement.executeQueryAll(Product::fromResultSet);
        }
    }

    @Override
    public Product getMinPrice() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1")) {
            return preparedStatement.executeQueryOne(Product::fromResultSet);
        }
    }

    @Override
    public Product getMaxPrice() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1")) {
            return preparedStatement.executeQueryOne(Product::fromResultSet);
        }
    }

    private static int getInt(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    @Override
    public int getSumPrice() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("SELECT SUM(price) FROM PRODUCT")) {
            return preparedStatement.executeQueryOne(ProductDAOTestDB::getInt);
        }
    }

    @Override
    public int getCount() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("SELECT COUNT(*) FROM PRODUCT")) {
            return preparedStatement.executeQueryOne(ProductDAOTestDB::getInt);
        }
    }

    @Override
    public void dropAll() throws SQLException {
        try (PreparedStatement preparedStatement = DB.getPreparedStatement("DROP TABLE IF EXISTS PRODUCT")) {
            preparedStatement.executeUpdate();
        }
    }
}
