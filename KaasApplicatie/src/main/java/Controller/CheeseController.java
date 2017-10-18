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
    private CheesePOJO cheesepojo;

    public CheeseController() {
        cheesedao = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");
        cheesepojo = new CheesePOJO();
    }

    //Voor test
    public CheeseController(CheeseDAOInterface cheesedao) {
        this.cheesedao = cheesedao;
        this.cheesepojo = new CheesePOJO();
    }

    public List<CheesePOJO> findAllCheese() {
        LOGGER.info("FindallCheese start");
        List<CheesePOJO> cheeses = cheesedao.getAllCheese();
        cheesedao.finalize();
        LOGGER.info("FindallCheese end");
        return cheeses;
    }

    public CheesePOJO findCheese(int ID) {
        LOGGER.info("findCheese start");
        cheesepojo.setCheeseID(ID);
        CheesePOJO cheese = cheesedao.getCheese(cheesepojo);
        cheesedao.finalize();
        LOGGER.info("findCheese end");
        return cheese;
    }

    public List<CheesePOJO> findCheeseWithName(String name) {
        LOGGER.info("findCheeseWithName start");
        cheesepojo.setCheeseName(name);
        List<CheesePOJO> cheeses = cheesedao.getCheeseWithName(cheesepojo);
        cheesedao.finalize();
        LOGGER.info("findCheeseWithName end");
        return cheeses;
    }

    public int newCheese(String name, BigDecimal price, int stock) {
        LOGGER.info("newCheese start");
        cheesepojo.setCheeseName(name);
        cheesepojo.setPrice(price);
        cheesepojo.setStock(stock);
        cheesedao.addCheese(cheesepojo);
        cheesedao.finalize();
        LOGGER.info("newCheese end");
        return cheesepojo.getCheeseID();
    }

    public void removeCheese(int ID) {
        LOGGER.info("removeCheese start");
        IDCheck check = new IDCheck();
        if (check.checkCheeseOrder(ID)) {
            System.out.println("Cheese is being ordered.");
            return;
        }
        try {
            cheesepojo.setCheeseID(ID);
            cheesedao.deleteCheese(cheesepojo);
            cheesedao.finalize();
            System.out.println("Cheese with ID " + ID + " is removed.");
        } catch (Exception E) {
            System.out.println("Has to be an existing Cheese.");
        }
        LOGGER.info("removeCheese end");
    }

    public String editCheese(int id, String name, BigDecimal price, int stock) {
        LOGGER.info("editCheese start");
        try {
            cheesepojo.setCheeseID(id);
            cheesepojo = cheesedao.getCheese(cheesepojo);
            cheesepojo.setCheeseName(name);
            cheesepojo.setPrice(price);
            cheesepojo.setStock(stock);
            cheesedao.updateCheese(cheesepojo);
            cheesedao.finalize();
            LOGGER.info("editCheese end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheeseName(int id, String name) {
        LOGGER.info("editCheeseName start");
        try {
            cheesepojo.setCheeseID(id);
            cheesepojo = cheesedao.getCheese(cheesepojo);
            cheesepojo.setCheeseName(name);
            cheesedao.updateCheese(cheesepojo);
            cheesedao.finalize();
            LOGGER.info("editCheeseName end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheesePrice(int id, BigDecimal price) {
        LOGGER.info("editCheesePrice start");
        try {
            cheesepojo.setCheeseID(id);
            cheesepojo = cheesedao.getCheese(cheesepojo);
            cheesepojo.setPrice(price);
            cheesedao.updateCheese(cheesepojo);
            cheesedao.finalize();
            LOGGER.info("editCheesePrice end");
            return "Cheese has been edited.";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

    public String editCheeseStock(int id, int stock) {
        LOGGER.info("editCheeseStock start");
        try {
            cheesepojo.setCheeseID(id);
            cheesepojo = cheesedao.getCheese(cheesepojo);
            cheesepojo.setStock(stock);
            cheesedao.updateCheese(cheesepojo);
            cheesedao.finalize();
            LOGGER.info("editCheeseStock end");
            return "Cheese has been edited: ";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

}
