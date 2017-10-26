/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import Client.ClientPOJO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gerben
 */
public class HibernateOrderDAOTest {
    
    public HibernateOrderDAOTest() {
    }

    @Test
    public void addOrder() {
        OrderPOJO order = new OrderPOJO();
        ClientPOJO client = new ClientPOJO();
        HibernateOrderDAO dao = new HibernateOrderDAO();
        order.setOrderDate(LocalDateTime.now());
        order.setProcessedDate(LocalDateTime.now());
        order.setTotalPrice(new BigDecimal(0));
        client.setClientID(1);
        order.setClient(client);
        int id = dao.addOrder(order);
        assertEquals(12, id);
    }
    
}
