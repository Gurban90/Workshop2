/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import HibernateDao.GenericDAO;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class HibernateOrderDetailDAO extends GenericDAO implements OrderDetailDAOInterface {

    private Logger LOGGER = Logger.getLogger(HibernateOrderDetailDAO.class.getName());

    public HibernateOrderDetailDAO() {
    }

    @Override
    public List<OrderDetailPOJO> getAllOrderDetail() {
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

    @Override
    public Integer addOrderDetail(OrderDetailPOJO orderDetail) {
        OrderDetailPOJO orderdetail2 = super.create(orderDetail);
        return orderdetail2.getOrderDetailID();
    }

    @Override
    public List<OrderDetailPOJO> getOrderDetail(OrderDetailPOJO orderdetail) {
        return getWithOrder(orderdetail.getOrder().getOrderID());
    }

    @Override
    public OrderDetailPOJO getOrderDetailWithID(OrderDetailPOJO orderdetail) {
        return super.findById(OrderDetailPOJO.class, orderdetail.getOrderDetailID());
    }

    @Override
    public void deleteOrderDetail(OrderDetailPOJO orderDetail) {
        super.delete(OrderDetailPOJO.class, orderDetail.getOrderDetailID());
    }

    @Override
    public void updateOrderDetail(OrderDetailPOJO orderDetail) {
        super.update(orderDetail);
    }

}
