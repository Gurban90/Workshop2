/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import DatabaseConnector.Connector;
import DatabaseConnector.DomXML;
import POJO.AccountPOJO;
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
public class AccountDAOTest {
     
    DomXML data = new DomXML();
    
    AccountDAO accountdao = new AccountDAO();
    
    private Connection connection;

    @Before
    public void before() {
        data.setDatabaseType("mysql");
        data.setConnectionType("jdbc");

        //Database vullen
        try {
            connection = Connector.getConnection();
            String query = "INSERT INTO Account (`AccountID`, `AccountName`, `AccountPassword`, `AccountStatus`) VALUES (2, 'Bob', 'Pass', 1)";
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij invullen");
        }
    }

    @Test
    public void testAddAccount() {
        AccountPOJO account = new AccountPOJO();
        account.setAccountName("Jan");
        account.setAccountPassword("Pass2");
        account.setAccountStatus(1);

        accountdao.addAccount(account);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account WHERE AccountID = 3";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Jan", resultset.getString(2));
            assertEquals("Pass2", resultset.getString(3));
            assertEquals(1, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij add");
        }
    }

    /**
     * Test of getAllCheese method, of class CheeseDAO.
     */
    @Test
    public void testGetAllAccount() {
        accountdao.getAllAccount();

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Bob", resultset.getString(2));
            assertEquals("Pass", resultset.getString(3));
            assertEquals(1, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij getall");
        }
    }

    @Test
    public void testGetAccount() {
        AccountPOJO account2 = new AccountPOJO();
        account2.setAccountID(2);
        AccountPOJO testAccount = accountdao.getAccount(account2);

        assertEquals(2, testAccount.getAccountID());
        assertEquals("Bob", testAccount.getAccountName());
        assertEquals("Pass", testAccount.getAccountPassword());
        assertEquals(1, testAccount.getAccountStatus());

    }

    @Test
    public void testUpdateAccount() {
        AccountPOJO account3 = new AccountPOJO();
        account3.setAccountID(3);
        account3.setAccountName("Jan");
        account3.setAccountPassword("Pass2");
        account3.setAccountStatus(1);

        accountdao.updateAccount(account3);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account WHERE AccountID = 3";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals("Jan", resultset.getString(2));
            assertEquals("Pass2", resultset.getString(3));
            assertEquals(1, resultset.getInt(4));
            connection.close();
            resultset.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij update");
        }
    }

    @Test
    public void testDeleteAccount() {

        AccountPOJO account4 = new AccountPOJO();
        account4.setAccountID(3);

        accountdao.deleteAccount(account4);

        try {
            connection = Connector.getConnection();
            String query = "SELECT * FROM Account WHERE AccountID = 3";
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            resultset.next();
            assertEquals(null, resultset.getString("AccountName"));
            connection.close();
            resultset.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij delete");
        }
    }

    @AfterClass
    public static void after() {
        //Database Leegmaken

        try {
            Connection connection = Connector.getConnection();

            Statement statement = connection.createStatement();
            String query = "DELETE FROM Account";
            statement.addBatch(query);
            String query2 = "ALTER TABLE Account AUTO_INCREMENT = 1";
            statement.addBatch(query2);
            statement.executeBatch();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Bij after");
        }

    }

}
