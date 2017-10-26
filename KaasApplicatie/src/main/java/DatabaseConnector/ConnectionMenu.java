/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnector;

import Account.LoginMenu;
import DatabaseConnector.DomXML;
import Helper.Validator;
import MainMenu.MainMenu;
import java.util.Scanner;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
@ComponentScan({"Account", "Helper"})
public class ConnectionMenu {

    Logger logger = Logger.getLogger(MainMenu.class.getName());

    private Scanner input;
    private int choice;
    @Autowired
    private Validator validator;
    @Autowired
    private DomXML data;
    @Autowired
    private LoginMenu menu;

    public void connectionMenu() {
        logger.info("ConnectionMenu start");
        input = new Scanner(System.in);

        System.out.print(" Connection menu, please select a connection type: " + "\n"
                + "1. Hikari and MySQL" + "\n"
                + "2. JDBC and MySQL" + "\n"
                + "3. MongoDB" + "\n"
                + "4. PostGreSQL, Hibernate and Spring." + "\n"
                + "5. Exit" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    data.setDatabaseType("mysql");
                    data.setConnectionType("hikari");
                    menu.loginMenu();
                    break;
                case 2:
                    data.setDatabaseType("mysql");
                    data.setConnectionType("jdbc");
                    menu.loginMenu();
                    break;
                case 3:
                    data.setDatabaseType("mongodb");
                    menu.loginMenu();
                    break;
                case 4:
                    data.setDatabaseType("hibernate");
                    menu.loginMenu();
                    break;
                case 5:
                    System.out.println("Goodbye...");
                    System.exit(0);
                default:
                    System.out.println("Wrong number, try again.");
                    connectionMenu();
            }
        } else {
            System.out.println("Choice must be an integer.");
            connectionMenu();
        }

        logger.info("ConnectionMenu end");
    }

}
