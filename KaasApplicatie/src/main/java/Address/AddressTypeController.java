/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Address;

import Cheese.CheeseController;
import Address.AddressDAO;
import Helper.HibernateDaoFactory;
import Address.HibernateAddressDAO;
import Address.AddressDAOInterface;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class AddressTypeController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());
    @Autowired
    private AddressDAOInterface addressdao;
    @Autowired
    private AddressTypePOJO addresstypepojo;

    public AddressTypeController() {

    }

    public AddressTypeController(AddressDAOInterface addressdao) {
        this.addressdao = addressdao;
    }

    public List<AddressTypePOJO> findAllAddressTypes() {
        LOGGER.info("findAllAddressTypes start");
        List<AddressTypePOJO> addressTypes = addressdao.getAllAddressType();
        LOGGER.info("findAllAddressTypes end");
        return addressTypes;
    }

    public AddressTypePOJO findAddressType(int ID) {
        LOGGER.info("findAddressType start");
        addresstypepojo.setAddressTypeID(ID);
        AddressTypePOJO returnedAddressType = addressdao.getAddressType(addresstypepojo);
        LOGGER.info("findAddressType end");
        return returnedAddressType;
    }

    public int newAddressType(String addressType) {
        LOGGER.info("newAddressType start");
        addresstypepojo.setAddressType(addressType);
        addressdao.addAddressType(addresstypepojo);
        LOGGER.info("newAddressType end");
        return addresstypepojo.getAddressTypeID();
    }

    public void removeAddressType(int ID) {
        LOGGER.info("removeAddressType start");
        try {
            addresstypepojo.setAddressTypeID(ID);
            addressdao.deleteAddressType(addresstypepojo);
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
            LOGGER.info("editAddressType end");
            return "AddressType has been edited.";
        } catch (Exception E) {
            return "AddressType not found.";
        }
    }
}
