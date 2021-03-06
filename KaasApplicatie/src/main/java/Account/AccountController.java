/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import Helper.PasswordHasher;
import Helper.TokenCreator;
import Helper.HibernateDaoFactory;
import Client.ClientPOJO;
import java.util.List;
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
@ComponentScan("Helper")
public class AccountController {

    private Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private Scanner input = new Scanner(System.in);
    private String saltedHashedPassword;
    private String salt;
    private String hashedPassword;
    private String totalPassword;

    @Autowired
    private AccountDAOInterface accountdao;
    @Autowired
    private AccountPOJO accountpojo;
    @Autowired
    private PasswordHasher passwordhasher;
    @Autowired
    private TokenCreator token;

    //Hibernate
    public AccountController() {

    }

    //Zonder Hibernate
    public AccountController(AccountDAOInterface accountdao) {
        this.accountdao = accountdao;
       
    }

    public boolean login(int id, String password) {
        LOGGER.info("login start");
        try {
            accountpojo.setAccountID(id);
            AccountPOJO foundAccount = accountdao.getAccount(accountpojo);
            salt = foundAccount.getAccountPassword().substring(32);
            hashedPassword = passwordhasher.hasher(password + salt);
            totalPassword = hashedPassword + salt;
            accountpojo.setAccountPassword(totalPassword);
            if (accountpojo.getAccountPassword().equals(foundAccount.getAccountPassword())) {
                token.jwtCreator(id);
                LOGGER.info("login end");
                return true;
            } else {
                LOGGER.info("login end");
                return false;
            }
        } catch (Exception E) {
            System.out.println("No account found.");
            return false;
        }
    }

    public int newAccount(String name, String password, int status) {
        LOGGER.info("newAccount start");
        accountpojo.setAccountName(name);
        saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
        accountpojo.setAccountPassword(saltedHashedPassword);
        accountpojo.setAccountStatus(status);
        LOGGER.info("newAccount end");
        accountdao.addAccount(accountpojo);
        return accountpojo.getAccountID();
    }

    public boolean removeAccount(int id, String password) {
        LOGGER.info("removeAccount start");
        try {
            accountpojo.setAccountID(id);
            AccountPOJO foundAccount = accountdao.getAccount(accountpojo);
            salt = foundAccount.getAccountPassword().substring(32);
            hashedPassword = passwordhasher.hasher(password + salt);
            totalPassword = hashedPassword + salt;
            accountpojo.setAccountPassword(totalPassword);

            if (accountpojo.getAccountPassword().equals(foundAccount.getAccountPassword())) {
                accountdao.deleteAccount(accountpojo);
                LOGGER.info("removeAccount end");
                return true;
            } else {
                LOGGER.info("removeAccount end");
                return false;
            }
        } catch (Exception E) {
            System.out.println("Has to be an existing Account.");
            return false;
        }
    }

    public String updateAccount(int id, String name, String password, int status) {
        LOGGER.info("updateAccount start");
        try {
            accountpojo.setAccountID(id);
            accountpojo.setAccountName(name);
            accountpojo.setAccountPassword(password);
            accountpojo.setAccountStatus(status);
            accountdao.updateAccount(accountpojo);
            LOGGER.info("updateAccount end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public void editAccountName(int id, String name) {
        LOGGER.info("editAccountName start");
        try {
            accountpojo.setAccountID(id);
            AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
            accountpojo2.setAccountName(name);
            accountdao.updateAccount(accountpojo2);
            LOGGER.info("editAccountName end");
            System.out.println("Account has been updated.");
        } catch (Exception E) {
            System.out.println("Account not found.");
        }
    }

    public String editAccountPassword(int id, String password) {
        LOGGER.info("editAccountPassword start");
        try {
            accountpojo.setAccountID(id);
            AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
            saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
            accountpojo2.setAccountPassword(saltedHashedPassword);
            accountdao.updateAccount(accountpojo2);
            LOGGER.info("editAccountPassword end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public String editAccountStatus(int id, int status) {
        LOGGER.info("editAccountStatus start");
        try {
            accountpojo.setAccountID(id);
            AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
            accountpojo2.setAccountStatus(status);
            accountdao.updateAccount(accountpojo2);
            LOGGER.info("editAccountStatus end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public AccountPOJO findAccount(int findId) {
        LOGGER.info("findAccount start");
        accountpojo.setAccountID(findId);
        AccountPOJO returnedAccount = accountdao.getAccount(accountpojo);
        LOGGER.info("findAccount end");
        return returnedAccount;

    }

    public List<AccountPOJO> findAccountWithName(String name) {
        LOGGER.info("findAccountWithName start");
        accountpojo.setAccountName(name);
        List<AccountPOJO> returnedAccounts = accountdao.getAccountWithName(accountpojo);
        LOGGER.info("findAccountWithName end");
        return returnedAccounts;
    }

    public List<AccountPOJO> getAllAccounts() {
        LOGGER.info("GetAllAccounts start");
        List<AccountPOJO> returnedAccounts = accountdao.getAllAccount();
        LOGGER.info("GetAllAccounts end");
        return returnedAccounts;
    }

    public List<AccountPOJO> getAccountsWithClients() {
        LOGGER.info("GetAllAccounts start");
        List<AccountPOJO> returnedAccounts = accountdao.getAccountsWithClients();
        LOGGER.info("GetAllAccounts end");
        return returnedAccounts;
    }
}
