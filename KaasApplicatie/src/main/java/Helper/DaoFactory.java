/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Order.OrderDetailMongoDao;
import Order.OrderMongoDAO;
import Client.ClientMongoDao;
import Cheese.CheeseMongoDao;
import Address.AddressMongoDao;
import Account.AccountMongoDao;
import Order.OrderDetailDAOInterface;
import Order.OrderDAOInterface;
import Client.ClientDAOInterface;
import Cheese.CheeseDAOInterface;
import Address.AddressDAOInterface;
import Account.AccountDAOInterface;
import Order.OrderDetailDAO;
import Order.OrderDAO;
import Client.ClientDAO;
import Cheese.CheeseDAO;
import Address.AddressDAO;
import Account.AccountDAO;
import Account.HibernateAccountDAO;
import Address.HibernateAddressDAO;
import Cheese.HibernateCheeseDAO;
import Client.HibernateClientDAO;
import DatabaseConnector.DomXML;
import Order.HibernateOrderDAO;
import Order.HibernateOrderDetailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
@ComponentScan("DatabaseConnector")
public class DaoFactory {
 
    public DaoFactory() {
    }

   
    public static AccountDAOInterface createAccountDao(String database) {
        switch (database.toLowerCase()) {
            case "mysql":
                return new AccountDAO();
            case "mongodb":
                return new AccountMongoDao();
            case "hibernate":
                return new HibernateAccountDAO();
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
            case "hibernate":
                return new HibernateAddressDAO();
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
            case "hibernate":
                return new HibernateCheeseDAO();
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
            case "hibernate":
                return new HibernateClientDAO();
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
            case "hibernate":
                return new HibernateOrderDAO();
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
            case "hibernate":
                return new HibernateOrderDetailDAO();
            default:
                return new OrderDetailDAO();
        }
    }

}
