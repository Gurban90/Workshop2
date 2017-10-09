/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;

import POJO.ClientPOJO;
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
public class ClientDAOTest {

    DomXML data = new DomXML();

    ClientDAO clientdao = new ClientDAO();

    private Connection connection;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        //Database vullen
        try {
            connection = Connector.getConnection();
            String query = "INSERT INTO Client (ClientID, FirstName, LastName, E-mail) VALUES (1, 'Bob', Laan, Bob@laan.com)";
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testAddClient() {
        ClientPOJO client = new ClientPOJO();
        client.setFirstName("Jan");
        client.setLastName("Jansen");
        client.setEMail("j@mail.com");

        clientdao.addClient(client);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Client WHERE ClientID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Jan", resultset.getString(2));
            assertEquals("Jansen", resultset.getString(3));
            assertEquals("j@mail.com", resultset.getString(4));
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
    public void testGetAllClient() {
        clientdao.getAllClient();

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Bob", resultset.getString(2));
            assertEquals("Laan", resultset.getString(3));
            assertEquals("Bob@laan.com", resultset.getString(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetClient() {
        ClientPOJO client2 = new ClientPOJO();
        client2.setClientID(1);
        clientdao.getClient(client2);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account WHERE ClientID=1";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Bob", resultset.getString(2));
            assertEquals("Laan", resultset.getString(3));
            assertEquals("Bob@laan.com", resultset.getString(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testUpdateClient() {
        ClientPOJO client3 = new ClientPOJO();
        client3.setClientID(2);
        client3.setFirstName("Jan");
        client3.setLastName("Hoor");
        client3.setEMail("j@mail.com");

        clientdao.updateClient(client3);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Client WHERE AccountID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Jan", resultset.getString(2));
            assertEquals("Hoor", resultset.getString(3));
            assertEquals("j@mail.com", resultset.getString(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testDeleteClient() {

        ClientPOJO client4 = new ClientPOJO();
        client4.setClientID(2);

        clientdao.deleteClient(client4);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Client WHERE ClientID = 2";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(null, resultset.getString("FirstName"));
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
            String query = "DELETE FROM Client";
            statement.addBatch(query);
            String query2 = "ALTER TABLE Client AUTO_INCREMENT = 1";
            statement.addBatch(query2);
            statement.executeBatch();
            connection.commit();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
