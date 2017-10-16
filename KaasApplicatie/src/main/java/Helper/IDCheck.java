/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import DatabaseConnector.DomXML;
import HibernateDao.HibernateCheeseDAO;
import HibernateDao.HibernateClientDAO;
import Interface.CheeseDAOInterface;
import Interface.ClientDAOInterface;
import POJO.CheesePOJO;
import POJO.ClientPOJO;
import java.util.List;

public class IDCheck {

    private DomXML data;

    public IDCheck() {
        data = new DomXML();
    }

    public boolean checkCheeseID(int sendID) {

        HibernateCheeseDAO Dao = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");
        

        List<CheesePOJO> list = Dao.getAll();
        for (CheesePOJO idsearch : list) {
            int returnedid = idsearch.getCheeseID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }

    public boolean checkClientID(int sendID) {

        HibernateClientDAO Dao = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

        List<ClientPOJO> list = Dao.getAll();
        for (ClientPOJO idsearch : list) {
            int returnedid = idsearch.getClientID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }
}
