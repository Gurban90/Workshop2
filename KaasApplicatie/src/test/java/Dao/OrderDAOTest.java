/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;
import POJO.ClientPOJO;
import POJO.OrderPOJO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jasper Thielen
 */
public class OrderDAOTest {

    public OrderDAOTest() {
    }
    DomXML data = new DomXML();
    OrderDAO OrderDAO = new OrderDAO();
    private Connection connect;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        try {
            connect = Connector.getConnection();
            String query = "INSERT INTO Order () VALUES ()";
            Statement statement = connect.createStatement();

            statement.executeUpdate(query);
            connect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void after() {
        try {
            Connection connect = Connector.getConnection();

            Statement statement = connect.createStatement();
            String query = "DELETE FROM Order";
            String query2 = "ALTER TABLE Order AUTO_INCREMENT = 1";
            statement.addBatch(query);
            statement.addBatch(query2);
            statement.executeBatch();
            //connect.commit(); is autocommit on?
            connect.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of addOrder method, of class OrderDAO.
     */
    @Test
    public void testAddOrder() {

    }

    /**
     * Test of getAllOrder method, of class OrderDAO.
     */
    @Test
    public void testGetAllOrder() {

    }

    /**
     * Test of getOrder method, of class OrderDAO.
     */
    @Test
    public void testGetOrder() {

    }

    /**
     * Test of getOrderWithClient method, of class OrderDAO.
     */
    @Test
    public void testGetOrderWithClient() {

    }

    /**
     * Test of updateOrder method, of class OrderDAO.
     */
    @Test
    public void testUpdateOrder() {

    }

    /**
     * Test of deleteOrder method, of class OrderDAO.
     */
    @Test
    public void testDeleteOrder() {

    }

}
