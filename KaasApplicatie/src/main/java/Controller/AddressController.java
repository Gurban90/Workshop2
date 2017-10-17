/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helper.HibernateDaoFactory;
import HibernateDao.HibernateAddressDAO;
import HibernateDao.HibernateClientDAO;
import Interface.AddressDAOInterface;
import Interface.ClientDAOInterface;
import Menu.AddressMenu;
import POJO.AddressPOJO;
import POJO.ClientPOJO;
import POJO.AddressTypePOJO;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.hibernate.HibernateException;

/**
 *
 * @author Gerben
 */
public class AddressController {

    private static final Logger LOGGER = Logger.getLogger(CheeseController.class.getName());
    private AddressDAOInterface addressdao;
    private AddressPOJO addresspojo;
    private ClientPOJO clientpojo;
    private AddressTypePOJO addresstypepojo;
    private ClientDAOInterface clientdao;
    private HibernateAddressDAO hibAddressDAO;
    private HibernateClientDAO hibClientDAO;

    public AddressController() {
        hibAddressDAO = (HibernateAddressDAO) HibernateDaoFactory.getInstance().getDao("address");
        addresspojo = new AddressPOJO();
        clientpojo = new ClientPOJO();
        addresstypepojo = new AddressTypePOJO();
        hibClientDAO = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

    }

    public AddressController(AddressDAOInterface addressdao, ClientDAOInterface clientdao) {
        this.addressdao = addressdao;
        this.addresspojo = new AddressPOJO();
        this.clientpojo = new ClientPOJO();
        this.addresstypepojo = new AddressTypePOJO();
        this.clientdao = clientdao;
    }

    public void findAllAddress() {
        LOGGER.info("findAllAddress start");
        System.out.println(hibAddressDAO.getAll());
        hibAddressDAO.finalize();
        LOGGER.info("findAllAddress end");
    }

    public void findAddress(int ID) {
        LOGGER.info("findAddress start");
        System.out.println(hibAddressDAO.findById(AddressPOJO.class, ID));
        hibAddressDAO.finalize();
        LOGGER.info("findAddress end");

    }

    public void findAddressWithClient(int clientID) {
        LOGGER.info("findAddressWithClient start");
        System.out.println(hibAddressDAO.getAddressWithClient(clientID));
        hibAddressDAO.finalize();
        LOGGER.info("findAddressWithClient end");
    }

    public void findAddressWithClientName(String lastName) {
        LOGGER.info("findAddressWithClientName start");
        System.out.println(hibClientDAO.getClientWithLastName(lastName));
        hibClientDAO.finalize();
        LOGGER.info("findAddressWithClientName end");
    }

    public void newAddress(int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city, int clientID, int addresstypeID) {
        LOGGER.info("newAddress start");
        addresspojo.setHouseNumber(houseNumber);
        addresspojo.setHouseNumberAddition(houseNumberAddition);
        addresspojo.setStreetName(streetName);
        addresspojo.setPostalCode(postalCode);
        addresspojo.setCity(city);
        addresspojo.setClient(hibClientDAO.findById(ClientPOJO.class, clientID));
        addresspojo.setAddresstype(hibAddressDAO.findById(AddressTypePOJO.class, addresstypeID));
        try {
            addresspojo.getClient().getClientID();
            addresspojo.getAddresstype().getAddressTypeID();
        } catch (Exception E) {
            System.out.println("First add the corresponding Client and AddressType.");
            return;
        }
        hibAddressDAO.create(addresspojo);
        hibAddressDAO.finalize();
        System.out.println("Address is added and has ID: " + addresspojo.getAddressID());
        LOGGER.info("newAddress end");
    }

    public void removeAddress(int ID) {
        LOGGER.info("removeAddress start");
        try {
            hibAddressDAO.delete(AddressPOJO.class, ID);
            hibAddressDAO.finalize();
        } catch (Exception E) {
            System.out.println("Has to be an existing Address.");
        }
        LOGGER.info("removeAddress end");
    }

    public String editAddress(int id, int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city) {
        LOGGER.info("editAddress start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setHouseNumber(houseNumber);
            addresspojo.setHouseNumberAddition(houseNumberAddition);
            addresspojo.setStreetName(streetName);
            addresspojo.setPostalCode(postalCode);
            addresspojo.setCity(city);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editAddress end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editHouseNumber(int id, int housenumber) {
        LOGGER.info("editHouseNumber start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setHouseNumber(housenumber);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editHouseNumber end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editHouseNumberAddition(int id, String housenumberaddition) {
        LOGGER.info("editHouseNumberAddition start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setHouseNumberAddition(housenumberaddition);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editHouseNumberAddition end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editStreetName(int id, String streetname) {
        LOGGER.info("editStreetName start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setStreetName(streetname);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editStreetName end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editPostalCode(int id, String postalcode) {
        LOGGER.info("editPostalCode start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setPostalCode(postalcode);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editPostalCode end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editCity(int id, String city) {
        LOGGER.info("editCity start");
        try {
            addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
            addresspojo.setCity(city);
            hibAddressDAO.update(addresspojo);
            hibAddressDAO.finalize();
            LOGGER.info("editCity end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

}
