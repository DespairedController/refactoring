package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.DocumentBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov, despairedController
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public GetProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> productList = productDAO.getAllProducts();
            DocumentBuilder document = new DocumentBuilder();
            document.addLines(productList.stream().map(Product::toString).collect(Collectors.toList()));
            response.getWriter().println(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
