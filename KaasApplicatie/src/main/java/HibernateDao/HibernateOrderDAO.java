/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import Interface.OrderDAOInterface;
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
public class HibernateOrderDAO extends GenericDAO implements OrderDAOInterface {

     private Logger LOGGER = Logger.getLogger(HibernateOrderDAO.class.getName());
    
    
    public HibernateOrderDAO(EntityManager em) {
        super(em);
    }
  
    public List<OrderPOJO> getAllOrder() {
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

    @Override
    public Integer addOrder(OrderPOJO order) {
        OrderPOJO order2 = super.create(order);
        return order2.getOrderID();
    }

    @Override
    public OrderPOJO getOrder(OrderPOJO order) {
        return super.findById(OrderPOJO.class, order.getOrderID());
    }

    @Override
    public List<OrderPOJO> getOrderWithClient(ClientPOJO client) {
        return getOrderWithClient(client.getClientID());
    }

    @Override
    public void updateOrder(OrderPOJO order) {
        super.update(order);
    }

    @Override
    public void deleteOrder(OrderPOJO order) {
        super.delete(OrderPOJO.class, order.getOrderID());
    }
     
     
     
}
