/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Helper.ConnectionFactory;
import Interface.ClientDAOInterface;
import POJO.ClientPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class ClientDAO implements ClientDAOInterface {

    private Logger LOGGER = Logger.getLogger(ClientDAOInterface.class.getName());

    private Connection connection;
    private PreparedStatement statement;
    private String query;
    private ResultSet resultSet;
    private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addClient(ClientPOJO client) {
        LOGGER.info("addClient Start");
        Integer newID = 0;

        query = "INSERT INTO Client (FirstName, LastName, Email) VALUES(?,?,?);";
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEMail());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newID = resultSet.getInt(1);
                    client.setClientID(newID);
                } else {
                    throw new SQLException("Inserting Client failed, no ID retrieved.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           try {
                connection.close();
           } catch (SQLException e) {
           }
        }

        LOGGER.info("addClient end");
        return newID;

    }

    @Override
    public List<ClientPOJO> getAllClient() {
        LOGGER.info("getAllClient Start");
        query = "SELECT * FROM Client;";
        List<ClientPOJO> returnedClients = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientPOJO client = new ClientPOJO();
                client.setClientID(resultSet.getInt(1));
                client.setFirstName(resultSet.getString(2));
                client.setLastName(resultSet.getString(3));
                client.setEMail(resultSet.getString(4));
                returnedClients.add(client);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getAllClient end");
        return returnedClients;
    }

    public ClientPOJO getClient(ClientPOJO client) {
        LOGGER.info("getClient Start");
        query = "SELECT * FROM Client WHERE ClientID=?";
        ClientPOJO foundClient = new ClientPOJO();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, client.getClientID());
            resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundClient.setClientID(resultSet.getInt(1));
                foundClient.setFirstName(resultSet.getString(2));
                foundClient.setLastName(resultSet.getString(3));
                foundClient.setEMail(resultSet.getString(4));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("getClient end");
        return foundClient;
    }

    @Override
    public List<ClientPOJO> getClientWithFirstName(String FirstName) {
        LOGGER.info("getClientWithName Start");
        query = "SELECT * FROM Client WHERE FirstName=?";
        List<ClientPOJO> returnedClients = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, FirstName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientPOJO client = new ClientPOJO();
                client.setClientID(resultSet.getInt(1));
                client.setFirstName(resultSet.getString(2));
                client.setLastName(resultSet.getString(3));
                client.setEMail(resultSet.getString(4));
                returnedClients.add(client);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getClientWithName end");
        return returnedClients;
    }

    @Override
    public List<ClientPOJO> getClientWithLastName(String LastName) {
        LOGGER.info("getClientWithLastName Start");
        query = "SELECT * FROM Client WHERE LastName=?";
        List<ClientPOJO> returnedClients = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, LastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientPOJO client = new ClientPOJO();
                client.setClientID(resultSet.getInt(1));
                client.setFirstName(resultSet.getString(2));
                client.setLastName(resultSet.getString(3));
                client.setEMail(resultSet.getString(4));
                returnedClients.add(client);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getClientWithLastName end");
        return returnedClients;
    }

    @Override
    public List<ClientPOJO> getClientWithEmail(String eMail) {
        LOGGER.info("getClientWithEmail Start");
        query = "SELECT * FROM Client WHERE Email=?";
        List<ClientPOJO> returnedClients = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, eMail);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientPOJO client = new ClientPOJO();
                client.setClientID(resultSet.getInt(1));
                client.setFirstName(resultSet.getString(2));
                client.setLastName(resultSet.getString(3));
                client.setEMail(resultSet.getString(4));
                returnedClients.add(client);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getClientWithEmail end");
        return returnedClients;
    }

    @Override
    public void updateClient(ClientPOJO client) {
        LOGGER.info("updateClient Start");
        query = "UPDATE Client SET FirstName = ?, LastName = ?, Email = ? WHERE ClientID=?";
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEMail());
            statement.setInt(4, client.getClientID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("updateClient end");
    }

    @Override
    public void deleteClient(ClientPOJO client) {
        LOGGER.info("deleteClient Start");
        query = "SELECT * FROM `Order` WHERE Client_ClientID=?";
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, client.getClientID());
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                query = "DELETE FROM Client WHERE ClientID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, client.getClientID());
                statement.executeUpdate();
            } else {
                System.out.println("Client is currently ordering, delete not possible.");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
        LOGGER.info("deleteClient end");
    }

}
