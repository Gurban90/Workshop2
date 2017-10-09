/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AddressDAO;
import Interface.AddressDAOInterface;
import POJO.AddressTypePOJO;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class AddressTypeController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());
    private AddressDAOInterface addressdao;
    private AddressTypePOJO addresstypepojo;

    public AddressTypeController() {
        addressdao = new AddressDAO();
        addresstypepojo = new AddressTypePOJO();
    }
    
    public AddressTypeController(AddressDAOInterface addressdao) {
        this.addressdao = addressdao;
        addresstypepojo = new AddressTypePOJO();
    }

    public List<AddressTypePOJO> findAllAddressTypes() {
        LOGGER.info("findAllAddressTypes start");
        LOGGER.info("findAllAddressTypes end");
        return addressdao.getAllAddressType();
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
        LOGGER.info("newAddressType end");
        return addressdao.addAddressType(addresstypepojo);
    }

    public void removeAddressType(int ID) {
        LOGGER.info("removeAddressType start");
        addresstypepojo.setAddressTypeID(ID);
        addressdao.deleteAddressType(addresstypepojo);
        LOGGER.info("removeAddressType end");
    }

    public String editAddressType(int id, String addressType) {
        LOGGER.info("editAddressType start");
        addresstypepojo.setAddressTypeID(id);
        addresstypepojo.setAddressType(addressType);
        addressdao.updateAddressType(addresstypepojo);
        LOGGER.info("editAddressType end");
        return "AddressType has been edited: ";
    }
}
