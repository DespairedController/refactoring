package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;

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

    private final ProductDAO productDAO;

    public QueryServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                Product maxPrice = productDAO.getMaxPrice();
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                response.getWriter().println(maxPrice.toString());
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if ("min".equals(command)) {
            try {
                Product minPrice = productDAO.getMinPrice();
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                response.getWriter().println(minPrice.toString());
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                int sum = productDAO.getSumPrice();
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                response.getWriter().println(sum);
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int count = productDAO.getCount();
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                response.getWriter().println(count);
                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
