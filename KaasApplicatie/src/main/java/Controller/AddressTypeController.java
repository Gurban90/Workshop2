/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AddressDAO;
import Helper.HibernateDaoFactory;
import HibernateDao.HibernateAddressDAO;
import Interface.AddressDAOInterface;
import POJO.AddressTypePOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.hibernate.JDBCException;

/**
 *
 * @author Gerben
 */
public class AddressTypeController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());
    private AddressDAOInterface addressdao;
    private AddressTypePOJO addresstypepojo;
    

    public AddressTypeController() {
        addressdao = (HibernateAddressDAO) HibernateDaoFactory.getInstance().getDao("address");
        addresstypepojo = new AddressTypePOJO();
    }

    public AddressTypeController(AddressDAOInterface addressdao) {
        this.addressdao = addressdao;
        addresstypepojo = new AddressTypePOJO();
    }

    public List<AddressTypePOJO> findAllAddressTypes() {
        LOGGER.info("findAllAddressTypes start");
        List<AddressTypePOJO> addressTypes = addressdao.getAllAddressType();
        addressdao.finalize();
        LOGGER.info("findAllAddressTypes end");
        return addressTypes;
    }

    public AddressTypePOJO findAddressType(int ID) {
        LOGGER.info("findAddressType start");
        addresstypepojo.setAddressTypeID(ID);
        AddressTypePOJO returnedAddressType = addressdao.getAddressType(addresstypepojo);
        addressdao.finalize();
        LOGGER.info("findAddressType end");
        return returnedAddressType;
    }

    public int newAddressType(String addressType) {
        LOGGER.info("newAddressType start");
        addresstypepojo.setAddressType(addressType);
        addressdao.addAddressType(addresstypepojo);
        addressdao.finalize();
        LOGGER.info("newAddressType end");
        return addresstypepojo.getAddressTypeID();
    }

    public void removeAddressType(int ID) {
        LOGGER.info("removeAddressType start");
        try {
            addresstypepojo.setAddressTypeID(ID);
            addressdao.deleteAddressType(addresstypepojo);
            addressdao.finalize();
        } catch (Exception E) {
            System.out.println("Has to be an existing AddressType.");
        }
        LOGGER.info("removeAddressType end");
    }

    public String editAddressType(int id, String addressType) {
        LOGGER.info("editAddressType start");
        try {
            addresstypepojo.setAddressTypeID(id);
            addresstypepojo = addressdao.getAddressType(addresstypepojo);
            addressdao.updateAddressType(addresstypepojo);
            addressdao.finalize();
            LOGGER.info("editAddressType end");
            return "AddressType has been edited.";
        } catch (Exception E) {
            return "AddressType not found.";
        }
    }
}
