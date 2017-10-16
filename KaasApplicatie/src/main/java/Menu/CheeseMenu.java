package Menu;

import Controller.CheeseController;
import DatabaseConnector.DomXML;
import Helper.DaoFactory;
import Helper.Validator;
import POJO.CheesePOJO;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class CheeseMenu {

    static final Logger LOGGER = Logger.getLogger(CheeseMenu.class.getName());

    private Scanner input;
    private int choice;
       private CheeseController controller;
    private int id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String cheeseId;
    private String stockString;
    private String cheesePrice;
    private Validator validator;

    public void cheeseMenu() {

        LOGGER.info("CheeseMenu start");

        input = new Scanner(System.in);
        controller = new CheeseController();
        validator = new Validator();

        System.out.print(" Cheese menu: " + "\n"
                + "1. New Cheese" + "\n"
                + "2. Remove Cheese" + "\n"
                + "3. Edit Cheese" + "\n"
                + "4. Search Cheese with ID" + "\n"
                + "5. Search Cheese with CheeseName" + "\n"
                + "6. Get All Cheese" + "\n"
                + "7. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    newCheese();
                    break;
                case 2:
                    removeCheese();
                    break;
                case 3:
                    editCheeseMenu();
                    break;
                case 4:
                    getCheeseWithID();
                    break;
                case 5:
                    getCheeseWithCheeseName();
                    break;
                case 6:
                    System.out.println(controller.findAllCheese());
                    cheeseMenu();
                    break;
                case 7:
                    LOGGER.info("Open MainMenu");
                    MainMenu mainmenu = new MainMenu();
                    mainmenu.mainMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    cheeseMenu();

            }
            LOGGER.info("CheeseMenu end");
        } else {
            System.out.println("Choice must be an integer.");
            cheeseMenu();
        }
    }

    public void editCheeseMenu() {
        LOGGER.info("editCheeseMenu start");
        System.out.print(" What do you want to edit? " + "\n"
                + "1. Name" + "\n"
                + "2. Price" + "\n"
                + "3. Stock" + "\n"
                + "4. All" + "\n"
                + "5. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber2 = input.nextLine();
        if (validator.menuValidator(choiceNumber2)) {

            int choice2 = Integer.parseInt(choiceNumber2);

            switch (choice2) {
                case 1:
                    editCheeseName();
                    break;
                case 2:
                    editCheesePrice();
                    break;
                case 3:
                    editCheeseStock();
                    break;
                case 4:
                    editCheeseAll();
                    break;
                case 5:
                    cheeseMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    editCheeseMenu();
            }
            LOGGER.info("editCheeseMenu start");
        } else {
            System.out.println("Choice must be an integer.");
            editCheeseMenu();
        }

    }

    private void newCheese() {
        System.out.print("Insert CheeseName: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
        } else {
            System.out.println("CheeseName cannot be empty.");
            cheeseMenu();
        }
        System.out.print("Insert Price: ");
        this.cheesePrice = input.nextLine();
        if (validator.priceValidator(this.cheesePrice)) {
            this.price = new BigDecimal(this.cheesePrice);
        } else {
            System.out.println("Price must be valid type like: 12.25");
            cheeseMenu();
        }
        System.out.print("Insert Stock: ");
        this.stockString = input.nextLine();
        if (validator.stockValidator(this.stockString)) {
            this.stock = Integer.parseInt(this.stockString);
            int cheeseID = controller.newCheese(name, price, stock);
            System.out.println("Cheese is added and has ID: " + cheeseID);
            cheeseMenu();
        } else {
            System.out.println("Stock must be an integer and between 0 and 1000.");
            cheeseMenu();
        }
    }

    private void removeCheese() {
        System.out.print("CheeseID please: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
            controller.removeCheese(id);
            cheeseMenu();
        } else {
            System.out.print("CheeseID must be an integer and between 1 and 1000.");
            cheeseMenu();
        }
    }

    private void getCheeseWithID() {
        System.out.print("CheeseID please: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
            CheesePOJO returnedcheese = controller.findCheese(id);
            System.out.println(returnedcheese);
            cheeseMenu();
        } else {
            System.out.println("CheeseID must be an integer and between 1 and 1000.");
            cheeseMenu();
        }
    }

    private void getCheeseWithCheeseName() {
        System.out.print("CheeseName please: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
            List<CheesePOJO> returnedcheeses = controller.findCheeseWithName(name);
            System.out.println(returnedcheeses);
            cheeseMenu();
        } else {
            System.out.println("CheeseName cannot be empty.");
            cheeseMenu();
        }
    }

    private void editCheeseName() {
        System.out.print("Insert CheeseID: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
        } else {
            System.out.println("CheeseID must be an integer and between 1 and 1000.");
            editCheeseMenu();
        }
        System.out.print("Insert new CheeseName: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
            System.out.println(controller.editCheeseName(id, name));
            cheeseMenu();
        } else {
            System.out.println("CheeseName must have a value.");
            editCheeseMenu();
        }
    }

    private void editCheesePrice() {
        System.out.print("Insert CheeseID: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
        } else {
            System.out.print("CheeseID must be an integer and between 1 and 1000.");
            editCheeseMenu();
        }
        System.out.print("Insert new CheesePrice: ");
        this.cheesePrice = input.nextLine();
        if (validator.priceValidator(this.cheesePrice)) {
            this.price = new BigDecimal(this.cheesePrice);
            System.out.println(controller.editCheesePrice(id, price));
            cheeseMenu();
        } else {
            System.out.println("Price must be valid type like: 12.25");
            editCheeseMenu();
        }
    }

    private void editCheeseStock() {
        System.out.print("Insert CheeseID: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
        } else {
            System.out.println("CheeseID must be an integer and between 1 and 1000.");
            editCheeseMenu();
        }
        System.out.print("Insert new CheeseStock: ");
        this.stockString = input.nextLine();
        if (validator.stockValidator(this.stockString)) {
            this.stock = Integer.parseInt(this.stockString);
            System.out.println(controller.editCheeseStock(id, stock));
            cheeseMenu();
        } else {
            System.out.print("Stock must be an integer and between 0 and 1000.");
            editCheeseMenu();
        }
    }

    private void editCheeseAll() {
        System.out.print("Insert CheeseID: ");
        this.cheeseId = input.nextLine();
        if (validator.idValidator(this.cheeseId)) {
            this.id = Integer.parseInt(this.cheeseId);
        } else {
            System.out.println("CheeseID must be an integer and between 1 and 1000. ");
            editCheeseMenu();
        }
        System.out.print("Insert new CheeseName: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
        } else {
            System.out.println("CheeseName must have a value.");
            editCheeseMenu();
        }
        System.out.print("Insert new Price: ");
        this.cheesePrice = input.nextLine();
        if (validator.priceValidator(this.cheesePrice)) {
            this.price = new BigDecimal(this.cheesePrice);
        } else {
            System.out.println("Price must be valid type like: 12.25.");
            editCheeseMenu();
        }
        System.out.print("Insert new Stock: ");
        this.stockString = input.nextLine();
        if (validator.stockValidator(this.stockString)) {
            this.stock = Integer.parseInt(this.stockString);
            System.out.println(controller.editCheese(id, name, price, stock));
            cheeseMenu();
        } else {
            System.out.println("Stock must be an integer and between 0 and 1000.");
            editCheeseMenu();
        }
    }
}
