/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helper.PasswordHasher;
import Helper.TokenCreator;
import HibernateDao.HibernateAccountDAO;
import HibernateDao.HibernateDaoFactory;
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
    private HibernateAccountDAO hibAccountDAO;

    //Hibernate
    public AccountController() {
        hibAccountDAO = (HibernateAccountDAO) HibernateDaoFactory.getInstance().getDao("account");
        accountpojo = new AccountPOJO();
        passwordhasher = new PasswordHasher();
        token = new TokenCreator();
    }

    //Zonder Hibernate
    public AccountController(AccountDAOInterface accountdao) {
        this.accountdao = accountdao;
        accountpojo = new AccountPOJO();
        passwordhasher = new PasswordHasher();
        token = new TokenCreator();
    }

    public boolean login(int id, String password) {
        LOGGER.info("login start");
        try{
            AccountPOJO foundAccount = hibAccountDAO.findById(AccountPOJO.class, id);
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
        catch(Exception E){
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
        hibAccountDAO.create(accountpojo);
        return accountpojo.getAccountID();
    }

    public boolean removeAccount(int id, String password) {
        LOGGER.info("removeAccount start");
        AccountPOJO foundAccount = hibAccountDAO.findById(AccountPOJO.class, id);
        salt = foundAccount.getAccountPassword().substring(32);
        hashedPassword = passwordhasher.hasher(password + salt);
        totalPassword = hashedPassword + salt;
        accountpojo.setAccountPassword(totalPassword);

        if (accountpojo.getAccountPassword().equals(foundAccount.getAccountPassword())) {
            hibAccountDAO.delete(AccountPOJO.class, id);
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
        hibAccountDAO.update(accountpojo);
        LOGGER.info("updateAccount end");
        return "Account has been updated.";
    }

    public String editAccountName(int id, String name) {
        LOGGER.info("editAccountName start");
        AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
        accountpojo2.setAccountName(name);
        hibAccountDAO.update(accountpojo2);
        LOGGER.info("editAccountName end");
        return "Account has been updated.";
    }

    public String editAccountPassword(int id, String password) {
        LOGGER.info("editAccountPassword start");
        AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
        saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
        accountpojo2.setAccountPassword(saltedHashedPassword);
        hibAccountDAO.update(accountpojo2);
        LOGGER.info("editAccountPassword end");
        return "Account has been updated.";
    }

    public String editAccountStatus(int id, int status) {
        LOGGER.info("editAccountStatus start");
        AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
        accountpojo2.setAccountStatus(status);
        hibAccountDAO.update(accountpojo2);
        LOGGER.info("editAccountStatus end");
        return "Account has been updated.";
    }

    public AccountPOJO findAccount(int findId) {
        LOGGER.info("findAccount start");
        AccountPOJO returnedAccount = hibAccountDAO.findById(AccountPOJO.class, findId);
        LOGGER.info("findAccount end");
        return returnedAccount;

    }

    public List<AccountPOJO> findAccountWithName(String name) {
        LOGGER.info("findAccountWithName start");
        List<AccountPOJO> returnedAccounts = hibAccountDAO.getAccountWithName(name);
        LOGGER.info("findAccountWithName end");
        return returnedAccounts;
    }

    public List<AccountPOJO> getAllAccounts() {
        LOGGER.info("GetAllAccounts start");
        List<AccountPOJO> returnedAccounts = hibAccountDAO.getAll();
        LOGGER.info("GetAllAccounts end");
        return returnedAccounts;

    }
}
