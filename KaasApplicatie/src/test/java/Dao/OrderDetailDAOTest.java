/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;
import POJO.CheesePOJO;

import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.sql.Connection;
import java.sql.ResultSet;
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
 * @author Gerben
 */
public class OrderDetailDAOTest {

    DomXML data = new DomXML();

    OrderDetailDAO orderdetaildao = new OrderDetailDAO();

    private Connection connection;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        //Database vullen
        try {
            connection = Connector.getConnection();
            String query = "INSERT INTO OrderDetail (OrderDetailID, Quantity, Cheese_CheeseID, Order_OrderID) VALUES (1, 8, 1, 1)";
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testAddOrderDetail() {
        OrderDetailPOJO orderdetail = new OrderDetailPOJO();
        CheesePOJO cheese = new CheesePOJO();
        cheese.setCheeseID(2);
        OrderPOJO order = new OrderPOJO();
        order.setOrderID(2);
        orderdetail.setQuantity(3);
        orderdetail.setCheese(cheese);
        orderdetail.setOrder(order);

        orderdetaildao.addOrderDetail(orderdetail);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM OrderDetail WHERE OrderDetailID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(3, resultset.getInt(2));
            assertEquals(2, resultset.getInt(3));
            assertEquals(2, resultset.getInt(4));
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
    public void testGetAllOrderDetail() {
        orderdetaildao.getAllOrderDetail();

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM OrderDetail";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(8, resultset.getInt(2));
            assertEquals(1, resultset.getInt(3));
            assertEquals(1, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetOrderDetail() {
        OrderDetailPOJO orderdetail2 = new OrderDetailPOJO();
        orderdetail2.setOrderDetailID(1);
        orderdetaildao.getOrderDetail(orderdetail2);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM OrderDetail WHERE OrderDetailID=1";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(8, resultset.getInt(2));
            assertEquals(1, resultset.getInt(3));
            assertEquals(1, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testUpdateClient() {
        OrderDetailPOJO orderdetail3 = new OrderDetailPOJO();
        CheesePOJO cheese = new CheesePOJO();
        cheese.setCheeseID(2);
        OrderPOJO order = new OrderPOJO();
        order.setOrderID(2);
        orderdetail3.setOrderDetailID(2);
        orderdetail3.setQuantity(88);
        orderdetail3.setCheese(cheese);
        orderdetail3.setOrder(order);

        orderdetaildao.updateOrderDetail(orderdetail3);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM OrderDetail WHERE OrderDetailID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(88, resultset.getInt(2));
            assertEquals(2, resultset.getInt(3));
            assertEquals(2, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testDeleteClient() {

        OrderDetailPOJO orderdetail4 = new OrderDetailPOJO();
        orderdetail4.setOrderDetailID(2);

        orderdetaildao.deleteOrderDetail(orderdetail4);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM OrderDetail WHERE OrderDetailID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(null, resultset.getInt("Quantity"));
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
            String query = "DELETE FROM OrderDetail";
            statement.addBatch(query);
            String query2 = "ALTER TABLE OrderDetail AUTO_INCREMENT = 1";
            statement.addBatch(query2);
            statement.executeBatch();
            connection.commit();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
