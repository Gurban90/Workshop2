/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Controller.AccountController;
import Helper.TokenCreator;
import Helper.Validator;
import POJO.AccountPOJO;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class LoginMenu {

    Logger logger = Logger.getLogger(LoginMenu.class.getName());
    private Scanner input;
    private int choice;
    private int id;
    private int findId;
    private String name;
    private String password;
    private int status;
    private String accountIdString;
    private String accounStatusString;

    private AccountController controller;
    private Validator validator;
    private TokenCreator token;

    public void loginMenu() {

        controller = new AccountController();
        validator = new Validator();
        input = new Scanner(System.in);
        token = new TokenCreator();

        System.out.println("Welcome to Applikaasie.... " + "\n"
                + "1. Log in with an existing account " + "\n"
                + "2. Create account " + "\n"
                + "3. Update account " + "\n"
                + "4. Remove account " + "\n"
                + "5. Search for an account with AccountID" + "\n"
                + "6. Search for an account with Accountname" + "\n"
                + "7. Get all accounts" + "\n"
                + "8. Go to main menu" + "\n"
                + "9. Exit" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber = input.nextLine();
        if (validator.menuValidator(choiceNumber)) {

            choice = Integer.parseInt(choiceNumber);

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    updateAccount();
                    break;
                case 4:
                    deleteAccount();
                    break;
                case 5:
                    searchAccountWithID();
                    break;
                case 6:
                    searchAccountWithName();
                    break;
                case 7:
                    getAllAccounts();
                    break;
                case 8:
                    goToMain();
                    break;
                case 9:
                    System.out.println("Goodbye...");
                    System.exit(0);
                default:
                    System.out.println("Wrong number, try again.");
                    loginMenu();
            }
        } else {
            System.out.println("Choice must be an integer.");
            loginMenu();
        }

    }

    public void updateAccountMenu() {
        System.out.println("What do you want to edit?" + "\n"
                + "1. Edit accountname " + "\n"
                + "2. Edit password " + "\n"
                + "3. Edit accountstatus " + "\n"
                + "4. Edit all " + "\n"
                + "5. Return to last menu" + "\n"
                + "Please enter the number of your choice: ");

        String choiceNumber2 = input.nextLine();
        if (validator.menuValidator(choiceNumber2)) {

            int choice2 = Integer.parseInt(choiceNumber2);

            switch (choice2) {
                case 1:
                    editAccountName();
                    break;
                case 2:
                    editPassword();
                    break;
                case 3:
                    editAccountStatus();
                    break;
                case 4:
                    editAccountAll();
                    break;
                case 5:
                    loginMenu();
                    break;
                default:
                    System.out.println("Wrong number, try again.");
                    updateAccountMenu();
            }
        } else {
            System.out.println("Choice must be an integer. ");
            updateAccountMenu();
        }
    }

    private void login() {
        if (token.checkJWT()) {
            System.out.println("You are already logged in.");
        } else {
            System.out.print("Please enter your account number: ");
            this.accountIdString = input.nextLine();
            if (validator.idValidator(this.accountIdString)) {
                this.id = Integer.parseInt(this.accountIdString);
            } else {
                System.out.println("AccountID must be an integer and between 1 and 1000.");
                loginMenu();
            }
            System.out.print("Please enter your password: ");
            this.password = input.nextLine();
            if (validator.stringValidator(this.password)) {
            } else {
                System.out.println("Password must have a value.");
                loginMenu();
            }
            if (controller.login(id, password)) {
                System.out.println("You are logged in.");
                loginMenu();
            } else {
                System.out.println("Wrong password or accountnumber, try again.");
                loginMenu();
            }
        }
        loginMenu();
    }

    private void createAccount() {
        System.out.print("Insert Accountname: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
        } else {
            System.out.println("Accountname must have a value.");
            loginMenu();
        }
        System.out.print("Insert Password: ");
        this.password = input.nextLine();
        if (validator.stringValidator(this.password)) {
        } else {
            System.out.println("Password must have a value.");
            loginMenu();
        }
        System.out.print("Insert Accountstatus: ");
        this.accounStatusString = input.nextLine();
        if (validator.statusValidator(this.accounStatusString)) {
            this.status = Integer.parseInt(this.accounStatusString);
            int accountid = controller.newAccount(name, password, status);
            System.out.println("Account is added and has ID: " + accountid);
            loginMenu();
        } else {
            System.out.println("Status must be an integer and between 0 and 5.");
            loginMenu();
        }
    }

    private void updateAccount() {
        if (token.checkJWT()) {
            updateAccountMenu();
        } else {
            System.out.println("Please log in first.");
            loginMenu();
        }
    }

    private void deleteAccount() {
        System.out.println("THIS WILL DELETE YOUR ACCOUNT! To cancel do not fill in your AccountID or password.");
        System.out.print("AccountID please: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.id = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            loginMenu();
        }
        System.out.print("Please enter your password: ");
        this.password = input.nextLine();
        if (validator.stringValidator(this.password)) {
        } else {
            System.out.println("Password must have a value.");
            loginMenu();
        }
        if (controller.removeAccount(id, password)) {
            System.out.print("Account has been deleted.");
            loginMenu();
        } else {
            System.out.println("Wrong password or accountnumber, try again.");
            loginMenu();
        }
    }

    private void searchAccountWithID() {
        if (token.checkJWT()) {
        } else {
            System.out.println("Please log in first.");
            loginMenu();
        }
        System.out.print("AccountID of the account you want to find please: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.findId = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            loginMenu();
        }
        AccountPOJO returnedAccount = controller.findAccount(this.findId);
        System.out.println(returnedAccount);
        loginMenu();
    }

    private void searchAccountWithName() {
        if (token.checkJWT()) {
        } else {
            System.out.println("Please log in first.");
            loginMenu();
        }
        System.out.print("AccountName of the account you want to find please: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
        } else {
            System.out.println("AccountName must have a value.");
            loginMenu();
        }
        List<AccountPOJO> returnedAccounts = controller.findAccountWithName(name);
        System.out.println(returnedAccounts);
        loginMenu();

    }

    private void getAllAccounts() {
        if (token.checkJWT()) {
        } else {
            System.out.println("Please log in first.");
            loginMenu();
        }
        List<AccountPOJO> returnedAccounts = controller.getAllAccounts();
        System.out.println(returnedAccounts);
        loginMenu();

    }

    private void editAccountName() {
        System.out.print("Insert AccountID: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.id = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            updateAccountMenu();
        }
        System.out.print("Insert new AccountName: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
            System.out.println(controller.editAccountName(id, name));
            loginMenu();
        } else {
            System.out.println("Name must have a value.");
            updateAccountMenu();
        }
    }

    private void editPassword() {
        System.out.print("Insert AccountID: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.id = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            updateAccountMenu();
        }
        System.out.print("Insert new password: ");
        String password2 = input.nextLine();
        if (validator.stringValidator(password2)) {
            System.out.println(controller.editAccountPassword(id, password2));
            loginMenu();
        } else {
            System.out.println("Password must have a value.");
            updateAccountMenu();
        }
    }

    private void editAccountStatus() {
        System.out.print("Insert AccountID: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.id = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            updateAccountMenu();
        }
        System.out.print("Insert new AccountStatus: ");
        this.accounStatusString = input.nextLine();
        if (validator.statusValidator(this.accounStatusString)) {
            this.status = Integer.parseInt(this.accounStatusString);
            System.out.println(controller.editAccountStatus(id, status));
            loginMenu();
        } else {
            System.out.println("Status must be an integer and between 0 and 5.");
            updateAccountMenu();
        }
    }

    private void editAccountAll() {
        System.out.print("Insert AccountID: ");
        this.accountIdString = input.nextLine();
        if (validator.idValidator(this.accountIdString)) {
            this.id = Integer.parseInt(this.accountIdString);
        } else {
            System.out.println("AccountID must be an integer and between 1 and 1000.");
            updateAccountMenu();
        }
        System.out.print("Insert new Accountname: ");
        this.name = input.nextLine();
        if (validator.stringValidator(this.name)) {
        } else {
            System.out.println("Accountname must have a value.");
            updateAccountMenu();
        }
        System.out.print("Insert new Password: ");
        this.password = input.nextLine();
        if (validator.stringValidator(this.password)) {
        } else {
            System.out.println("Password must have a value.");
            updateAccountMenu();
        }
        System.out.print("Insert new Accountstatus: ");
        this.accounStatusString = input.nextLine();
        if (validator.statusValidator(this.accounStatusString)) {
            this.status = Integer.parseInt(this.accounStatusString);
            System.out.println(controller.updateAccount(id, name, password, status));
            loginMenu();
        } else {
            System.out.println("Status must be an integer and between 0 and 5.");
            updateAccountMenu();
        }
    }

    private void goToMain() {
        if (token.checkJWT()) {
            MainMenu mainmenu = new MainMenu();
            mainmenu.mainMenu();
        } else {
            System.out.println("Please log in first.");
            loginMenu();
        }
    }

}
