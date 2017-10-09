/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDao;

import DatabaseConnector.MongoConnector;
import Helper.Converter;
import Interface.OrderDAOInterface;
import POJO.ClientPOJO;
import POJO.OrderPOJO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Jasper Thielen
 */
public class OrderMongoDAO implements OrderDAOInterface {

    private List<OrderPOJO> order;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    private Document doc;

    private ClientPOJO checkedClientID;

    private MongoConnector mongoConnector;
    private Logger logger = Logger.getLogger(ClientMongoDao.class.getName());

    public OrderMongoDAO() {
        mongoConnector = new MongoConnector();

    }

    public OrderPOJO convertDocumentToOrder(Document doc) {
        OrderPOJO returnOrder = new OrderPOJO();
        Converter converter = new Converter();
        try {
            returnOrder = new OrderPOJO(doc.getInteger("id"), converter.convertDate(doc.getString("orderdate")), new BigDecimal(doc.getString("totalprice")), converter.convertDate(doc.getString("processeddate")), doc.getInteger("clientid"));
        } catch (NullPointerException e) {
            System.out.println("Order not found.");
        }
        return returnOrder;
    }

    private Document convertOrderToDocument(OrderPOJO order) {
        doc = new Document();
        Converter converter = new Converter();

        doc.append("id", order.getOrderID());
        doc.append("orderdate", converter.convertLocalDateTime(order.getOrderDate()));
        doc.append("totalprice", order.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
        doc.append("processeddate", converter.convertLocalDateTime(order.getProcessedDate()));
        doc.append("clientid", order.getClientID());

        return doc;
    }

    private Integer getNextId() {
        int id = 0;
        collection = mongoConnector.makeConnection().getCollection("order");

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
    public Integer addOrder(OrderPOJO order) {
        logger.info("addOrderDetail Start");

        ClientMongoDao clientMongo = new ClientMongoDao();

        try {
            collection = mongoConnector.makeConnection().getCollection("client"); //clientid
            doc = collection.find(eq("id", order.getClientID())).first();
            checkedClientID = clientMongo.convertDocumentToClient(doc);
            if (checkedClientID.getClientID() == order.getClientID()) {
                order.setOrderID(getNextId());
                collection = mongoConnector.makeConnection().getCollection("order");
                collection.insertOne(convertOrderToDocument(order));
                logger.info("addOrderDetailt end");
            } else {
                System.out.println("no orderid or cheese id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoConnector.closeConnection();
        }
        return order.getOrderID();

    }

    @Override
    public List<OrderPOJO> getAllOrder() {
        logger.info("getAllorder Start");
        order = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("order");
        cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            order.add(convertDocumentToOrder(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllorderend");
        return order;
    }

    @Override
    public OrderPOJO getOrder(OrderPOJO order) {
        logger.info("getOrder Start");
        collection = mongoConnector.makeConnection().getCollection("order");
        doc = collection.find(eq("id", order.getOrderID())).first();
        mongoConnector.closeConnection();
        logger.info("getOrder end");
        return convertDocumentToOrder(doc);
    }

    @Override
    public List<OrderPOJO> getOrderWithClient(ClientPOJO client) {
        logger.info("getAddressWithClient Start");
        order = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("order");
        cursor = collection.find(eq("clientid", client.getClientID())).iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            order.add(convertDocumentToOrder(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAddressWithClient end");
        return order;
    }

    @Override
    public void updateOrder(OrderPOJO order) {
        logger.info("updateOrder Start");
        collection = mongoConnector.makeConnection().getCollection("order");
        collection.findOneAndReplace(eq("id", order.getOrderID()), convertOrderToDocument(order));
        mongoConnector.closeConnection();
        logger.info("updateOrder end");
    }

    @Override
    public void deleteOrder(OrderPOJO order) {
        logger.info("deleteOrder Start");
        collection = mongoConnector.makeConnection().getCollection("order");
        collection.findOneAndDelete(eq("id", order.getOrderID()));
        MongoCollection<Document> collection2 = mongoConnector.makeConnection().getCollection("orderdetail");
        collection2.deleteMany(eq("orderid", order.getOrderID()));
        mongoConnector.closeConnection();
        logger.info("deleteOrde End");
    }
}
