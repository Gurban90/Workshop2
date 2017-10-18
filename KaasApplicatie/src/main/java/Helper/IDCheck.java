/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import DatabaseConnector.DomXML;
import HibernateDao.HibernateCheeseDAO;
import HibernateDao.HibernateClientDAO;
import HibernateDao.HibernateOrderDAO;
import HibernateDao.HibernateOrderDetailDAO;
import Interface.CheeseDAOInterface;
import Interface.ClientDAOInterface;
import Interface.OrderDAOInterface;
import Interface.OrderDetailDAOInterface;
import POJO.CheesePOJO;
import POJO.ClientPOJO;
import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.util.List;

public class IDCheck {

    private DomXML data;

    public IDCheck() {
        data = new DomXML();
    }

    public boolean checkCheeseID(int sendID) {

        CheeseDAOInterface Dao = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");

        List<CheesePOJO> list = Dao.getAllCheese();
        for (CheesePOJO idsearch : list) {
            int returnedid = idsearch.getCheeseID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }

    public boolean checkClientID(int sendID) {

        ClientDAOInterface Dao = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

        List<ClientPOJO> list = Dao.getAllClient();
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
        OrderDetailDAOInterface Dao = (HibernateOrderDetailDAO) HibernateDaoFactory.getInstance().getDao("orderdetail");

        List<OrderDetailPOJO> list = Dao.getWithCheese(cheeseID);
        if (list.isEmpty()) {
            isOrdered = false;
        }
        return isOrdered;
    }

    public boolean checkClientOrder(int clientID) {
        boolean isOrdered = true;
        OrderDAOInterface Dao = (HibernateOrderDAO) HibernateDaoFactory.getInstance().getDao("order");
        ClientPOJO client = new ClientPOJO();
        client.setClientID(clientID);

        List<OrderPOJO> list = Dao.getOrderWithClient(client);
        if (list.isEmpty()) {
            isOrdered = false;
        }
        return isOrdered;
    }

}
