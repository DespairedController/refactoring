package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    void insert(String name, long price) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    Product getMinPrice() throws SQLException;

    Product getMaxPrice() throws SQLException;

    int getSumPrice() throws SQLException;

    int getCount() throws SQLException;

    void dropAll() throws SQLException;
}
