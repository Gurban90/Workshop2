/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helper.PasswordHasher;
import Helper.TokenCreator;
import HibernateDao.HibernateAccountDAO;
import Helper.HibernateDaoFactory;
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
        try {
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
        } catch (Exception E) {
            System.out.println("No account found.");
            return false;
        } finally {
            hibAccountDAO.finalize();
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
        hibAccountDAO.finalize();
        return accountpojo.getAccountID();
    }

    public boolean removeAccount(int id, String password) {
        LOGGER.info("removeAccount start");
        try {
            AccountPOJO foundAccount = hibAccountDAO.findById(AccountPOJO.class, id);
            salt = foundAccount.getAccountPassword().substring(32);
            hashedPassword = passwordhasher.hasher(password + salt);
            totalPassword = hashedPassword + salt;
            accountpojo.setAccountPassword(totalPassword);

            if (accountpojo.getAccountPassword().equals(foundAccount.getAccountPassword())) {
                hibAccountDAO.delete(AccountPOJO.class, id);
                hibAccountDAO.finalize();
                LOGGER.info("removeAccount end");
                return true;
            } else {
                LOGGER.info("removeAccount end");
                hibAccountDAO.finalize();
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
            hibAccountDAO.update(accountpojo);
            hibAccountDAO.finalize();
            LOGGER.info("updateAccount end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public void editAccountName(int id, String name) {
        LOGGER.info("editAccountName start");
        try {
            AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
            accountpojo2.setAccountName(name);
            hibAccountDAO.update(accountpojo2);
            hibAccountDAO.finalize();
            LOGGER.info("editAccountName end");
            System.out.println("Account has been updated.");
        } catch (Exception E) {
            System.out.println("Account not found.");
        }
    }

    public String editAccountPassword(int id, String password) {
        LOGGER.info("editAccountPassword start");
        try {
            AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
            saltedHashedPassword = passwordhasher.makeSaltedPasswordHash(password);
            accountpojo2.setAccountPassword(saltedHashedPassword);
            hibAccountDAO.update(accountpojo2);
            hibAccountDAO.finalize();
            LOGGER.info("editAccountPassword end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public String editAccountStatus(int id, int status) {
        LOGGER.info("editAccountStatus start");
        try {
            AccountPOJO accountpojo2 = hibAccountDAO.findById(AccountPOJO.class, id);
            accountpojo2.setAccountStatus(status);
            hibAccountDAO.update(accountpojo2);
            hibAccountDAO.finalize();
            LOGGER.info("editAccountStatus end");
            return "Account has been updated.";
        } catch (Exception E) {
            return "Account not found.";
        }
    }

    public AccountPOJO findAccount(int findId) {
        LOGGER.info("findAccount start");
        AccountPOJO returnedAccount = hibAccountDAO.findById(AccountPOJO.class, findId);
        hibAccountDAO.finalize();
        LOGGER.info("findAccount end");
        return returnedAccount;

    }

    public List<AccountPOJO> findAccountWithName(String name) {
        LOGGER.info("findAccountWithName start");
        List<AccountPOJO> returnedAccounts = hibAccountDAO.getAccountWithName(name);
        hibAccountDAO.finalize();
        LOGGER.info("findAccountWithName end");
        return returnedAccounts;
    }

    public List<AccountPOJO> getAllAccounts() {
        LOGGER.info("GetAllAccounts start");
        List<AccountPOJO> returnedAccounts = hibAccountDAO.getAll();
        hibAccountDAO.finalize();
        LOGGER.info("GetAllAccounts end");
        return returnedAccounts;

    }
}
