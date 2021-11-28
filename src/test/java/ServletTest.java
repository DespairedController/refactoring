import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.dao.ProductDAOTestDB;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServletTest {
    private ProductDAO productDAO;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    StringWriter stringWriter;

    @Before
    public void setUp() throws SQLException {
        productDAO = new ProductDAOTestDB();
    }

    protected void setUpResponseMock() throws IOException {
        stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    @Test
    public void testGet() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet(productDAO);
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new GetProductsServlet(productDAO).doGet(request, response);
        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "apple\t100</br>\n" +
                        "banana\t50</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void testMax() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet(productDAO);
        when(request.getParameter("command")).thenReturn("max");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet(productDAO).doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "apple\t100</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void testMin() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet(productDAO);
        when(request.getParameter("command")).thenReturn("min");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet(productDAO).doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "banana\t50</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void testCount() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet(productDAO);
        when(request.getParameter("command")).thenReturn("count");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet(productDAO).doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "Number of products: \n" +
                        "2\n" +
                        "</body></html>\n");
    }

    @Test
    public void testSum() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet(productDAO);
        when(request.getParameter("command")).thenReturn("sum");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet(productDAO).doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "Summary price: \n" +
                        "150\n" +
                        "</body></html>\n");
    }
}
