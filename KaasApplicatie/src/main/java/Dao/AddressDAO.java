/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Helper.ConnectionFactory;
import Interface.AddressDAOInterface;
import POJO.AddressPOJO;
import POJO.AddressTypePOJO;
import POJO.ClientPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author Jasper Thielen
 */
public class AddressDAO implements AddressDAOInterface {

    private Logger LOGGER = Logger.getLogger(AddressDAOInterface.class.getName());
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String query;
     private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addAddress(AddressPOJO address) {
        LOGGER.info("addAddress Start");
        Integer newID = 0;
        this.query = "select * from Client where ClientID = ?";
        try {
             connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setInt(1, address.getClientID());
            this.resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.query = "select * from AddressType where AddressTypeID = ?";
                statement = connect.prepareStatement(query);
                statement.setInt(1, address.getAddressTypeID());
                this.resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    this.query = "INSERT INTO Address (StreetName, HouseNumber, HouseNumberAddition, PostalCode, City, Client_ClientID, AddressType_AddressTypeID ) VALUES (?,?,?,?,?,?,?);";
                    connect = connectionfactory.getConnection();
                    statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, address.getStreetName());
                    statement.setInt(2, address.getHouseNumber());
                    statement.setString(3, address.getHouseNumberAddition());
                    statement.setString(4, address.getPostalCode());
                    statement.setString(5, address.getCity());
                    statement.setInt(6, address.getClientID());
                    statement.setInt(7, address.getAddressTypeID());
                    statement.executeUpdate();

                    try (ResultSet resultSet3 = statement.getGeneratedKeys()) {
                        if (resultSet3.next()) { //skips to next row
                            newID = resultSet3.getInt(1); //selects number from first column of database in row
                            address.setAddressID(newID); // sets Id
                        } else {
                            throw new SQLException("Creating Address failed, no ID obtained. Client and AddressType must exist.");
                        }
                    }
                } else {
                    System.out.println("Check AddressType , has to exist in database");
                }
            } else {
                System.out.println("Check Client, has to exist in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("addAddress end");
        return newID;
    }

    @Override
    public AddressPOJO getAddress(AddressPOJO address) {
        LOGGER.info("getAddress Start");
        query = "SELECT * FROM Address WHERE AddressID=?";

        AddressPOJO foundAddress = new AddressPOJO();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setObject(1, address.getAddressID());
            resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundAddress.setAddressID(resultSet.getInt(1));
                foundAddress.setStreetName(resultSet.getString(2));
                foundAddress.setHouseNumber(resultSet.getInt(3));
                foundAddress.setHouseNumberAddition(resultSet.getString(4));
                foundAddress.setPostalCode(resultSet.getString(5));
                foundAddress.setCity(resultSet.getString(6));
                foundAddress.setClientID(resultSet.getInt(7));
                foundAddress.setAddressTypeID(resultSet.getInt(8));

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
        LOGGER.info("getAddress end");
        return foundAddress;

    }

    @Override
    public List<AddressPOJO> getAddressWithClient(ClientPOJO client) {
        LOGGER.info("getAllAddress Start");
        query = "SELECT * FROM Address WHERE Client_ClientID=?";

        List<AddressPOJO> returnedAddress = new ArrayList<>();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setObject(1, client.getClientID());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                AddressPOJO foundAddress = new AddressPOJO();

                foundAddress.setAddressID(resultSet.getInt(1));
                foundAddress.setStreetName(resultSet.getString(2));
                foundAddress.setHouseNumber(resultSet.getInt(3));
                foundAddress.setHouseNumberAddition(resultSet.getString(4));
                foundAddress.setPostalCode(resultSet.getString(5));
                foundAddress.setCity(resultSet.getString(6));
                foundAddress.setClientID(resultSet.getInt(7));
                foundAddress.setAddressTypeID(resultSet.getInt(8));
                returnedAddress.add(foundAddress);
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
    public List<AddressPOJO> getAllAddress() {
        LOGGER.info("getAllAddress Start");
        query = "SELECT * FROM Address;";

        List<AddressPOJO> returnedAddress = new ArrayList<>();

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                AddressPOJO foundAddress = new AddressPOJO();

                foundAddress.setAddressID(resultSet.getInt(1));
                foundAddress.setStreetName(resultSet.getString(2));
                foundAddress.setHouseNumber(resultSet.getInt(3));
                foundAddress.setHouseNumberAddition(resultSet.getString(4));
                foundAddress.setPostalCode(resultSet.getString(5));
                foundAddress.setCity(resultSet.getString(6));
                foundAddress.setClientID(resultSet.getInt(7));
                foundAddress.setAddressTypeID(resultSet.getInt(8));
                returnedAddress.add(foundAddress);
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
    public void updateAddress(AddressPOJO address) {
        LOGGER.info("updateAddress Start");
        query = "UPDATE Address SET StreetName =? , HouseNumber = ?, HouseNumberAddition =?, PostalCode =?, City =?, Client_ClientID =?, AddressType_AddressTypeID =?  WHERE AddressID=?";
        try {
           connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setString(1, address.getStreetName());
            statement.setInt(2, address.getHouseNumber());
            statement.setString(3, address.getHouseNumberAddition());
            statement.setString(4, address.getPostalCode());
            statement.setString(5, address.getCity());
            statement.setInt(6, address.getClientID());
            statement.setInt(7, address.getAddressTypeID());
            statement.setInt(8, address.getAddressID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("updateAccount end");
    }

    @Override
    public void deleteAddress(AddressPOJO address) {
        LOGGER.info("deleteAddress Start");
        query = "DELETE FROM Address where AddressID = ?";

        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setInt(1, address.getAddressID());
            statement.executeUpdate();

            connect.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("deleteAdress stop");

    }

    @Override
    public Integer addAddressType(AddressTypePOJO address) {
        Integer newID = 0;

        LOGGER.info("addaddresstype Start");
        query = "INSERT INTO addressType"
                + "(type) VALUES "
                + "(?);";
        try {
           connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getAddressType());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newID = resultSet.getInt(1);
                    address.setAddressTypeID(newID);
                } else {
                    throw new SQLException("Inserting order failed, no ID retrieved.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("addaddresstype end");
        return newID;
    }

    @Override
    public List<AddressTypePOJO> getAllAddressType() {
        LOGGER.info("getAllAddressType Start");
        query = "SELECT * FROM AddressType;";
        List<AddressTypePOJO> GetAllAddressType = new ArrayList<>();
        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AddressTypePOJO AddressType = new AddressTypePOJO();
                AddressType.setAddressTypeID(resultSet.getInt(1));
                AddressType.setAddressType(resultSet.getString(2));
                GetAllAddressType.add(AddressType);
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
        LOGGER.info("getAllAdressType end");
        return GetAllAddressType;
    }

    @Override
    public AddressTypePOJO getAddressType(AddressTypePOJO address) {
        LOGGER.info("getAddressType Start");
        query = "SELECT * FROM AddressType WHERE AddressTypeID=?";
        AddressTypePOJO foundAddressType = new AddressTypePOJO();
        try {
            connect = connectionfactory.getConnection();
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setObject(1, address.getAddressTypeID());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundAddressType.setAddressTypeID(resultSet.getInt(1));
                foundAddressType.setAddressType(resultSet.getString(2));
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
        LOGGER.info("getaddressType end");
        return foundAddressType;
    }

    @Override
    public void updateAddressType(AddressTypePOJO address) {
        LOGGER.info("updateAddressType Start");

        query = "UPDATE AddressType SET Type = ?, WHERE AddressTypeID=?";
        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setString(1, address.getAddressType());
            statement.setInt(2, address.getAddressTypeID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("updateAddressType end");
    }

    @Override
    public void deleteAddressType(AddressTypePOJO address) {
        LOGGER.info("deleteaddresstype Start");
        this.query = "select * from Address where AddressType_AddressTypeID = ?";
        try {
            connect = connectionfactory.getConnection();
            statement = connect.prepareStatement(query);
            statement.setInt(1, address.getAddressTypeID());
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                this.query = "DELETE from AddressType WHERE AddressTypeID = ?";
                statement = connect.prepareStatement(query);
                statement.setInt(1, address.getAddressTypeID());
                statement.executeUpdate();
                System.out.println("AddressType has been deleted");
            } else {
                System.out.println("AddressType is currently in use, delete not possible.");
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
        LOGGER.info("deleteaddresstype end");
    }

}
