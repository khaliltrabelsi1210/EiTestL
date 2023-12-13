package fr.troisIl.evaluation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private Database db = null;
    private ProductService productService;

    private int countBefore = 0;

    @Before
    public void setUp() throws SQLException {
        String testDatabaseFileName = "product.db";

        // reset la BDD avant le test
        File file = new File(testDatabaseFileName);
        file.delete();

        db = new Database(testDatabaseFileName);
        db.createBasicSqlTable();

        productService = new ProductService(db);

        countBefore = count();
    }

    /**
     * Compte les produits en BDD
     *
     * @return le nombre de produit en BDD
     */
    private int count() throws SQLException {
        ResultSet resultSet = db.executeSelect("Select count(*) from Product");
        assertNotNull(resultSet);
        return resultSet.getInt(1);
    }

    @Test
    public void testInsert() throws SQLException {
        Product p = new Product("label",2);
        Product produitIp=productService.insert(p);
        System.out.println(produitIp.getLabel());
        assertEquals(produitIp,p);
    }

    @Test
    public void testUpdate() throws SQLException {
       Product p= productService.findById(1);
       System.out.println(p.getLabel());
       p.setLabel("label2");
       p.setQuantity(10);
       Product p_updated=productService.update(p);
       assertEquals(p,p_updated);
    }

    @Test
    public void testFindById() throws SQLException {
        Product p1=productService.findById(1);
        Product p2=productService.findById(1);
        System.out.println(p1.getLabel());
        assertEquals(p2.getId(),p1.getId());
    }

    @Test
    public void testDelete() throws SQLException {
        Product p=productService.findById(count());
        productService.delete(p.getId());
        productService.findById(p.getId());
        assertNull(p);
    }

}
