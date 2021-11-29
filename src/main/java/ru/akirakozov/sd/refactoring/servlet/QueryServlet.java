package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.DocumentBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov, despairedController
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
                printWithHeader(productDAO::getMaxPrice, "Product with max price: ", response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if ("min".equals(command)) {
            try {
                printWithHeader(productDAO::getMinPrice, "Product with min price: ", response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                printWithHeader(productDAO::getSumPrice, "Summary price: ", response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            printWithHeader(productDAO::getCount, "Number of products: ", response);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private <T> void printWithHeader(SupplierWithIO<T> s, String header, HttpServletResponse response) {
        try {
            T result = s.get();
            DocumentBuilder document = new DocumentBuilder();
            document.addH1(header);
            document.addLine(result.toString());
            response.getWriter().println(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    private interface SupplierWithIO<T> {
        T get() throws SQLException, IOException;
    }
}
