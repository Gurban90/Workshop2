/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Controller.MenuController;
import Helper.Validator;
import java.util.*;
import java.util.logging.Logger;

public class MainMenu {

    private Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

    private Scanner input;
    private int choice;
    Validator validator;
    MenuController menu;

    public void mainMenu() {
        LOGGER.info("mainMenu start");
        validator = new Validator();
        input = new Scanner(System.in);
        menu = new MenuController();

        System.out.print(" Main menu: " + "\n"
                + "1. Order Menu" + "\n"
                + "2. Cheese Menu" + "\n"
                + "3. Client Menu" + "\n"
                + "4. Go back to loginMenu" + "\n"
                + "5. Exit" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    LOGGER.info("Open OrderMenu");
                    menu.goToOrder();
                    break;
                case 2:
                    LOGGER.info("Open CheeseMenu");
                    menu.goToCheese();
                    break;
                case 3:
                    LOGGER.info("Open ClientMenu");
                    menu.goToClient();
                    break;
                case 4:
                    LOGGER.info("Open LoginMenu");
                    menu.goToLogin();
                    break;
                case 5:
                    System.out.println("Goodbye...");
                    System.exit(0);
                default:
                    System.out.println("Wrong number, try again.");
                    MainMenu mainmenu = new MainMenu();
                    mainmenu.mainMenu();
            }
        } else {
            System.out.println("Choice must be an integer.");
            MainMenu mainmenu = new MainMenu();
            mainmenu.mainMenu();
        }

        LOGGER.info("mainMenu end");
    }

}
