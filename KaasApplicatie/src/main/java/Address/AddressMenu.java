/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Address;

import Address.AddressController;
import DatabaseConnector.DomXML;
import Helper.DaoFactory;
import Helper.Validator;
import Address.AddressPOJO;
import Client.ClientPOJO;
import MainMenu.MenuController;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class AddressMenu {

    private static final Logger LOGGER = Logger.getLogger(AddressMenu.class.getName());
    private int id;
    private int housenumber;
    private String housenumberAddition;
    private String streetname;
    private String postalCode;
    private String city;
    private int clientID;
    private int addressTypeID;
    private Scanner input;
    private int choice;
    private String idString;
    private String houseNumberString;
    private String clientIDString;
    private String addressTypeIDString;
    private String clientLastName;
    @Autowired
    private AddressController controller;
    @Autowired
    private Validator validator;
    @Autowired
    private MenuController menu;

    public void addressMenu() {

        LOGGER.info("addressMenu start");
        input = new Scanner(System.in);

        System.out.print(" Address menu: " + "\n"
                + "1. New Address" + "\n"
                + "2. Remove Address" + "\n"
                + "3. Edit Address" + "\n"
                + "4. Search Address with ID" + "\n"
                + "5. Search Address with ClientID" + "\n"
                + "6. Search Address with Client last name" + "\n"
                + "7. Get All Addresses" + "\n"
                + "8. Go to AddressType menu" + "\n"
                + "9. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    newAddress();
                    break;
                case 2:
                    removeAddress();
                    break;
                case 3:
                    editAddressMenu();
                    break;
                case 4:
                    getAddressWithID();
                    break;
                case 5:
                    getAdddressWithClientID();
                    break;
                case 6:
                    getAddressWithClientLastName();
                    break;
                case 7:
                    controller.findAllAddress();
                    addressMenu();
                    break;
                case 8:
                    LOGGER.info("Open AddressTypeMenu");
                    menu.goToAddressType();
                    break;
                case 9:
                    LOGGER.info("Open ClientMenu");
                    menu.goToClient();
                default:
                    System.out.println("Wrong number, try again.");
                    addressMenu();

            }
            LOGGER.info("addressMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            addressMenu();
        }
    }

    public void editAddressMenu() {
        LOGGER.info("editAddressMenu start");
        System.out.print(" What do you want to edit? " + "\n"
                + "1. HouseNumber" + "\n"
                + "2. HouseNumber Addition" + "\n"
                + "3. Streetname" + "\n"
                + "4. Postalcode" + "\n"
                + "5. City" + "\n"
                + "6. All" + "\n"
                + "7. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber2 = input.nextLine();
        if (validator.menuValidator(choiceNumber2)) {

            int choice2 = Integer.parseInt(choiceNumber2);

            switch (choice2) {
                case 1:
                    editHouseNumber();
                    break;
                case 2:
                    editHouseNumberAddition();
                    break;
                case 3:
                    editStreetName();
                    break;
                case 4:
                    editPostalcode();
                    break;
                case 5:
                    editCity();
                    break;
                case 6:
                    editAddressAll();
                    break;
                case 7:
                    addressMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    editAddressMenu();
            }
            LOGGER.info("editAddressMenu start");
        } else {
            System.out.println("Choice must be an integer.");
            editAddressMenu();
        }

    }

    private void newAddress() {
        System.out.print("To add an Address, an existing clientID and Addresstype must be used.");
        System.out.print("Insert HouseNumber: ");
        this.houseNumberString = input.nextLine();
        if (validator.stockValidator(this.houseNumberString)) {
            this.housenumber = Integer.parseInt(this.houseNumberString);
        } else {
            System.out.println("Housenumber must be an integer and between 0 and 1000.");
            addressMenu();
        }
        System.out.print("Insert HouseNumber Addition: ");
        this.housenumberAddition = input.nextLine();
        System.out.print("Insert StreetName: ");
        this.streetname = input.nextLine();
        if (validator.stringValidator(this.streetname)) {
        } else {
            System.out.println("StreetName cannot be empty.");
            addressMenu();
        }
        System.out.print("Insert Postalcode: ");
        this.postalCode = input.nextLine();
        if (validator.stringValidator(this.postalCode)) {
        } else {
            System.out.println("PostalCode cannot be empty.");
            addressMenu();
        }
        System.out.print("Insert city: ");
        this.city = input.nextLine();
        if (validator.stringValidator(this.city)) {
        } else {
            System.out.println("City cannot be empty.");
            addressMenu();
        }

        System.out.print("Insert ClientID: ");
        this.clientIDString = input.nextLine();
        if (validator.idValidator(this.clientIDString)) {
            this.clientID = Integer.parseInt(this.clientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 0 and 1000.");
            addressMenu();
        }
        System.out.print("Insert AddressTypeID: ");
        this.addressTypeIDString = input.nextLine();
        if (validator.idValidator(this.addressTypeIDString)) {
            this.addressTypeID = Integer.parseInt(this.addressTypeIDString);
            controller.newAddress(housenumber, housenumberAddition, streetname, postalCode, city, clientID, addressTypeID);
            addressMenu();
        } else {
            System.out.println("AddressTypeID must be an integer and between 0 and 1000.");
            addressMenu();
        }
    }

    private void removeAddress() {
        System.out.print("AddressID please: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
            controller.removeAddress(id);
            addressMenu();
        } else {
            System.out.print("AddressID must be an integer and between 1 and 1000.");
            addressMenu();
        }
    }

    private void getAddressWithID() {
        System.out.print("AddressID please: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
            controller.findAddress(id);
            addressMenu();
        } else {
            System.out.println("AddressID must be an integer and between 1 and 1000.");
            addressMenu();
        }
    }

    private void getAdddressWithClientID() {
        System.out.print("ClientID please: ");
        this.clientIDString = input.nextLine();
        if (validator.stringValidator(this.clientIDString)) {
            this.clientID = Integer.parseInt(this.clientIDString);
            controller.findAddressWithClient(clientID);
            addressMenu();
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            addressMenu();
        }
    }

    private void getAddressWithClientLastName() {
        System.out.print("Client last name please: ");
        this.clientLastName = input.nextLine();
        if (validator.stringValidator(this.clientLastName)) {
            controller.findAddressWithClientName(clientLastName);
        } else {
            System.out.println("Client last name cannot be empty.");
            addressMenu();
        }
        System.out.print("ClientID please: ");
        this.clientIDString = input.nextLine();
        if (validator.stringValidator(this.clientIDString)) {
            this.clientID = Integer.parseInt(this.clientIDString);
            controller.findAddressWithClient(clientID);
            addressMenu();
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            addressMenu();
        }
    }

    private void editHouseNumber() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressID must be an integer and between 1 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new HouseNumber: ");
        this.houseNumberString = input.nextLine();
        if (validator.stockValidator(this.houseNumberString)) {
            this.housenumber = Integer.parseInt(this.houseNumberString);
            System.out.println(controller.editHouseNumber(id, housenumber));
            addressMenu();
        } else {
            System.out.println("Housenumber must be an integer and between 1 and 1000.");
            editAddressMenu();
        }

    }

    private void editHouseNumberAddition() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
            System.out.print("Insert new HouseNumber Addition: ");
            this.housenumberAddition = input.nextLine();
            System.out.println(controller.editHouseNumberAddition(id, housenumberAddition));
            addressMenu();
        } else {
            System.out.print("AddressID must be an integer and between 1 and 1000.");
            editAddressMenu();
        }
    }

    private void editStreetName() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressID must be an integer and between 1 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new Streetname: ");
        this.streetname = input.nextLine();
        if (validator.stringValidator(this.streetname)) {
            System.out.println(controller.editStreetName(id, streetname));
            addressMenu();
        } else {
            System.out.print("Streetname cannot be empty.");
            editAddressMenu();
        }
    }

    private void editPostalcode() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressID must be an integer and between 1 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new Postal Code: ");
        this.postalCode = input.nextLine();
        if (validator.stringValidator(this.postalCode)) {
            System.out.println(controller.editPostalCode(id, postalCode));
            addressMenu();
        } else {
            System.out.print("Postal Code cannot be empty.");
            editAddressMenu();
        }
    }

    private void editCity() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressID must be an integer and between 1 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new City: ");
        this.city = input.nextLine();
        if (validator.stringValidator(this.city)) {
            System.out.println(controller.editCity(id, city));
            addressMenu();
        } else {
            System.out.print("City cannot be empty.");
            editAddressMenu();
        }
    }

    private void editAddressAll() {
        System.out.print("Insert AddressID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressID must be an integer and between 0 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new HouseNumber: ");
        this.houseNumberString = input.nextLine();
        if (validator.stockValidator(this.houseNumberString)) {
            this.housenumber = Integer.parseInt(this.houseNumberString);
        } else {
            System.out.println("Housenumber must be an integer and between 0 and 1000.");
            editAddressMenu();
        }
        System.out.print("Insert new HouseNumber Addition: ");
        this.housenumberAddition = input.nextLine();
        System.out.print("Insert new Streetname: ");
        this.streetname = input.nextLine();
        if (validator.stringValidator(this.streetname)) {
        } else {
            System.out.println("Streetname must have a value.");
            editAddressMenu();
        }
        System.out.print("Insert new Postal Code: ");
        this.postalCode = input.nextLine();
        if (validator.stringValidator(this.postalCode)) {
        } else {
            System.out.println("Postal Code must have a value.");
            editAddressMenu();
        }
        System.out.print("Insert new City: ");
        this.city = input.nextLine();
        if (validator.stringValidator(this.city)) {
            System.out.println(controller.editAddress(id, housenumber, housenumberAddition, streetname, postalCode, city));
            addressMenu();
        } else {
            System.out.println("City must have a value.");
            editAddressMenu();
        }
    }
}
