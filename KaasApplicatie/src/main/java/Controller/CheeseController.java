/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.CheeseDAO;
import Interface.CheeseDAOInterface;
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

    public CheeseController() {
        cheesedao = new CheeseDAO();
        cheesePOJO = new CheesePOJO();
    }

    //Voor test
    public CheeseController(CheeseDAOInterface cheesedao) {
        this.cheesedao = cheesedao;
        this.cheesePOJO = new CheesePOJO();
    }
 

    public List<CheesePOJO> findAllCheese() {
        LOGGER.info("FindallCheese start");
        LOGGER.info("FindallCheese end");
        return cheesedao.getAllCheese();
    }

    public CheesePOJO findCheese(int ID) {
        LOGGER.info("findCheese start");

        cheesePOJO.setCheeseID(ID);
        CheesePOJO returnedcheese = cheesedao.getCheese(cheesePOJO);
        LOGGER.info("findCheese end");
        return returnedcheese;
    }

    public CheesePOJO findCheeseWithName(String name) {
        LOGGER.info("findCheeseWithName start");

        cheesePOJO.setCheeseName(name);
        CheesePOJO returnedcheese = cheesedao.getCheeseWithName(cheesePOJO);
        LOGGER.info("findCheeseWithName end");
        return returnedcheese;
    }

    public int newCheese(String name, BigDecimal price, int stock) {
        LOGGER.info("newCheese start");

        cheesePOJO.setCheeseName(name);
        cheesePOJO.setPrice(price);
        cheesePOJO.setStock(stock);
        LOGGER.info("newCheese end");
        return cheesedao.addCheese(cheesePOJO);
    }

    public void removeCheese(int ID) {
        LOGGER.info("removeCheese start");

        cheesePOJO.setCheeseID(ID);
        cheesedao.deleteCheese(cheesePOJO);
        LOGGER.info("removeCheese end");
    }

    public String editCheese(int id, String name, BigDecimal price, int stock) {
        LOGGER.info("editCheese start");

        cheesePOJO.setCheeseID(id);
        cheesePOJO.setCheeseName(name);
        cheesePOJO.setPrice(price);
        cheesePOJO.setStock(stock);
        cheesedao.updateCheese(cheesePOJO);
        LOGGER.info("editCheese end");
        return "Cheese has been edited: ";
    }

    public String editCheeseName(int id, String name) {
        LOGGER.info("editCheeseName start");
        getCheese = new CheesePOJO();

        getCheese.setCheeseID(id);
        cheesePOJO = cheesedao.getCheese(getCheese);
        cheesePOJO.setCheeseName(name);
        cheesedao.updateCheese(cheesePOJO);
        LOGGER.info("editCheeseName end");
        return "Cheese has been edited. ";
    }

    public String editCheesePrice(int id, BigDecimal price) {
        LOGGER.info("editCheesePrice start");
        getCheese = new CheesePOJO();

        getCheese.setCheeseID(id);
        cheesePOJO = cheesedao.getCheese(getCheese);
        cheesePOJO.setPrice(price);
        cheesedao.updateCheese(cheesePOJO);
        LOGGER.info("editCheesePrice end");
        return "Cheese has been edited: ";
    }

    public String editCheeseStock(int id, int stock) {
        LOGGER.info("editCheeseStock start");
        getCheese = new CheesePOJO();

        getCheese.setCheeseID(id);
        cheesePOJO = cheesedao.getCheese(getCheese);
        cheesePOJO.setStock(stock);
        cheesedao.updateCheese(cheesePOJO);
        LOGGER.info("editCheeseStock end");
        return "Cheese has been edited: ";
    }

}
