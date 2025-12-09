import com.example.productmanagement.db.DBConnection;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.service.DataRetriever;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataRetrieverTest {
    @Test
    void testDBConnection() throws SQLException {
        DBConnection db = new DBConnection();
        Connection con = db.getDBConnection();
        Assertions.assertNotNull(con);
        Assertions.assertFalse(con.isClosed());
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

        Assertions.assertEquals(expected, categories);
    }

    @Test
    void  getProductListTest() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductList(1, 4);
        Assertions.assertEquals(4, products.size());
        Assertions.assertEquals("Laptop Dell XPS", products.getFirst().getName());

        List<Product> products2 = dataRetriever.getProductList(2, 3);
        Assertions.assertEquals(3, products2.size());
        Assertions.assertEquals("Clavier Logitech", products2.getFirst().getName());
    }

    @Test
    void getProductByCriteriaTest() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductsByCriteria("Dell", null, null, null);
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals("Laptop Dell XPS", products.getFirst().getName());

        List<Product> products2 = dataRetriever.getProductsByCriteria(null, "Informatique", null, null);
        Assertions.assertEquals(2, products2.size());
        Assertions.assertEquals("Ecran Samsung 27", products2.get(1).getName());
    }

    @Test
    void getProductByCriteriaTest2() throws SQLException {
        DataRetriever dataRetriever = new DataRetriever();

        List<Product> products = dataRetriever.getProductsByCriteria(null, null, null, null, 2, 3);
        Assertions.assertEquals(3, products.size());
        Assertions.assertEquals("Casque Sony WH1000", products.getFirst().getName());
        Assertions.assertEquals("Clavier Logitech", products.get(1).getName());

        List<Product> products2 = dataRetriever.getProductsByCriteria("IPhone", null, null, null, 1, 5);
        Assertions.assertEquals(2, products2.size());
        Assertions.assertEquals("IPhone 13", products2.getFirst().getName());
    }
}
