package ru.akirakozov.sd.refactoring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class Product {
    public String name;
    public int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static Product fromResultSet(ResultSet rs) throws SQLException {
        return new Product(rs.getString("name"), rs.getInt("price"));
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}\t{1}</br>", this.name, this.price);
    }
}
