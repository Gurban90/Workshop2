/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.ClientPOJO;
import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Gerben
 */
public class HibernateOrderDAOTest {

    public HibernateOrderDAOTest() {

    }

    @Test
    public void create() {
        HibernateOrderDAO dao1 = new HibernateOrderDAO(Persistence.createEntityManagerFactory("Hibernate").createEntityManager());
        HibernateClientDAO dao2 = new HibernateClientDAO(Persistence.createEntityManagerFactory("Hibernate").createEntityManager());

        
        OrderPOJO orderpojo = new OrderPOJO();
        ClientPOJO clientpojo = new ClientPOJO();
        clientpojo.setFirstName("fistnam");
        orderpojo.setClient(clientpojo);
        orderpojo.setOrderDate(LocalDateTime.now());
        orderpojo.setProcessedDate(LocalDateTime.now());
        orderpojo.setTotalPrice(BigDecimal.ONE);
        dao1.create(orderpojo);
        assertEquals(orderpojo.getOrderID(), 1);

    }

    /**
     * Test of getAll method, of class HibernateOrderDAO.
     */
    @Test
    public void testGetAll() {
    }

    /**
     * Test of getOrderWithClient method, of class HibernateOrderDAO.
     */
    @Test
    public void testGetOrderWithClient() {
    }

}
