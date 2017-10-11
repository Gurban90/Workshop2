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
import POJO.AddressPOJO;
import POJO.ClientPOJO;
import POJO.AddressTypePOJO;

import java.util.List;
import java.util.logging.Logger;

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

    public List<AddressPOJO> findAllAddress() {
        LOGGER.info("findAllAddress start");
        List<AddressPOJO> addresses = hibAddressDAO.getAll();
        hibAddressDAO.finalize();
        LOGGER.info("findAllAddress end");
        return addresses;
    }

    public AddressPOJO findAddress(int ID) {
        LOGGER.info("findAddress start");
        AddressPOJO returnedAddress = hibAddressDAO.findById(AddressPOJO.class, ID);
        hibAddressDAO.finalize();
        LOGGER.info("findAddress end");
        return returnedAddress;
    }

    public List<AddressPOJO> findAddressWithClient(int clientID) {
        LOGGER.info("findAddressWithClient start");
        List<AddressPOJO> returnedAddresses = hibAddressDAO.getAddressWithClient(clientID);
        hibAddressDAO.finalize();
        LOGGER.info("findAddressWithClient end");
        return returnedAddresses;
    }

    public List<ClientPOJO> findAddressWithClientName(String lastName) {/////////////////////////////////////
        LOGGER.info("findAddressWithClientName start");
        List<ClientPOJO> returnedClients = hibClientDAO.getClientWithLastName(lastName);
        hibClientDAO.finalize();
        LOGGER.info("findAddressWithClientName end");
        return returnedClients;
    }

    public int newAddress(int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city, int clientID, int addresstypeID) {
        LOGGER.info("newAddress start");
        try {
            addresspojo.setHouseNumber(houseNumber);
            addresspojo.setHouseNumberAddition(houseNumberAddition);
            addresspojo.setStreetName(streetName);
            addresspojo.setPostalCode(postalCode);
            addresspojo.setCity(city);
            addresspojo.setClient(hibClientDAO.findById(ClientPOJO.class, clientID));
            addresspojo.setAddresstype(hibAddressDAO.findById(AddressTypePOJO.class, addresstypeID));
        } catch (Exception E) {
            System.out.println("First create Client and AddressType");
        }
        hibAddressDAO.create(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("newAddress end");
        return addresspojo.getAddressID();
    }

    public void removeAddress(int ID) {
        LOGGER.info("removeAddress start");
        hibAddressDAO.delete(AddressPOJO.class, ID);
        hibAddressDAO.finalize();
        LOGGER.info("removeAddress end");
    }

    public String editAddress(int id, int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city) {
        LOGGER.info("editAddress start");
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
    }

    public String editHouseNumber(int id, int housenumber) {
        LOGGER.info("editHouseNumber start");
        addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
        addresspojo.setHouseNumber(housenumber);
        hibAddressDAO.update(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("editHouseNumber end");
        return "Address has been edited.";
    }

    public String editHouseNumberAddition(int id, String housenumberaddition) {
        LOGGER.info("editHouseNumberAddition start");
        addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
        addresspojo.setHouseNumberAddition(housenumberaddition);
        hibAddressDAO.update(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("editHouseNumberAddition end");
        return "Address has been edited.";
    }

    public String editStreetName(int id, String streetname) {
        LOGGER.info("editStreetName start");
        addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
        addresspojo.setStreetName(streetname);
        hibAddressDAO.update(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("editStreetName end");
        return "Address has been edited.";
    }

    public String editPostalCode(int id, String postalcode) {
        LOGGER.info("editPostalCode start");
        addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
        addresspojo.setPostalCode(postalcode);
        hibAddressDAO.update(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("editPostalCode end");
        return "Address has been edited.";
    }

    public String editCity(int id, String city) {
        LOGGER.info("editCity start");
        addresspojo = hibAddressDAO.findById(AddressPOJO.class, id);
        addresspojo.setCity(city);
        hibAddressDAO.update(addresspojo);
        hibAddressDAO.finalize();
        LOGGER.info("editCity end");
        return "Address has been edited.";
    }

}
