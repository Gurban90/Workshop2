/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import HibernateDao.GenericDAO;
import Account.HibernateAccountDAO;
import Address.HibernateAddressDAO;
import Cheese.HibernateCheeseDAO;
import Client.HibernateClientDAO;
import Order.HibernateOrderDAO;
import Order.HibernateOrderDetailDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Gerben
 */
public class HibernateDaoFactory {

    private static HibernateDaoFactory instance;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager em;

    private HibernateDaoFactory() {
       
    }

    public static HibernateDaoFactory getInstance() {
        if (instance == null) {
            instance = new HibernateDaoFactory();
        }
        return instance;
    }

    public static EntityManager getEntityManger() {
        if (em == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("Hibernate");
            em = entityManagerFactory.createEntityManager();
        }
        return em;
    }

    public static void close() {
        entityManagerFactory.close();
        instance = null;
    }
}
