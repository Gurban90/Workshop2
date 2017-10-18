
package Dao;

import Helper.ConnectionFactory;
import Interface.AccountDAOInterface;
import POJO.AccountPOJO;
import POJO.ClientPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountDAO implements AccountDAOInterface {

    private Logger LOGGER = Logger.getLogger(AccountDAOInterface.class.getName());
    private Connection connection;
    private String query;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private ConnectionFactory connectionfactory = new ConnectionFactory();

    @Override
    public Integer addAccount(AccountPOJO account) {
        LOGGER.info("Start addAccount log");
        Integer newID = 0;

        query = "INSERT INTO Account (AccountName, AccountPassword, AccountStatus) VALUES (?,?,?);";

        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getAccountName());
            statement.setString(2, account.getAccountPassword());
            statement.setInt(3, account.getAccountStatus());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newID = resultSet.getInt(1);
                    account.setAccountID(newID);
                } else {
                    throw new SQLException("Creating Account failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("addAccount end");
        return newID;
    }

    @Override
    public List<AccountPOJO> getAllAccount() {
        LOGGER.info("getAllAccount Start");
        String query = "SELECT * FROM Account;";
        List<AccountPOJO> returnedAccounts = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AccountPOJO account = new AccountPOJO();
                account.setAccountID(resultSet.getInt(1));
                account.setAccountName(resultSet.getString(2));
                account.setAccountPassword(resultSet.getString(3));
                account.setAccountStatus(resultSet.getInt(4));
                returnedAccounts.add(account);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("getAllAccount end");
        return returnedAccounts;
    }

    @Override
    public AccountPOJO getAccount(AccountPOJO account) {
        LOGGER.info("getAccount Start");
        query = "SELECT * FROM Account WHERE AccountID=?";
        AccountPOJO foundaccount = new AccountPOJO();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, account.getAccountID());
            resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                foundaccount.setAccountID(resultSet.getInt(1));
                foundaccount.setAccountName(resultSet.getString(2));
                foundaccount.setAccountPassword(resultSet.getString(3));
                foundaccount.setAccountStatus(resultSet.getInt(4));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("getAccount end");
        return foundaccount;
    }

    @Override
    public List<AccountPOJO> getAccountWithName(AccountPOJO account) {
        LOGGER.info("getAccountWithName Start");
        query = "SELECT * FROM Account WHERE AccountName=?";
        List<AccountPOJO> returnedAccounts = new ArrayList<>();
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, account.getAccountName());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AccountPOJO accountpojo = new AccountPOJO();
                accountpojo.setAccountID(resultSet.getInt(1));
                accountpojo.setAccountName(resultSet.getString(2));
                accountpojo.setAccountPassword(resultSet.getString(3));
                accountpojo.setAccountStatus(resultSet.getInt(4));
                returnedAccounts.add(accountpojo);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("getAccountWithName end");
        return returnedAccounts;
    }

    @Override
    public void updateAccount(AccountPOJO account) {
        LOGGER.info("updateAccount Start");
        query = "UPDATE Account SET AccountName = ?, AccountPassword = ?, AccountStatus = ? WHERE AccountID=?";
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, account.getAccountName());
            statement.setString(2, account.getAccountPassword());
            statement.setInt(3, account.getAccountStatus());
            statement.setInt(4, account.getAccountID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("updateAccount end");
    }

    @Override
    public void deleteAccount(AccountPOJO account) {
        LOGGER.info("deleteAccount Start");
        query = "DELETE FROM Account WHERE AccountID = ?";
        try {
            connection = connectionfactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, account.getAccountID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("deleteAccount end");
    }
           
    @Override
    public void finalize(){};

    @Override
    public List<AccountPOJO> getAccountsWithClients() {
        throw new UnsupportedOperationException("Not necessary yet."); 
    }
}
