/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Controller.AddressTypeController;
import DatabaseConnector.DomXML;
import Helper.DaoFactory;
import Helper.Validator;
import POJO.AddressTypePOJO;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class AddressTypeMenu {

    static final Logger LOGGER = Logger.getLogger(AddressTypeMenu.class.getName());

    private int id;
    private String addresstype;
    private Scanner input;
    private int choice;
    private String idString;
    private AddressTypeController controller;
    private Validator validator;

    public void addressTypeMenu() {

        LOGGER.info("addressTypeMenu start");
        controller = new AddressTypeController();
        validator = new Validator();
        input = new Scanner(System.in);

        System.out.print(" AddressType menu: " + "\n"
                + "1. New AddressType" + "\n"
                + "2. Remove AddressType" + "\n"
                + "3. Edit AddressType" + "\n"
                + "4. Search AddressType with ID" + "\n"
                + "5. Get All AddressTypes" + "\n"
                + "6. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    newAddressType();
                    break;
                case 2:
                    deleteAddressType();
                    break;
                case 3:
                    editAddressType();
                    break;
                case 4:
                    searchAddressTypeWithID();
                    break;
                case 5:
                    System.out.println(controller.findAllAddressTypes());
                    addressTypeMenu();
                    break;
                case 6:
                    LOGGER.info("Open AddressMenu");
                    AddressMenu menu = new AddressMenu();
                    menu.addressMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    addressTypeMenu();
            }
            LOGGER.info("addressTypeMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            addressTypeMenu();
        }
    }

    private void newAddressType() {
        System.out.print("Insert AddressType name: ");
        this.addresstype = input.nextLine();
        if (validator.stringValidator(this.addresstype)) {
            int addressTypeID = controller.newAddressType(addresstype);
            System.out.println("AddressType is added and has ID: " + addressTypeID);
            addressTypeMenu();
        } else {
            System.out.println("AddressType cannot be empty.");
            addressTypeMenu();
        }
    }

    private void deleteAddressType() {
        System.out.print("AddressTypeID please: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
            controller.removeAddressType(id);
            addressTypeMenu();
        } else {
            System.out.print("AddressTypeID must be an integer and between 1 and 1000.");
            addressTypeMenu();
        }
    }

    private void editAddressType() {
        System.out.print("Insert AddressTypeID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
        } else {
            System.out.println("AddressTypeID must be an integer and between 0 and 1000.");
            addressTypeMenu();
        }
        System.out.print("Insert new AddressType name: ");
        this.addresstype = input.nextLine();
        if (validator.stringValidator(this.addresstype)) {
            System.out.println(controller.editAddressType(id, addresstype));
            addressTypeMenu();
        } else {
            System.out.println("AddressType cannot be empty.");
            addressTypeMenu();
        }
    }

    private void searchAddressTypeWithID() {
        System.out.print("Insert AddressTypeID: ");
        this.idString = input.nextLine();
        if (validator.idValidator(this.idString)) {
            this.id = Integer.parseInt(this.idString);
            AddressTypePOJO returnedAddressType = controller.findAddressType(id);
            System.out.println(returnedAddressType);
            addressTypeMenu();
        } else {
            System.out.println("AddressTypeID must be an integer and between 1 and 1000.");
            addressTypeMenu();
        }
    }
}
