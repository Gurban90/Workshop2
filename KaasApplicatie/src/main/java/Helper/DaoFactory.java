/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Dao.*;
import Interface.*;
import MongoDao.*;

/**
 *
 * @author Gerben
 */
public class DaoFactory {

    public DaoFactory() {
    }

    public static AccountDAOInterface createAccountDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new AccountDAO();
            case "mongodb":
                return new AccountMongoDao();
            default:
                return new AccountDAO();
        }
    }

    public static AddressDAOInterface createAddressDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new AddressDAO();
            case "mongodb":
                return new AddressMongoDao();
            default:
                return new AddressDAO();
        }
    }

    public static CheeseDAOInterface createCheeseDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new CheeseDAO();
            case "mongodb":
                return new CheeseMongoDao();
            default:
                return new CheeseDAO();
        }
    }

    public static ClientDAOInterface createClientDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new ClientDAO();
            case "mongodb":
                return new ClientMongoDao();
            default:
                return new ClientDAO();
        }
    }

    public static OrderDAOInterface createOrderDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new OrderDAO();
            case "mongodb":
                return new OrderMongoDAO();
            default:
                return new OrderDAO();
        }
    }

    public static OrderDetailDAOInterface createOrderDetailDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new OrderDetailDAO();
            case "mongodb":
                return new OrderDetailMongoDao();
            default:
                return new OrderDetailDAO();
        }
    }

}
