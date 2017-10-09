/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AccountDAO;
import Helper.PasswordHasher;
import Helper.TokenCreator;
import Interface.AccountDAOInterface;
import POJO.AccountPOJO;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Gerben
 */
public class AccountController {

    private Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private Scanner input = new Scanner(System.in);
    private AccountDAOInterface accountdao;
    private AccountPOJO accountpojo;
    private PasswordHasher passwordhasher;
    private String saltedHashedPassword;
    private String salt;
    private String hashedPassword;
    private String totalPassword;
    private TokenCreator token;

    public AccountController() {
        accountdao = new AccountDAO();
        accountpojo = new AccountPOJO();
    }

    //Voor test
    public AccountController(AccountDAOInterface accountdao) {
        this.accountdao = accountdao;
        accountpojo = new AccountPOJO();
        passwordhasher = new PasswordHasher();
        token = new TokenCreator();
    }

    public boolean login(int id, String password) {
        LOGGER.info("login start");
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
    }

    public int newAccount(String name, String password, int status) {
        LOGGER.info("newAccount start");
        accountpojo.setAccountName(name);
        saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
        accountpojo.setAccountPassword(saltedHashedPassword);
        accountpojo.setAccountStatus(status);
        LOGGER.info("newAccount end");
        return accountdao.addAccount(accountpojo);
    }

    public boolean removeAccount(int id, String password) {
        LOGGER.info("removeAccount start");
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
    }

    public String updateAccount(int id, String name, String password, int status) {
        LOGGER.info("updateAccount start");

        accountpojo.setAccountID(id);
        accountpojo.setAccountName(name);
        accountpojo.setAccountPassword(password);
        accountpojo.setAccountStatus(status);
        accountdao.updateAccount(accountpojo);
        LOGGER.info("updateAccount end");
        return "Account has been updated.";
    }

    public String editAccountName(int id, String name) {
        LOGGER.info("editAccountName start");

        accountpojo.setAccountID(id);
        AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
        accountpojo2.setAccountName(name);
        accountdao.updateAccount(accountpojo2);
        LOGGER.info("editAccountName end");
        return "Account has been updated.";
    }

    public String editAccountPassword(int id, String password) {
        LOGGER.info("editAccountPassword start");

        accountpojo.setAccountID(id);
        AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
        saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
        accountpojo2.setAccountPassword(saltedHashedPassword);
        accountdao.updateAccount(accountpojo2);
        LOGGER.info("editAccountPassword end");
        return "Account has been updated.";
    }

    public String editAccountStatus(int id, int status) {
        LOGGER.info("editAccountStatus start");

        accountpojo.setAccountID(id);
        AccountPOJO accountpojo2 = accountdao.getAccount(accountpojo);
        accountpojo2.setAccountStatus(status);
        accountdao.updateAccount(accountpojo2);
        LOGGER.info("editAccountStatus end");
        return "Account has been updated.";
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
}
