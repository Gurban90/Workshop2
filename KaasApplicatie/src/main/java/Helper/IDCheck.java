/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import DatabaseConnector.DomXML;
import Cheese.HibernateCheeseDAO;
import Client.HibernateClientDAO;
import Order.HibernateOrderDAO;
import Order.HibernateOrderDetailDAO;
import Cheese.CheeseDAOInterface;
import Client.ClientDAOInterface;
import Order.OrderDAOInterface;
import Order.OrderDetailDAOInterface;
import Cheese.CheesePOJO;
import Client.ClientPOJO;
import Order.OrderDetailPOJO;
import Order.OrderPOJO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IDCheck {

   
    @Autowired
    private CheeseDAOInterface CheeseDao;
    @Autowired
    private ClientDAOInterface ClientDao;
    @Autowired
    private OrderDetailDAOInterface OrderDetailDao;
    @Autowired
    private OrderDAOInterface OrderDao;
    @Autowired
    ClientPOJO client;

  
    public boolean checkCheeseID(int sendID) {

        List<CheesePOJO> list = CheeseDao.getAllCheese();
        for (CheesePOJO idsearch : list) {
            int returnedid = idsearch.getCheeseID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }

    public boolean checkClientID(int sendID) {

        List<ClientPOJO> list = ClientDao.getAllClient();
        for (ClientPOJO idsearch : list) {
            int returnedid = idsearch.getClientID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCheeseOrder(int cheeseID) {
        boolean isOrdered = true;

        List<OrderDetailPOJO> list = OrderDetailDao.getWithCheese(cheeseID);
        if (list.isEmpty()) {
            isOrdered = false;
        }
        return isOrdered;
    }

    public boolean checkClientOrder(int clientID) {
        boolean isOrdered = true;

        client.setClientID(clientID);

        List<OrderPOJO> list = OrderDao.getOrderWithClient(client);
        if (list.isEmpty()) {
            isOrdered = false;
        }
        return isOrdered;
    }

}
