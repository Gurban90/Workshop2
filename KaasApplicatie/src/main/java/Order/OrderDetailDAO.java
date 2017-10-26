/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import Helper.ConnectionFactory;
import Cheese.CheeseDAOInterface;
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
public class OrderDetailDAO implements OrderDetailDAOInterface {

    private Logger LOGGER = Logger.getLogger(CheeseDAOInterface.class.getName());
    private Connection connection;
    private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addOrderDetail(OrderDetailPOJO orderDetail) {

        LOGGER.info("addOrderDetail Start");
        Integer newID = 0;
        String query = "select * from Cheese where CheeseID = ?";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderDetail.getCheeseID());
            ResultSet resultSet = statement.executeQuery();//Check of Cheese wel bestaat.
            if (resultSet.next()) {
                query = "select * from `order` where OrderID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, orderDetail.getOrderID());
                ResultSet resultSet2 = statement.executeQuery();//Check of Order wel bestaat.
                if (resultSet2.next()) {
                    query = "INSERT INTO OrderDetail (Quantity, Cheese_CheeseID, Order_OrderID) VALUES (?,?,?);";
                    statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, orderDetail.getQuantity());
                    statement.setInt(2, orderDetail.getCheeseID());
                    statement.setInt(3, orderDetail.getOrderID());
                    statement.executeUpdate();
                    try (ResultSet resultSet3 = statement.getGeneratedKeys()) {
                        if (resultSet3.next()) {
                            newID = resultSet3.getInt(1);
                            orderDetail.setOrderDetailID(newID);
                        } else {
                            throw new SQLException("Creating OrderDetail failed, no ID obtained.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Check order , has to exist in database");
                }
            } else {
                System.out.println("Check cheese, has to exist in database");
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

        LOGGER.info("addOrderDetail end");
        return newID;

    }

    @Override
    public List<OrderDetailPOJO> getAllOrderDetail() {
        LOGGER.info("getAllOrderDetail Start");
        String query = "SELECT * FROM OrderDetail;";
        List<OrderDetailPOJO> returnedOrderDetail = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetailPOJO orderDetail = new OrderDetailPOJO();
                orderDetail.setOrderDetailID(resultSet.getInt(1));
                orderDetail.setQuantity(resultSet.getInt(2));
                orderDetail.setCheeseID(resultSet.getInt(3));
                orderDetail.setOrderID(resultSet.getInt(4));
                returnedOrderDetail.add(orderDetail);
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

        LOGGER.info("getAllOrderDetail end");
        return returnedOrderDetail;
    }

    @Override
    public List<OrderDetailPOJO> getOrderDetail(OrderDetailPOJO orderdetail) {
        LOGGER.info("getOrderDetail Start");
        String query = "SELECT * FROM OrderDetail WHERE Order_OrderID =?";
        List<OrderDetailPOJO> returnedOrderDetail = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, orderdetail.getOrderID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetailPOJO orderDetail = new OrderDetailPOJO();
                orderDetail.setOrderDetailID(resultSet.getInt(1));
                orderDetail.setQuantity(resultSet.getInt(2));
                orderDetail.setCheeseID(resultSet.getInt(3));
                orderDetail.setOrderID(resultSet.getInt(4));
                returnedOrderDetail.add(orderDetail);
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

        LOGGER.info("getOrderDetail end");
        return returnedOrderDetail;
    }

    @Override
    public OrderDetailPOJO getOrderDetailWithID(OrderDetailPOJO orderdetail) {
        LOGGER.info("getOrderDetail Start");
        String query = "SELECT * FROM OrderDetail WHERE OrderDetailID =?";
        OrderDetailPOJO foundOrderDetail = new OrderDetailPOJO();
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, orderdetail.getOrderDetailID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundOrderDetail.setOrderDetailID(resultSet.getInt(1));
                foundOrderDetail.setQuantity(resultSet.getInt(2));
                foundOrderDetail.setCheeseID(resultSet.getInt(3));
                foundOrderDetail.setOrderID(resultSet.getInt(4));
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
        LOGGER.info("getOrderDetail end");
        return foundOrderDetail;
    }

    public void updateOrderDetail(OrderDetailPOJO orderDetail) {
        LOGGER.info("updateOrderDetail Start");
        String query = "select * from Cheese where CheeseID = ?";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderDetail.getCheeseID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                query = "SELECT * from `order` WHERE OrderID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, orderDetail.getOrderID());
                ResultSet resultSet2 = statement.executeQuery();
                if (resultSet2.next()) {
                    query = "UPDATE orderdetail SET Quantity = ?, Cheese_CheeseID = ?, Order_OrderID = ? WHERE OrderDetailID = ?;";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, orderDetail.getQuantity());
                    statement.setInt(2, orderDetail.getCheeseID());
                    statement.setInt(3, orderDetail.getOrderID());
                    statement.setInt(4, orderDetail.getOrderDetailID());
                    statement.executeUpdate();
                } else {
                    System.out.println("Check order , has to exist in database");
                }
            } else {
                System.out.println("Check cheese, has to exist in database");
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

        LOGGER.info("updateOrderDetail end");

    }

    @Override
    public void deleteOrderDetail(OrderDetailPOJO orderdetail
    ) {
        LOGGER.info("deleteOrderDetail Start");
        String query = "DELETE FROM OrderDetail WHERE OrderDetailID = ?";
        try {
            connection = connectionfactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderdetail.getOrderDetailID());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
        LOGGER.info("deleteOrderDetail end");
    }
    
     @Override
    public void finalize(){};

    @Override
    public List<OrderDetailPOJO> getWithCheese(int CheeseID) {
        throw new UnsupportedOperationException("Not needed in this DAO."); 
    }

}
