/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cheese;

import Helper.ConnectionFactory;
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
public class CheeseDAO implements CheeseDAOInterface {

    Logger logger = Logger.getLogger(CheeseDAOInterface.class.getName());
    private Connection connection;
    private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addCheese(CheesePOJO cheese) {
        logger.info("addCheese Start");
        Integer newID = 0;
        String query = "INSERT INTO Cheese (Name, Price, Stock) VALUES (?,?,?);";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, cheese.getCheeseName());
            statement.setBigDecimal(2, cheese.getPrice());
            statement.setInt(3, cheese.getStock());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newID = resultSet.getInt(1);
                    cheese.setCheeseID(newID);
                } else {
                    throw new SQLException("Creating Cheese failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.info("addCheese end");
        return newID;

    }

    @Override
    public List<CheesePOJO> getAllCheese() {
        logger.info("getAllCheese Start");
        String query = "SELECT * FROM Cheese;";
        List<CheesePOJO> returnedCheeses = new ArrayList<>();

        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CheesePOJO cheese = new CheesePOJO();
                cheese.setCheeseID(resultSet.getInt(1));
                cheese.setCheeseName(resultSet.getString(2));
                cheese.setPrice(resultSet.getBigDecimal(3));
                cheese.setStock(resultSet.getInt(4));
                returnedCheeses.add(cheese);
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
        logger.info("getAllCheese end");
        return returnedCheeses;
    }

    @Override
    public CheesePOJO getCheese(CheesePOJO cheese) {
        logger.info("getCheese Start");
        String query = "SELECT * FROM Cheese WHERE CheeseID=?";
        CheesePOJO foundCheese = new CheesePOJO();
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, cheese.getCheeseID());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundCheese.setCheeseID(resultSet.getInt(1));
                foundCheese.setCheeseName(resultSet.getString(2));
                foundCheese.setPrice(resultSet.getBigDecimal(3));
                foundCheese.setStock(resultSet.getInt(4));
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
        logger.info("getCheese end");
        return foundCheese;
    }

    @Override
    public List<CheesePOJO> getCheeseWithName(CheesePOJO cheese) {
        logger.info("getCheeseWithName Start");
        String query = "SELECT * FROM Cheese WHERE Name=?";
        List<CheesePOJO> returnedCheeses = new ArrayList<>();
       
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, cheese.getCheeseName());
            ResultSet resultSet = statement.executeQuery();

             while (resultSet.next()) {
                CheesePOJO foundcheese = new CheesePOJO();
                foundcheese.setCheeseID(resultSet.getInt(1));
                foundcheese.setCheeseName(resultSet.getString(2));
                foundcheese.setPrice(resultSet.getBigDecimal(3));
                foundcheese.setStock(resultSet.getInt(4));
                returnedCheeses.add(foundcheese);
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
        logger.info("getCheeseWithName end");
        return returnedCheeses;

    }

    @Override
    public void updateCheese(CheesePOJO cheese) {
        logger.info("updateCheese Start");
        String query = "UPDATE Cheese SET Name = ?, Price = ?, Stock = ? WHERE CheeseID=?";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cheese.getCheeseName());
            statement.setBigDecimal(2, cheese.getPrice());
            statement.setInt(3, cheese.getStock());
            statement.setInt(4, cheese.getCheeseID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.info("updateCheese end");
    }

    @Override
    public void deleteCheese(CheesePOJO cheese) {
        logger.info("deleteCheese Start");
        String query = "select * from OrderDetail where Cheese_CheeseID = ?";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cheese.getCheeseID());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                query = "DELETE FROM Cheese WHERE CheeseID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, cheese.getCheeseID());
                statement.executeUpdate();
                System.out.println("Cheese is deleted");
            } else {
                System.out.println("Cheese is currently being ordered, delete not possible.");
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
        logger.info("deleteCheese end");
    }

     @Override
    public void finalize(){};
}
