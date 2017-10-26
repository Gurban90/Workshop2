/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cheese;

import DatabaseConnector.MongoConnector;
import Cheese.CheeseDAOInterface;
import Order.OrderDetailMongoDao;
import Order.OrderDetailPOJO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Gerben
 */
public class CheeseMongoDao implements CheeseDAOInterface {

    private MongoConnector mongoConnector;
    Logger logger = Logger.getLogger(CheeseMongoDao.class.getName());

    public CheeseMongoDao() {
        mongoConnector = new MongoConnector();
    }

    public CheesePOJO convertDocumentToCheese(Document doc) {
        CheesePOJO returnCheese = new CheesePOJO();
        try {
            returnCheese = new CheesePOJO(doc.getInteger("id"), doc.getString("name"), new BigDecimal(doc.getString("price")), doc.getInteger("stock"));
        } catch (NullPointerException e) {
            System.out.println("Must be an existing Cheese");
        }
        return returnCheese;
    }

    private Document convertCheeseToDocument(CheesePOJO cheese) {
        Document document = new Document();
        document.append("id", cheese.getCheeseID());
        document.append("name", cheese.getCheeseName());
        document.append("price", cheese.getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
        document.append("stock", cheese.getStock());
        return document;
    }

    private Integer getNextId() {
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        if (collection.count() > 0) {
            Document highestId = collection.find().sort(orderBy(descending("id"))).first();
            id = highestId.getInteger("id") + 1;
        } else {
            id = 1;
        }
        mongoConnector.closeConnection();
        return id;
    }

    @Override
    public Integer addCheese(CheesePOJO cheese) {
        logger.info("addCheese Start");
        cheese.setCheeseID(getNextId());
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        collection.insertOne(convertCheeseToDocument(cheese));
        mongoConnector.closeConnection();
        logger.info("addCheese end");
        return cheese.getCheeseID();

    }

    @Override
    public List<CheesePOJO> getAllCheese() {
        logger.info("getAllCheese Start");
        List<CheesePOJO> cheeses = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            cheeses.add(convertDocumentToCheese(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllCheese end");
        return cheeses;
    }

    @Override
    public CheesePOJO getCheese(CheesePOJO cheese) {
        logger.info("getCheese Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        Document doc = collection.find(eq("id", cheese.getCheeseID())).first();
        mongoConnector.closeConnection();
        logger.info("getCheese end");
        return convertDocumentToCheese(doc);
    }

    @Override
    public List<CheesePOJO> getCheeseWithName(CheesePOJO cheese) {
        logger.info("getCheeseWithName Start");
        List<CheesePOJO> cheeses = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        MongoCursor<Document> cursor = collection.find(eq("name", cheese.getCheeseName())).iterator();
         while (cursor.hasNext()) {
            Document doc = cursor.next();
            cheeses.add(convertDocumentToCheese(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllCheese end");
        return cheeses;
    }
    

    @Override
    public void updateCheese(CheesePOJO cheese) {
        logger.info("updateCheese Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("cheese");
        collection.findOneAndReplace(eq("id", cheese.getCheeseID()), convertCheeseToDocument(cheese));
        mongoConnector.closeConnection();
        logger.info("updateCheese end");
    }

    @Override
    public void deleteCheese(CheesePOJO cheese) {
        logger.info("deleteCheese Start");
        OrderDetailMongoDao orderdetaildao = new OrderDetailMongoDao();
        try {
            MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("orderdetail");
            Document doc = collection.find(eq("cheeseid", cheese.getCheeseID())).first();
            OrderDetailPOJO thisOrderDetail = orderdetaildao.convertDocumentToOrderDetail(doc);
            if (thisOrderDetail.getCheeseID() == 0) {
                MongoCollection<Document> collection2 = mongoConnector.makeConnection().getCollection("cheese");
                collection2.findOneAndDelete(eq("id", cheese.getCheeseID()));
            } else {
                System.out.println("Cheese is currently in use, delete not possible.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoConnector.closeConnection();
        logger.info("deleteCheese End");
    }

      @Override
    public void finalize(){};
}
