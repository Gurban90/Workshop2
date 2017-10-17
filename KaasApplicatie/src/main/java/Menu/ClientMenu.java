package Menu;

import Controller.ClientController;
import Controller.MenuController;
import Helper.Validator;
import POJO.ClientPOJO;
import java.util.*;
import java.util.logging.Logger;

public class ClientMenu {

    static final Logger LOGGER = Logger.getLogger(ClientMenu.class.getName());
    private Scanner input;
    private int choice;
    private int editChoice;
    private int searchChoice;
    private int clientID;
    private String firstName;
    private String lastName;
    private String eMail;
    private String anwser;
    private String ClientIDString;
    ClientPOJO returnedClient;
    List<ClientPOJO> returnedClientList;
    private String accountIDString;
    private int accountID;
    private MenuController menu;
    private ClientController controller;
    private Validator validator;

    public void clientMenu() {

        LOGGER.info("clientMenu start");
        input = new Scanner(System.in);
        controller = new ClientController();
        validator = new Validator();
        input = new Scanner(System.in);
        menu = new MenuController();

        System.out.print(" Client menu: " + "\n"
                + "1. New Client" + "\n"
                + "2. Remove Client" + "\n"
                + "3. Edit Client" + "\n"
                + "4. Search Client" + "\n"
                + "5  Go to Address menu" + "\n"
                + "6. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    newClient();
                    break;
                case 2:
                    deleteClient();
                    break;
                case 3:
                    editClientMenu();
                    break;
                case 4:
                    searchClientMenu();
                    break;
                case 5:
                    LOGGER.info("Open AddressMenu");
                    menu.goToAddress();
                    break;
                case 6:
                    LOGGER.info("Open MainMenu");
                    menu.goToMain();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    clientMenu();

            }
            LOGGER.info("clientMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            clientMenu();
        }
    }

