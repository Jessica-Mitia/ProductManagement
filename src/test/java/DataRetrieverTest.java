import com.example.productmanagement.db.DBConnection;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.service.DataRetriever;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataRetrieverTest {
    @Test
    void testDBConnection() throws SQLException {
        DBConnection db = new DBConnection();
        Connection con = db.getDBConnection();
        assertNotNull(con);
        assertFalse(con.isClosed());
    }

    @Test
    void getAllCategoriesTest() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();
        List<Category> expected = new ArrayList<>();
        expected.add(new Category(1, "Informatique"));
        expected.add(new Category(2, "Téléphonie"));
        expected.add(new Category(3, "Audio"));
        expected.add(new Category(4, "Accessoires"));
        expected.add(new Category(5,"Informatique"));
        expected.add(new Category(6, "Bureau"));
        expected.add(new Category(7, "Mobile"));

        List<Category> categories = dataRetriever.getAllCategories();

        assertEquals(expected, categories);
    }

    @Test
    void  getProductListTest() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductList(1, 4);
        assertEquals(4, products.size());
        assertEquals("Laptop Dell XPS", products.get(0).getName());

        List<Product> products2 = dataRetriever.getProductList(2, 3);
        assertEquals(3, products2.size());
        assertEquals("Clavier Logitech", products2.get(0).getName());
    }

    @Test
    void getProductByCriteriaTest() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductsByCriteria("Dell", null, null, null);
        assertEquals(1, products.size());
        assertEquals("Laptop Dell XPS", products.get(0).getName());

        List<Product> products2 = dataRetriever.getProductsByCriteria(null, "Informatique", null, null);
        assertEquals(2, products2.size());
        assertEquals("Ecran Samsung 27", products2.get(1).getName());
    }

    @Test
    void getProductByCriteriaTest2() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductsByCriteria(null, null, null, null, 2, 3);
        assertEquals(3, products.size());
        assertEquals("Casque Sony WH1000", products.get(0).getName());
        assertEquals("Clavier Logitech", products.get(1).getName());

        List<Product> products2 = dataRetriever.getProductsByCriteria("IPhone", null, null, null, 1, 5);
        assertEquals(2, products2.size());
        assertEquals("IPhone 13", products2.get(0).getName());
    }
}
