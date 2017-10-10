/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Gerben
 */
public class HibernateDaoFactory {

    private static HibernateDaoFactory instance;
    private static EntityManagerFactory entityManagerFactory;

    private HibernateDaoFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Hibernate");
    }
    
    public static HibernateDaoFactory getInstance(){
        if(instance == null){
            instance = new HibernateDaoFactory();
        }
        return instance;
    }


    public GenericDAO getDao(String classname) {
        classname = classname.toLowerCase();
        switch (classname) {
            case "account":
                return new HibernateAccountDAO(entityManagerFactory.createEntityManager());
            case "address":
                return new HibernateAddressDAO(entityManagerFactory.createEntityManager());
            case "cheese":
                return new HibernateCheeseDAO(entityManagerFactory.createEntityManager());
            case "client":
                return new HibernateClientDAO(entityManagerFactory.createEntityManager());
            case "order":
                return new HibernateOrderDAO(entityManagerFactory.createEntityManager());
            case "orderdetail":
                return new HibernateOrderDetailDAO(entityManagerFactory.createEntityManager());
            default:
                throw new IllegalArgumentException("No DAO with that name.");
        }
    }

    public static void close() {
        entityManagerFactory.close();
        instance=null;
    }
}
