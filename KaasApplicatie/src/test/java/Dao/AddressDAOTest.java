/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;
import POJO.AddressPOJO;
import POJO.AddressTypePOJO;
import POJO.ClientPOJO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jasper Thielen
 */
public class AddressDAOTest {

    public AddressDAOTest() {
    }
    DomXML data = new DomXML();
    AddressDAO AddressDAO = new AddressDAO();
    private Connection connect;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        try {
            connect = Connector.getConnection();
            Statement statement = connect.createStatement();
            String query = "INSERT INTO Address (Streetname , HouseNumber, HouseNumberAddition, PostalCode, City) VALUES (Houtwal, 12 ,  , 1234AB , Vlissingen)";
            statement.executeUpdate(query);
            connect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @After
    public void after() {
        try {
            connect = Connector.getConnection();
            Statement statement = connect.createStatement();
            String query = "DELETE FROM Address";
            String query2 = "ALTER TABLE Address AUTO_INCREMENT = 1";   //why......? (tells the program to keep skipping to next id?)
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
     * Test of addAddress method, of class AddressDAO.
     */
    @Test
    public void testAddAddress() {
        AddressPOJO Address = new AddressPOJO();

        Address.setStreetName("telderslaan");
        Address.setHouseNumber(62);
        Address.setHouseNumberAddition("");
        Address.setPostalCode("3527 KH");
        Address.setCity("Utrecht");
        AddressDAO.addAddress(Address);

        try {
            connect = Connector.getConnection();
            String query = "SELECT * FROM Address WHERE AddressID = 2";
            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("telderslaan", resultset.getString(2));
            assertEquals(62, resultset.getInt(3));
            assertEquals("", resultset.getString(4));
            assertEquals("3527 KH", resultset.getString(5));
            assertEquals("Utrecht", resultset.getString(6));

            connect.close();
            resultset.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of getAddress method, of class AddressDAO.
     */
    @Test
    public void testGetAddress() {
        AddressPOJO address = new AddressPOJO();
        address.setAddressID(2);
        AddressPOJO test = AddressDAO.getAddress(address);

        assertEquals(2, test.getAddressID());
        assertEquals("telderslaan", test.getStreetName());
        assertEquals(62, test.getHouseNumber());
        assertEquals("", test.getHouseNumberAddition());
        assertEquals("3527 KH", test.getPostalCode());
        assertEquals("Utrecht", test.getCity());
    }

    /**
     * Test of getAddressWithClient method, of class AddressDAO.
     */
    @Test
    public void testGetAddressWithClient() {
    }

    //lookup how this changes
    /**
     * Test of getAllAddress method, of class AddressDAO.
     */
    @Test
    public void testGetAllAddress() {
        AddressDAO.getAllAddress();

        try {
            connect = Connector.getConnection();
            String query = "SELECT * FROM Address";
            Statement statement = connect.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();

            //iff not correct, increase count assertEquals 1 klopt wel rest niet?
            assertEquals(1, resultset.getInt(1));
            assertEquals("telderslaan", resultset.getString(2));
            assertEquals(62, resultset.getInt(3));
            assertEquals("", resultset.getString(4));
            assertEquals("3527 KH", resultset.getString(5));
            assertEquals("Utrecht", resultset.getString(6));

            connect.close();
            resultset.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Test of updateAddress method, of class AddressDAO.
     */
    @Test
    public void testUpdateAddress() {
    }

    /**
     * Test of deleteAddress method, of class AddressDAO.
     */
    @Test
    public void testDeleteAddress() {
    }

    /**
     * Test of addAddressType method, of class AddressDAO.
     */
    @Test
    public void testAddAddressType() {
    }

    /**
     * Test of getAllAddressType method, of class AddressDAO.
     */
    @Test
    public void testGetAllAddressType() {
    }

    /**
     * Test of getAddressType method, of class AddressDAO.
     */
    @Test
    public void testGetAddressType() {
    }

    /**
     * Test of updateAddressType method, of class AddressDAO.
     */
    @Test
    public void testUpdateAddressType() {
    }

    /**
     * Test of deleteAddressType method, of class AddressDAO.
     */
    @Test
    public void testDeleteAddressType() {
    }

}
