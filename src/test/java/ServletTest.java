import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServletTest {
    private static final String DB_NAME = "jdbc:sqlite:test.db";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    StringWriter stringWriter;

    @Before
    public void setUp() throws SQLException {
        try (Connection c = DriverManager.getConnection(DB_NAME)) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }

    }

    protected void setUpResponseMock() throws IOException {
        stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    @Test
    public void testGet() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet();
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new GetProductsServlet().doGet(request, response);
        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "apple\t100</br>\n" +
                        "banana\t50</br>\n" +
                        "</body></html>\n");
    }

    @After
    public void dropDatabase() throws SQLException {
        try (Connection c = DriverManager.getConnection(DB_NAME)) {
            String sql = "DROP TABLE IF EXISTS PRODUCT";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    @Test
    public void testMax() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet();
        when(request.getParameter("command")).thenReturn("max");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet().doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "apple\t100</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void testMin() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet();
        when(request.getParameter("command")).thenReturn("min");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet().doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "banana\t50</br>\n" +
                        "</body></html>\n");
    }

    @Test
    public void testCount() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet();
        when(request.getParameter("command")).thenReturn("count");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet().doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "Number of products: \n" +
                        "2\n" +
                        "</body></html>\n");
    }

    @Test
    public void testSum() throws IOException {
        AddProductServlet addProductServlet = new AddProductServlet();
        when(request.getParameter("command")).thenReturn("sum");
        when(request.getParameter("name")).thenReturn("apple").thenReturn("banana");
        when(request.getParameter("price")).thenReturn("100").thenReturn("50");
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        addProductServlet.doGet(request, response);
        setUpResponseMock();
        new QueryServlet().doGet(request, response);

        assertEquals(stringWriter.toString(),
                "<html><body>\n" +
                        "Summary price: \n" +
                        "150\n" +
                        "</body></html>\n");
    }
}
