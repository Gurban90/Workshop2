/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.OrderDetailPOJO;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Gerben
 */
public class HibernateOrderDetailDAO extends GenericDAO {

    private Logger LOGGER = Logger.getLogger(HibernateOrderDetailDAO.class.getName());
   
    
    public HibernateOrderDetailDAO(EntityManager em) {
        super(em);
    }

    @Override
    public List<OrderDetailPOJO> getAll() {
       LOGGER.info("getAllOrderDetail Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM OrderDetailPOJO");
        List<OrderDetailPOJO> ordersDetails = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllOrderDetail End");
        return ordersDetails;
    }
    
    public List<OrderDetailPOJO> getWithOrder(int OrderID) {
       LOGGER.info("getWithOrder Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM OrderDetailPOJO WHERE OrderID = :id");
        query.setParameter("id", OrderID);
        List<OrderDetailPOJO> ordersDetails = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getWithOrder End");
        return ordersDetails;
    }
    
     public List<OrderDetailPOJO> getWithCheese(int CheeseID) {
       LOGGER.info("getWithOrder Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM OrderDetailPOJO WHERE CheeseID = :id");
        query.setParameter("id", CheeseID);
        List<OrderDetailPOJO> ordersDetails = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getWithOrder End");
        return ordersDetails;
    }
     
     
    
    
}
