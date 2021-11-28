package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.BiConsumer;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    public static final String DB = "jdbc:sqlite:test.db";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            tryRequest(QueryServlet::pricePrinter, response, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "<h1>Product with max price: </h1>");
        } else if ("min".equals(command)) {
            tryRequest(QueryServlet::pricePrinter, response, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "<h1>Product with min price: </h1>");
        } else if ("sum".equals(command)) {
            tryRequest(QueryServlet::singleValuePrinter, response, "SELECT SUM(price) FROM PRODUCT", "Summary price: ");
        } else if ("count".equals(command)) {
            tryRequest(QueryServlet::singleValuePrinter, response, "SELECT COUNT(*) FROM PRODUCT", "Number of products: ");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void tryRequest(BiConsumer<ResultSet, HttpServletResponse> function, HttpServletResponse response, String query, String header) {
        try {
            try (Connection c = DriverManager.getConnection(DB)) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                response.getWriter().println("<html><body>");
                response.getWriter().println(header);

                function.accept(rs, response);
                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void pricePrinter(ResultSet rs, HttpServletResponse response) {
        while (true) {
            try {
                if (!rs.next()) break;
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void singleValuePrinter(ResultSet rs, HttpServletResponse response) {
        try {
            if (rs.next()) {
                response.getWriter().println(rs.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
