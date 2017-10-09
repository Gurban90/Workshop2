/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AddressDAO;
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

    public AddressController() {
        addressdao = new AddressDAO();
        addresspojo = new AddressPOJO();
        clientpojo = new ClientPOJO();
        addresstypepojo = new AddressTypePOJO();
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
        LOGGER.info("findAllAddress end");
        return addressdao.getAllAddress();
    }

    public AddressPOJO findAddress(int ID) {
        LOGGER.info("findAddress start");

        addresspojo.setAddressID(ID);
        AddressPOJO returnedAddress = addressdao.getAddress(addresspojo);
        LOGGER.info("findAddress end");
        return returnedAddress;
    }

    public List<AddressPOJO> findAddressWithClient(int clientID) {
        LOGGER.info("findAddressWithClient start");

        clientpojo.setClientID(clientID);
        List<AddressPOJO> returnedAddresses = addressdao.getAddressWithClient(clientpojo);
        LOGGER.info("findAddressWithClient end");
        return returnedAddresses;
    }

    public List<ClientPOJO> findAddressWithClientName(String lastName) {
        LOGGER.info("findAddressWithClientName start");
        List<ClientPOJO> returnedClients = clientdao.getClientWithLastName(lastName);

        LOGGER.info("findAddressWithClientName end");
        return returnedClients;
    }

    public int newAddress(int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city, int clientID, int addresstypeID) {
        LOGGER.info("newAddress start");
        addresspojo.setHouseNumber(houseNumber);
        addresspojo.setHouseNumberAddition(houseNumberAddition);
        addresspojo.setStreetName(streetName);
        addresspojo.setPostalCode(postalCode);
        addresspojo.setCity(city);
        addresspojo.setClientID(clientID);
        addresspojo.setAddressTypeID(addresstypeID);
        LOGGER.info("newAddress end");
        return addressdao.addAddress(addresspojo);
    }

    public void removeAddress(int ID) {
        LOGGER.info("removeAddress start");

        addresspojo.setAddressID(ID);
        addressdao.deleteAddress(addresspojo);
        LOGGER.info("removeAddress end");
    }

    public String editAddress(int id, int houseNumber, String houseNumberAddition, String streetName, String postalCode, String city) {
        LOGGER.info("editAddress start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setHouseNumber(houseNumber);
        addresspojo.setHouseNumberAddition(houseNumberAddition);
        addresspojo.setStreetName(streetName);
        addresspojo.setPostalCode(postalCode);
        addresspojo.setCity(city);

        addressdao.updateAddress(addresspojo);
        LOGGER.info("editAddress end");
        return "Address has been edited: ";
    }

    public String editHouseNumber(int id, int housenumber) {
        LOGGER.info("editHouseNumber start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setHouseNumber(housenumber);
        addressdao.updateAddress(addresspojo);
        LOGGER.info("editHouseNumber end");
        return "Address has been edited. ";
    }

    public String editHouseNumberAddition(int id, String housenumberaddition) {
        LOGGER.info("editHouseNumberAddition start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setHouseNumberAddition(housenumberaddition);
        addressdao.updateAddress(addresspojo);
        LOGGER.info("editHouseNumberAddition end");
        return "Address has been edited. ";
    }

    public String editStreetName(int id, String streetname) {
        LOGGER.info("editStreetName start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setStreetName(streetname);
        addressdao.updateAddress(addresspojo);
        LOGGER.info("editStreetName end");
        return "Address has been edited. ";
    }

    public String editPostalCode(int id, String postalcode) {
        LOGGER.info("editPostalCode start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setPostalCode(postalcode);
        addressdao.updateAddress(addresspojo);
        LOGGER.info("editPostalCode end");
        return "Address has been edited. ";
    }

    public String editCity(int id, String city) {
        LOGGER.info("editCity start");
        AddressPOJO getAddress = new AddressPOJO();

        getAddress.setAddressID(id);
        addresspojo = addressdao.getAddress(getAddress);
        addresspojo.setCity(city);
        addressdao.updateAddress(addresspojo);
        LOGGER.info("editCity end");
        return "Address has been edited. ";
    }

}
