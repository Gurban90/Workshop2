/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cheese;

import Helper.HibernateDaoFactory;
import Helper.IDCheck;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class CheeseController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());

    @Autowired
    private CheeseDAOInterface cheesedao;
    @Autowired
    private CheesePOJO cheesepojo;

    public CheeseController() {

    }

    //Voor test
    public CheeseController(CheeseDAOInterface cheesedao) {
        this.cheesedao = cheesedao;
           }

    public List<CheesePOJO> findAllCheese() {
        LOGGER.info("FindallCheese start");
        List<CheesePOJO> cheeses = cheesedao.getAllCheese();
        LOGGER.info("FindallCheese end");
        return cheeses;
    }

    public CheesePOJO findCheese(int ID) {
        LOGGER.info("findCheese start");
        cheesepojo.setCheeseID(ID);
        CheesePOJO cheese = cheesedao.getCheese(cheesepojo);
        LOGGER.info("findCheese end");
        return cheese;
    }

    public List<CheesePOJO> findCheeseWithName(String name) {
        LOGGER.info("findCheeseWithName start");
        cheesepojo.setCheeseName(name);
        List<CheesePOJO> cheeses = cheesedao.getCheeseWithName(cheesepojo);
        LOGGER.info("findCheeseWithName end");
        return cheeses;
    }

    public int newCheese(String name, BigDecimal price, int stock) {
        LOGGER.info("newCheese start");
        cheesepojo.setCheeseName(name);
        cheesepojo.setPrice(price);
        cheesepojo.setStock(stock);
        cheesedao.addCheese(cheesepojo);
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
            LOGGER.info("editCheeseStock end");
            return "Cheese has been edited: ";
        } catch (Exception E) {
            return "Cheese not found.";
        }
    }

}
