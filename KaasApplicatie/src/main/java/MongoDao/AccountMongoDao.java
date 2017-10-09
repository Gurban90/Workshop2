/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDao;

import DatabaseConnector.MongoConnector;
import Interface.AccountDAOInterface;
import POJO.AccountPOJO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Gerben
 */
public class AccountMongoDao implements AccountDAOInterface {

    private MongoConnector mongoConnector;
    Logger logger = Logger.getLogger(AccountMongoDao.class.getName());

    public AccountMongoDao() {
        mongoConnector = new MongoConnector();
    }

    private AccountPOJO convertDocumentToAccount(Document doc) {
        AccountPOJO returnAccount = new AccountPOJO();
        try {
            returnAccount = new AccountPOJO(doc.getInteger("AccountID"), doc.getString("Name"), doc.getString("Password"), doc.getInteger("Status"));
        } catch (NullPointerException e) {
            System.out.println("Account not found");
        }
        return returnAccount;
    }

    private Document convertAccountToDocument(AccountPOJO account) {
        Document document = new Document();
        document.append("AccountID", account.getAccountID());
        document.append("Name", account.getAccountName());
        document.append("Password", account.getAccountPassword());
        document.append("Status", account.getAccountStatus());
        return document;
    }

    private Integer getNextId() {
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        if (collection.count() > 0) {
            Document highestId = collection.find().sort(orderBy(descending("AccountID"))).first();
            id = highestId.getInteger("AccountID") + 1;
        } else {
            id = 1;
        }
        mongoConnector.closeConnection();
        return id;
    }

    @Override
    public Integer addAccount(AccountPOJO account) {
        logger.info("addAccount Start");
        account.setAccountID(getNextId());
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        collection.insertOne(convertAccountToDocument(account));
        mongoConnector.closeConnection();
        logger.info("addAccount end");
        return account.getAccountID();

    }

    @Override
    public List<AccountPOJO> getAllAccount() {
        logger.info("getAllAccount Start");
        List<AccountPOJO> accounts = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            accounts.add(convertDocumentToAccount(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllAccount end");
        return accounts;
    }

    @Override
    public AccountPOJO getAccount(AccountPOJO account) {
        logger.info("getAccount Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        Document doc = collection.find(eq("AccountID", account.getAccountID())).first();
        mongoConnector.closeConnection();
        logger.info("getAccount end");
        return convertDocumentToAccount(doc);
    }

    @Override
    public List<AccountPOJO> getAccountWithName(AccountPOJO account) {
        logger.info("getAccountWithName Start");
        List<AccountPOJO> accounts = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        MongoCursor<Document> cursor = collection.find(eq("Name", account.getAccountName())).iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            accounts.add(convertDocumentToAccount(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAccountWithName end");
        return accounts;
    }

    @Override
    public void updateAccount(AccountPOJO account) {
        logger.info("updateAccount Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        collection.findOneAndReplace(eq("AccountID", account.getAccountID()), convertAccountToDocument(account));
        mongoConnector.closeConnection();
        logger.info("updateAccount end");
    }

    @Override
    public void deleteAccount(AccountPOJO account) {
        logger.info("deleteAccount Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("account");
        collection.findOneAndDelete(eq("AccountID", account.getAccountID()));
        mongoConnector.closeConnection();
        logger.info("deleteAccount End");
    }

}
