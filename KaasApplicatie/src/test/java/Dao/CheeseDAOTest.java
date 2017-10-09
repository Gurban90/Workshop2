/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;
import POJO.CheesePOJO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gerben
 */
public class CheeseDAOTest {
    //Gebruikt testDatabase, DomXML verwijst naar andere Database!!!!

    DomXML data = new DomXML();
    CheeseDAO cheesedao = new CheeseDAO();
    private Connection connection;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        //Database vullen
        try {
            connection = Connector.getConnection();
            String query = "INSERT INTO Cheese (Name, Price, Stock) VALUES ('Amsterdammer', 9, 8)";
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testAddCheese() {
        CheesePOJO cheese = new CheesePOJO();
        cheese.setCheeseName("Gouda");
        cheese.setPrice(new BigDecimal("13"));
        cheese.setStock(5);

        cheesedao.addCheese(cheese);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Cheese WHERE CheeseID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Gouda", resultset.getString(2));
            assertEquals(new BigDecimal("13"), resultset.getBigDecimal(3));
            assertEquals(5, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of getAllCheese method, of class CheeseDAO.
     */
    @Test
    public void testGetAllCheese() {
        cheesedao.getAllCheese();

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Cheese";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Amsterdammer", resultset.getString(2));
            assertEquals(new BigDecimal("13"), resultset.getBigDecimal(7));
            assertEquals(5, resultset.getInt(8));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of getCheese method, of class CheeseDAO.
     */
    @Test
    public void testGetCheese() {
        CheesePOJO cheese2 = new CheesePOJO();
        cheese2.setCheeseID(1);
        CheesePOJO testCheese = cheesedao.getCheese(cheese2);

        assertEquals(1, testCheese.getCheeseID());
        assertEquals("Amsterdammer", testCheese.getCheeseName());
        assertEquals(new BigDecimal("9"), testCheese.getPrice());
        assertEquals(8, testCheese.getStock());

    }

    @Test
    public void testUpdateCheese() {
        CheesePOJO cheese3 = new CheesePOJO();
        cheese3.setCheeseID(2);
        cheese3.setCheeseName("Gobo");
        cheese3.setPrice(new BigDecimal("14"));
        cheese3.setStock(6);

        cheesedao.updateCheese(cheese3);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Cheese WHERE CheeseID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Gobo", resultset.getString(2));
            assertEquals(new BigDecimal("14"), resultset.getBigDecimal(3));
            assertEquals(6, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testDeleteCheese() {

        CheesePOJO cheese4 = new CheesePOJO();
        cheese4.setCheeseID(2);

        cheesedao.deleteCheese(cheese4);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Cheese WHERE CheeseID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(null, resultset.getString("Name"));
            connection.close();
            resultset.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void after() {
        //Database Leegmaken

        try {
            Connection connection = Connector.getConnection();

            Statement statement = connection.createStatement();
            String query = "DELETE FROM Cheese";
            statement.addBatch(query);
            String query2 = "ALTER TABLE Cheese AUTO_INCREMENT = 1";
            statement.addBatch(query2);
            statement.executeBatch();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
