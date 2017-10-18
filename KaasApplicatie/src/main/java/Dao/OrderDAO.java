/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Helper.Converter;

import Helper.ConnectionFactory;
import Interface.ClientDAOInterface;
import Interface.OrderDAOInterface;
import POJO.ClientPOJO;
import POJO.OrderPOJO;
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
 * @author Jasper Thielen
 */
public class OrderDAO implements OrderDAOInterface {

    private Logger LOGGER = Logger.getLogger(ClientDAOInterface.class.getName());
    private Connection connect;
    private Converter convert;
    private PreparedStatement statement;
    private String query;
    private ResultSet resultSet;
    private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addOrder(OrderPOJO order) {
        Integer newID = 0;
        convert = new Converter();
        LOGGER.info("addorder Start");
        this.query = "select * from Client where ClientID = ?";
        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setInt(1, order.getClientID());
            this.resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.query = "INSERT INTO `order` (`OrderDate`, `TotalPrice`, `ProcessedDate`, `Client_ClientID`) VALUES (?,?,?,?);";
                try {
                    connect = connectionfactory.getConnection();
                    statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, convert.convertLocalDateTime(order.getOrderDate()));
                    statement.setBigDecimal(2, order.getTotalPrice());
                    statement.setString(3, convert.convertLocalDateTime(order.getProcessedDate()));
                    statement.setInt(4, order.getClientID());
                    statement.executeUpdate();

                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            newID = resultSet.getInt(1);
                            order.setOrderID(newID);
                        } else {
                            throw new SQLException("Inserting order failed, no ID retrieved.");
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Check Client , has to exist in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("addorder end");
        return newID;
    }

    @Override
    public List<OrderPOJO> getAllOrder() {
        LOGGER.info("getAllOrder Start");
        query = "SELECT * FROM `order`;";

        List<OrderPOJO> returnedOrder = new ArrayList<>();
        convert = new Converter();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                OrderPOJO foundOrder = new OrderPOJO();

                foundOrder.setOrderID(resultSet.getInt(1));
                foundOrder.setOrderDate(convert.convertDate(resultSet.getString(2)));
                foundOrder.setTotalPrice(resultSet.getBigDecimal(3));
                foundOrder.setProcessedDate(convert.convertDate(resultSet.getString(4)));
                foundOrder.setClientID(resultSet.getInt(5));
                returnedOrder.add(foundOrder);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getAllOrder end");
        return returnedOrder;
    }

    @Override
    public OrderPOJO getOrder(OrderPOJO order) {
        LOGGER.info("getOrder Start");
        String query = "SELECT * FROM `order` WHERE OrderID=?";
        OrderPOJO foundOrder = new OrderPOJO();
        convert = new Converter();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setObject(1, order.getOrderID());
            resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundOrder.setOrderID(resultSet.getInt(1));
                foundOrder.setOrderDate(convert.convertDate(resultSet.getString(2)));
                foundOrder.setTotalPrice(resultSet.getBigDecimal(3));
                foundOrder.setProcessedDate(convert.convertDate(resultSet.getString(4)));
                foundOrder.setClientID(resultSet.getInt(5));

            }
            connect.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("getorder end");
        return foundOrder;
    }

    @Override
    public List<OrderPOJO> getOrderWithClient(ClientPOJO client) {
        LOGGER.info("getAllAddress Start");
        query = "SELECT * FROM `order` WHERE Client_ClientID=?";
        List<OrderPOJO> returnedAddress = new ArrayList<>();
        convert = new Converter();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setObject(1, client.getClientID());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderPOJO foundOrder = new OrderPOJO();

                foundOrder.setOrderID(resultSet.getInt(1));
                foundOrder.setOrderDate(convert.convertDate(resultSet.getString(2)));
                foundOrder.setTotalPrice(resultSet.getBigDecimal(3));
                foundOrder.setProcessedDate(convert.convertDate(resultSet.getString(4)));
                foundOrder.setClientID(resultSet.getInt(5));
                returnedAddress.add(foundOrder);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("getAllCheese end");
        return returnedAddress;
    }

    @Override
    public void updateOrder(OrderPOJO order) {
        LOGGER.info("updateOrder Start");
        String query = "UPDATE `order` SET OrderDate = ?, TotalPrice = ?, ProcessedDate = ?, Client_ClientID = ?  WHERE OrderID=?";
        convert = new Converter();

        try {
            connect = connectionfactory.getConnection();
            PreparedStatement updateOrder = connect.prepareStatement(query);
            updateOrder.setInt(5, order.getOrderID());
            updateOrder.setString(1, convert.convertLocalDateTime(order.getOrderDate()));
            updateOrder.setBigDecimal(2, order.getTotalPrice());
            updateOrder.setString(3, convert.convertLocalDateTime(order.getProcessedDate()));
            updateOrder.setInt(4, order.getClientID());
            updateOrder.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("updateOrder end");
    }

    @Override
    public void deleteOrder(OrderPOJO order) {
        LOGGER.info("deleteOrder Start");

        query = "DELETE FROM `order` where OrderID = ?";
        String query2 = "DELETE FROM `orderdetail` where Order_OrderID = ?";
        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setInt(1, order.getOrderID());
            statement.executeUpdate();
            PreparedStatement statement2 = connect.prepareStatement(query2);
            statement2.setInt(1, order.getOrderID());
            statement2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
            }
        }

        LOGGER.info("deleteOrder end");
    }
    
     @Override
    public void finalize(){};
}
