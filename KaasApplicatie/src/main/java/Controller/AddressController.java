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
   

    public AddressController() {
        addressdao = (HibernateAddressDAO) HibernateDaoFactory.getInstance().getDao("address");
        addresspojo = new AddressPOJO();
        clientpojo = new ClientPOJO();
        addresstypepojo = new AddressTypePOJO();
        clientdao = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

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
        System.out.println(addressdao.getAllAddress());
        addressdao.finalize();
        LOGGER.info("findAllAddress end");
    }

    public void findAddress(int ID) {
        LOGGER.info("findAddress start");
        addresspojo.setAddressID(ID);
        addresspojo = addressdao.getAddress(addresspojo);
        System.out.println(addresspojo);
        addressdao.finalize();
        LOGGER.info("findAddress end");

    }

    public void findAddressWithClient(int clientID) {
        LOGGER.info("findAddressWithClient start");
        clientpojo = new ClientPOJO();
        clientpojo.setClientID(clientID);
        List<AddressPOJO> addresses = addressdao.getAddressWithClient(clientpojo);
        System.out.println(addresses);
        addressdao.finalize();
        LOGGER.info("findAddressWithClient end");
    }

    public void findAddressWithClientName(String lastName) {
        LOGGER.info("findAddressWithClientName start");
        System.out.println(clientdao.getClientWithLastName(lastName));
        clientdao.finalize();
        LOGGER.info("findAddressWithClientName end");
    }

    public void newAddress(int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city, int clientID, int addresstypeID) {
        LOGGER.info("newAddress start");
        addresspojo.setHouseNumber(houseNumber);
        addresspojo.setHouseNumberAddition(houseNumberAddition);
        addresspojo.setStreetName(streetName);
        addresspojo.setPostalCode(postalCode);
        addresspojo.setCity(city);
        clientpojo.setClientID(clientID);
        addresstypepojo.setAddressTypeID(addresstypeID);
        addresspojo.setClient(clientdao.getClient(clientpojo));
        addresspojo.setAddresstype(addressdao.getAddressType(addresstypepojo));
        try {
            addresspojo.getClient().getClientID();
            addresspojo.getAddresstype().getAddressTypeID();
        } catch (Exception E) {
            System.out.println("First add the corresponding Client and AddressType.");
            return;
        }
        addressdao.addAddress(addresspojo);
        addressdao.finalize();
        System.out.println("Address is added and has ID: " + addresspojo.getAddressID());
        LOGGER.info("newAddress end");
    }

    public void removeAddress(int ID) {
        LOGGER.info("removeAddress start");
        try {
            addresspojo.setAddressID(ID);
            addressdao.deleteAddress(addresspojo);
            addressdao.finalize();
        } catch (Exception E) {
            System.out.println("Has to be an existing Address.");
        }
        LOGGER.info("removeAddress end");
    }

    public String editAddress(int id, int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city) {
        LOGGER.info("editAddress start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setHouseNumber(houseNumber);
            addresspojo.setHouseNumberAddition(houseNumberAddition);
            addresspojo.setStreetName(streetName);
            addresspojo.setPostalCode(postalCode);
            addresspojo.setCity(city);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editAddress end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editHouseNumber(int id, int housenumber) {
        LOGGER.info("editHouseNumber start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setHouseNumber(housenumber);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editHouseNumber end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editHouseNumberAddition(int id, String housenumberaddition) {
        LOGGER.info("editHouseNumberAddition start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setHouseNumberAddition(housenumberaddition);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editHouseNumberAddition end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editStreetName(int id, String streetname) {
        LOGGER.info("editStreetName start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setStreetName(streetname);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editStreetName end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editPostalCode(int id, String postalcode) {
        LOGGER.info("editPostalCode start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setPostalCode(postalcode);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editPostalCode end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

    public String editCity(int id, String city) {
        LOGGER.info("editCity start");
        try {
            addresspojo.setAddressID(id);
            addresspojo = addressdao.getAddress(addresspojo);
            addresspojo.setCity(city);
            addressdao.updateAddress(addresspojo);
            addressdao.finalize();
            LOGGER.info("editCity end");
            return "Address has been edited.";
        } catch (Exception E) {
            return "Address not found.";
        }
    }

}
