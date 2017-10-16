/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.ClientPOJO;
import POJO.OrderPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Gerben
 */
public class HibernateOrderDAO extends GenericDAO {

     private Logger LOGGER = Logger.getLogger(HibernateOrderDAO.class.getName());
    
    
    public HibernateOrderDAO(EntityManager em) {
        super(em);
    }


   @Override
    public List<OrderPOJO> getAll() {
       LOGGER.info("getAllOrder Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM OrderPOJO");
        List<OrderPOJO> orders = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllOrder End");
        return orders;
    }
    
     public List<OrderPOJO> getOrderWithClient(int clientID) {
        LOGGER.info("getOrderWithClient Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM OrderPOJO WHERE ClientID = :id");
        query.setParameter("id", clientID);
        List<OrderPOJO> orders = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getOrderWithClient end");
        return orders;
    } 
     
     
     
}
