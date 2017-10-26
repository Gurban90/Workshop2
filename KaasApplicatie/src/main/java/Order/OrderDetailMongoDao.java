/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import Order.OrderMongoDAO;
import Client.ClientMongoDao;
import Cheese.CheeseMongoDao;
import DatabaseConnector.MongoConnector;
import Order.OrderDetailDAOInterface;
import Cheese.CheesePOJO;
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
 * @author Jasper Thielen
 */
public class OrderDetailMongoDao implements OrderDetailDAOInterface {

    private List<OrderDetailPOJO> orderDetail;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    private Document doc;

    private OrderPOJO checkedOrderID;
    private CheesePOJO checkedCheeseID;

    private MongoConnector mongoConnector;
    private Logger logger = Logger.getLogger(ClientMongoDao.class.getName());

    public OrderDetailMongoDao() {
        mongoConnector = new MongoConnector();
    }

    public OrderDetailPOJO convertDocumentToOrderDetail(Document doc) {
        OrderDetailPOJO returnOrderDetail = new OrderDetailPOJO();
        try {
            returnOrderDetail = new OrderDetailPOJO(doc.getInteger("id"), doc.getInteger("quantity"), doc.getInteger("cheeseid"), doc.getInteger("orderid"));
        } catch (NullPointerException e) {
            System.out.println("OrderDetail not found.");
        }
        return returnOrderDetail;
    }

    private Document convertOrderDetailToDocument(OrderDetailPOJO orderdetail) {
        doc = new Document();
        doc.append("id", orderdetail.getOrderDetailID());
        doc.append("quantity", orderdetail.getQuantity());
        doc.append("cheeseid", orderdetail.getCheeseID());
        doc.append("orderid", orderdetail.getOrderID());
        return doc;
    }

    private Integer getNextId() {
        int id = 0;
        collection = mongoConnector.makeConnection().getCollection("orderdetail");

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
    public Integer addOrderDetail(OrderDetailPOJO orderDetail) {

        logger.info("addOrderDetail Start");
        CheeseMongoDao cheeseMongo = new CheeseMongoDao();
        OrderMongoDAO orderMongo = new OrderMongoDAO();

        try {
            this.collection = mongoConnector.makeConnection().getCollection("cheese"); //cheeseid
            this.doc = collection.find(eq("id", orderDetail.getCheeseID())).first();
            checkedCheeseID = cheeseMongo.convertDocumentToCheese(doc);

            this.collection = mongoConnector.makeConnection().getCollection("order"); //orderid
            this.doc = collection.find(eq("id", orderDetail.getOrderID())).first();
            checkedOrderID = orderMongo.convertDocumentToOrder(doc);
            if (checkedOrderID.getOrderID() == orderDetail.getOrderID() && checkedCheeseID.getCheeseID() == orderDetail.getCheeseID()) {

                orderDetail.setOrderDetailID(getNextId());
                collection = mongoConnector.makeConnection().getCollection("orderdetail");
                collection.insertOne(convertOrderDetailToDocument(orderDetail));
                logger.info("addOrderDetailt end");
                return orderDetail.getOrderDetailID();
            } else {
                System.out.println("no orderid or cheese id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoConnector.closeConnection();
        }
        return null;
    }

    @Override
    public List<OrderDetailPOJO> getAllOrderDetail() {
        logger.info("getAllorderdetail Start");
        orderDetail = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("orderdetail");
        cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            orderDetail.add(convertDocumentToOrderDetail(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllorderdetailend");
        return orderDetail;

    }

    @Override
    public List<OrderDetailPOJO> getOrderDetail(OrderDetailPOJO orderdetail) {
        logger.info("getorderdetail Start");
        orderDetail = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("orderdetail");
        cursor = (MongoCursor<Document>) collection.find(eq("orderid", orderdetail.getOrderID())).iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            orderDetail.add(convertDocumentToOrderDetail(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getallorderdetailend");
        return orderDetail;
    }

    @Override
    public OrderDetailPOJO getOrderDetailWithID(OrderDetailPOJO orderdetail) {
        logger.info("getOrderDetailWithID Start");
        collection = mongoConnector.makeConnection().getCollection("orderdetail");
        doc = collection.find(eq("id", orderdetail.getOrderDetailID())).first();
        mongoConnector.closeConnection();
        logger.info("getOrderDetailWithID end");
        return convertDocumentToOrderDetail(doc);
    }

    @Override
    public void deleteOrderDetail(OrderDetailPOJO orderDetail) {
        logger.info("deleteOrderDetail Start");
        collection = mongoConnector.makeConnection().getCollection("orderdetail");
        collection.findOneAndDelete(eq("id", orderDetail.getOrderDetailID()));
        mongoConnector.closeConnection();
        logger.info("deleteOrderDetail End");
    }

    @Override
    public void updateOrderDetail(OrderDetailPOJO orderDetail) {
        logger.info("updateOrderDetail Start");
        CheeseMongoDao cheeseMongo = new CheeseMongoDao();
        try {
            this.collection = mongoConnector.makeConnection().getCollection("cheese"); //cheeseid
            this.doc = collection.find(eq("id", orderDetail.getCheeseID())).first();
            checkedCheeseID = cheeseMongo.convertDocumentToCheese(doc);
            if (checkedCheeseID.getCheeseID() == orderDetail.getCheeseID()) {
                collection = mongoConnector.makeConnection().getCollection("orderdetail");
                collection.findOneAndReplace(eq("id", orderDetail.getOrderDetailID()), convertOrderDetailToDocument(orderDetail));
                mongoConnector.closeConnection();
                logger.info("updateOrderDetail end");
            } else {
                System.out.println("Cheese not in database");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoConnector.closeConnection();
        }
    }
    
      @Override
    public void finalize(){};

    @Override
    public List<OrderDetailPOJO> getWithCheese(int CheeseID) {
        throw new UnsupportedOperationException("Not needed in this DAO."); 
    }
}
