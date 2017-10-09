/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import DatabaseConnector.DomXML;
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

        CheeseDAOInterface DAO = DaoFactory.createCheeseDao(data.getDatabaseType());

        List<CheesePOJO> list = DAO.getAllCheese();
        for (CheesePOJO idsearch : list) {
            int returnedid = idsearch.getCheeseID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }

    public boolean checkClientID(int sendID) {

        ClientDAOInterface DAO = DaoFactory.createClientDao(data.getDatabaseType());

        List<ClientPOJO> list = DAO.getAllClient();
        for (ClientPOJO idsearch : list) {
            int returnedid = idsearch.getClientID();

            if (sendID == returnedid) {
                return false;
            }
        }
        return true;
    }
}