    public void editClientMenu() {

        System.out.print(" Client menu: " + "\n"
                + "1. Show all clients" + "\n"
                + "2. Alter first Name" + "\n"
                + "3. Alter last Name" + "\n"
                + "4. Alter E-mail" + "\n"
                + "5. Edit all" + "\n"
                + "6. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber2 = input.nextLine();
        if (validator.menuValidator(choiceNumber2)) {

            editChoice = Integer.parseInt(choiceNumber2);

            switch (editChoice) {

                case 1:
                    LOGGER.info("showallClient start");
                    controller.getAllClients();
                    LOGGER.info("showallClient start");
                    clientMenu();
                    break;
                case 2:
                    editClientFirstName();
                    break;
                case 3:
                    editClientLastName();
                    break;
                case 4:
                    editClientEmail();
                    break;
                case 5:
                    editClientAll();
                    break;
                case 6:
                    clientMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    editClientMenu();
            }
            LOGGER.info("editclientMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            editClientMenu();
        }
    }

    public void searchClientMenu() {

        System.out.print(" Client menu: " + "\n"
                + "1. Show all clients" + "\n"
                + "2. Search on ID" + "\n"
                + "3. Search on first name" + "\n"
                + "4  Search on last name" + "\n"
                + "5  Search on E-Mail" + "\n"
                + "6. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber3 = input.nextLine();
        if (validator.menuValidator(choiceNumber3)) {

            searchChoice = Integer.parseInt(choiceNumber3);

            switch (searchChoice) {

                case 1:
                    LOGGER.info("showallClient start");
                    controller.getAllClients();
                    LOGGER.info("showallClient end");
                    clientMenu();
                    break;
                case 2:
                    searchClientWithID();
                    break;
                case 3:
                    searchClientWithFirstName();
                    break;
                case 4:
                    searchClientWithLastName();
                    break;
                case 5:
                    searchClientWithEmail();
                    break;
                case 6:
                    clientMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    searchClientMenu();
            }
        } else {
            System.out.println("Choice must be an integer.");
            searchClientMenu();
        }
    }

    private void newClient() {
        LOGGER.info("newClient start");
        System.out.print("Insert Client First Name: ");
        firstName = input.nextLine();
        if (validator.stringValidator(firstName)) {
        } else {
            System.out.println("First name cannot be empty.");
            clientMenu();
        }
        System.out.print("Insert your AccountID: ");
        accountIDString = input.nextLine();
        if (validator.idValidator(this.accountIDString)) {
            accountID = Integer.parseInt(this.accountIDString);
        } else {
            System.out.println("AccountID cannot be empty.");
            clientMenu();
        }
        System.out.print("Insert Client Last name: ");
        lastName = input.nextLine();
        if (validator.stringValidator(lastName)) {
        } else {
            System.out.println("Last name cannot be empty. ");
            clientMenu();
        }
        System.out.print("Insert Client email: ");
        eMail = input.nextLine();
        if (validator.eMailValidator(eMail)) {
            controller.newClient(this.firstName, this.lastName, this.eMail, this.accountID);
            clientMenu();
            LOGGER.info("newClient end");
        } else {
            System.out.println("E-mail must be a valid e-mail address.");
            clientMenu();
        }
    }

    private void deleteClient() {
        LOGGER.info("removeClient start");
        System.out.println("This will delete your account too! To cancel leave ClientID empty.");
        System.out.print("Enter The clientID you want to remove: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            clientID = Integer.parseInt(this.ClientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            clientMenu();
        }
        controller.removeClient(clientID);
        LOGGER.info("removeClient end");
        clientMenu();

    }

    private void editClientFirstName() {
        System.out.print("Insert ClientID: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            clientID = Integer.parseInt(this.ClientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            editClientMenu();
        }
        System.out.print("Insert new First Name: ");
        this.firstName = input.nextLine();
        if (validator.stringValidator(this.firstName)) {
            System.out.println(controller.editClientFirstName(clientID, firstName));
            clientMenu();
        } else {
            System.out.println("First name cannot be empty.");
            editClientMenu();
        }
    }

    private void editClientLastName() {
        System.out.print("Insert ClientID: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            clientID = Integer.parseInt(this.ClientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            editClientMenu();
        }
        System.out.print("Insert last Name: ");
        this.lastName = input.nextLine();
        if (validator.stringValidator(this.lastName)) {
            System.out.println(controller.editClientLastName(clientID, lastName));
            clientMenu();
        } else {
            System.out.println("Last name cannot be empty. ");
            editClientMenu();
        }
    }

    private void editClientEmail() {
        System.out.print("Insert ClientID: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            clientID = Integer.parseInt(this.ClientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            editClientMenu();
        }
        System.out.print("Insert eMail: ");
        eMail = input.nextLine();
        if (validator.eMailValidator(eMail)) {

            System.out.println(controller.editClientEMail(clientID, eMail));
            clientMenu();
        } else {
            System.out.println("E-mail must be a valid e-mail address.");
            editClientMenu();
        }
    }

    private void editClientAll() {
        LOGGER.info("editClient start");
        System.out.print("Insert client ID: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            this.clientID = Integer.parseInt(this.ClientIDString);
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            editClientMenu();
        }
        System.out.print("Insert new Client First Name: ");
        this.firstName = input.nextLine();
        if (validator.stringValidator(this.firstName)) {
        } else {
            System.out.println("First name cannot be empty. ");
            editClientMenu();
        }
        System.out.print("Insert new Client Last Name: ");
        this.lastName = input.nextLine();
        if (validator.stringValidator(this.lastName)) {
        } else {
            System.out.println("Last Name cannot be empty. ");
            editClientMenu();
        }
        System.out.print("Insert new Client email: ");
        this.eMail = input.nextLine();
        if (validator.eMailValidator(this.eMail)) {
            controller.editClient(clientID, this.firstName, this.lastName, this.eMail);
            LOGGER.info("editClient end");
            clientMenu();
        } else {
            System.out.println("E-mail must be a valid e-mail address.");
            editClientMenu();
        }
    }

    private void searchClientWithID() {
        System.out.print("Insert client ID: ");
        ClientIDString = input.nextLine();
        if (validator.idValidator(this.ClientIDString)) {
            this.clientID = Integer.parseInt(this.ClientIDString);
            controller.findClientWithID(clientID);
            clientMenu();
        } else {
            System.out.println("ClientID must be an integer and between 1 and 1000.");
            searchClientMenu();
        }
    }

    private void searchClientWithFirstName() {
        System.out.print("Insert First name: ");
        this.firstName = input.nextLine();
        if (validator.stringValidator(this.firstName)) {
            controller.findClientWithFirstName(firstName);
            clientMenu();
        } else {
            System.out.println("First name cannot be empty.");
            searchClientMenu();
        }
    }

    private void searchClientWithLastName() {
        System.out.print("Insert Last name: ");
        this.lastName = input.nextLine();
        if (validator.stringValidator(this.lastName)) {
            controller.findClientWithLastName(lastName);
            clientMenu();
        } else {
            System.out.println("Last Name cannot be empty.");
            searchClientMenu();
        }
    }

    private void searchClientWithEmail() {
        System.out.print("Insert E-mail address: ");
        this.eMail = input.nextLine();
        if (validator.eMailValidator(this.eMail)) {
            controller.findClientWithEMail(eMail);
            clientMenu();
        } else {
            System.out.println("E-mail must be a valid e-mail address.");
            searchClientMenu();
        }
    }
}
