/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.CheeseDAO;
import Helper.HibernateDaoFactory;
import Helper.IDCheck;
import HibernateDao.HibernateCheeseDAO;
import Interface.CheeseDAOInterface;
import Menu.CheeseMenu;
import POJO.CheesePOJO;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class CheeseController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());
    private CheeseDAOInterface cheesedao;
    private CheesePOJO cheesePOJO;
    private CheesePOJO getCheese;
    private HibernateCheeseDAO hibCheeseDAO;

    public CheeseController() {
        hibCheeseDAO = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");
        cheesePOJO = new CheesePOJO();
    }

    //Voor test
    public CheeseController(CheeseDAOInterface cheesedao) {
        this.cheesedao = cheesedao;
        this.cheesePOJO = new CheesePOJO();
    }

    
    public List<CheesePOJO> findAllCheese() {
        LOGGER.info("FindallCheese start");
        List<CheesePOJO> cheeses = hibCheeseDAO.getAll();
        hibCheeseDAO.finalize();
        LOGGER.info("FindallCheese end");
        return cheeses;
    }

    public CheesePOJO findCheese(int ID) {
        LOGGER.info("findCheese start");
        CheesePOJO cheese = hibCheeseDAO.findById(CheesePOJO.class, ID);
        hibCheeseDAO.finalize();
        LOGGER.info("findCheese end");
        return cheese;
    }

    public List<CheesePOJO> findCheeseWithName(String name) {
        LOGGER.info("findCheeseWithName start");
        List<CheesePOJO> cheeses = hibCheeseDAO.getCheeseWithName(name);
        hibCheeseDAO.finalize();
        LOGGER.info("findCheeseWithName end");
        return cheeses;
    }

    public int newCheese(String name, BigDecimal price, int stock) {
        LOGGER.info("newCheese start");
        cheesePOJO.setCheeseName(name);
        cheesePOJO.setPrice(price);
        cheesePOJO.setStock(stock);
        hibCheeseDAO.create(cheesePOJO);
        hibCheeseDAO.finalize();
        LOGGER.info("newCheese end");
        return cheesePOJO.getCheeseID();
    }

    public void removeCheese(int ID) {
        LOGGER.info("removeCheese start");
        IDCheck check = new IDCheck();
        if (check.checkCheeseOrder(ID)) {
            System.out.println("Cheese is being ordered.");
            return;
        }
        try {
            hibCheeseDAO.delete(CheesePOJO.class, ID);
            hibCheeseDAO.finalize();
            System.out.println("Cheese with ID " + ID + " is removed.");
        } catch (Exception E) {
            System.out.println("Has to be an existing Cheese.");
        }
        LOGGER.info("removeCheese end");
    }

    public String editCheese(int id, String name, BigDecimal price, int stock) {
        LOGGER.info("editCheese start");
        try {
            cheesePOJO = hibCheeseDAO.findById(CheesePOJO.class, id);
            cheesePOJO.setCheeseName(name);
            cheesePOJO.setPrice(price);
            cheesePOJO.setStock(stock);
            hibCheeseDAO.update(cheesePOJO);
            hibCheeseDAO.finalize();
            LOGGER.info("editCheese end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheeseName(int id, String name) {
        LOGGER.info("editCheeseName start");
        try {
            cheesePOJO = hibCheeseDAO.findById(CheesePOJO.class, id);
            cheesePOJO.setCheeseName(name);
            hibCheeseDAO.update(cheesePOJO);
            hibCheeseDAO.finalize();
            LOGGER.info("editCheeseName end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheesePrice(int id, BigDecimal price) {
        LOGGER.info("editCheesePrice start");
        try {
            cheesePOJO = hibCheeseDAO.findById(CheesePOJO.class, id);
            cheesePOJO.setPrice(price);
            hibCheeseDAO.update(cheesePOJO);
            hibCheeseDAO.finalize();
            LOGGER.info("editCheesePrice end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheeseStock(int id, int stock) {
        LOGGER.info("editCheeseStock start");
        try {
            cheesePOJO = hibCheeseDAO.findById(CheesePOJO.class, id);
            cheesePOJO.setStock(stock);
            hibCheeseDAO.update(cheesePOJO);
            LOGGER.info("editCheeseStock end");
            return "Cheese has been edited: ";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

}
