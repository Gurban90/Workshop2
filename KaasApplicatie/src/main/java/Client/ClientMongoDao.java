package Client;

import DatabaseConnector.MongoConnector;
import Client.ClientDAOInterface;
import Order.OrderMongoDAO;
import Order.OrderPOJO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;

public class ClientMongoDao implements ClientDAOInterface {

    private List<ClientPOJO> client;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    private Document doc;

    private MongoConnector mongoConnector;
    private Logger logger = Logger.getLogger(ClientMongoDao.class.getName());

    public ClientMongoDao() {
        mongoConnector = new MongoConnector();

    }

    public ClientPOJO convertDocumentToClient(Document doc) {
        ClientPOJO returnClient = new ClientPOJO();
        try {
            returnClient = new ClientPOJO(doc.getInteger("id"), doc.getString("firstname"), doc.getString("lastname"), doc.getString("email"));
        } catch (NullPointerException e) {
            System.out.println("Client not found.");

        }
        return returnClient;
    }

    private Document convertClientToDocument(ClientPOJO client) {
        Document document = new Document();
        document.append("id", client.getClientID());
        document.append("firstname", client.getFirstName());
        document.append("lastname", client.getLastName());
        document.append("email", client.getEMail());
        return document;
    }

    private Integer getNextId() {
        int id = 0;
        collection = mongoConnector.makeConnection().getCollection("client");

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
    public Integer addClient(ClientPOJO client) {
        logger.info("addclient Start");
        client.setClientID(getNextId());
        collection = mongoConnector.makeConnection().getCollection("client");
        collection.insertOne(convertClientToDocument(client));
        mongoConnector.closeConnection();
        logger.info("addclient end");
        return client.getClientID();

    }

    @Override
    public List<ClientPOJO> getAllClient() {
        logger.info("getAllclient Start");
        client = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("client");
        cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            client.add(convertDocumentToClient(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllclient end");
        return client;
    }

    @Override
    public ClientPOJO getClient(ClientPOJO client) {
        logger.info("getClient Start");
        collection = mongoConnector.makeConnection().getCollection("client");
        doc = collection.find(eq("id", client.getClientID())).first();
        mongoConnector.closeConnection();
        logger.info("getClient end");
        return convertDocumentToClient(doc);
    }

    @Override
    public List<ClientPOJO> getClientWithFirstName(String firstname) {
        logger.info("getclientfirstname Start");
        client = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("client");
        cursor = collection.find(eq("firstname", firstname)).iterator();
        mongoConnector.closeConnection();
        while (cursor.hasNext()) {
            doc = cursor.next();
            client.add(convertDocumentToClient(doc));
            logger.info("getclientfirstname end");

        }
        return client;
    }

    @Override
    public List<ClientPOJO> getClientWithLastName(String lastname) {
        logger.info("getclientlastname Start");
        client = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("client");
        cursor = collection.find(eq("lastname", lastname)).iterator();
        mongoConnector.closeConnection();
        while (cursor.hasNext()) {
            doc = cursor.next();
            client.add(convertDocumentToClient(doc));
            logger.info("getclientlastname end");

        }
        return client;
    }

    @Override
    public List<ClientPOJO> getClientWithEmail(String email) {
        logger.info("getclientemail Start");
        client = new ArrayList<>();
        collection = mongoConnector.makeConnection().getCollection("client");
        cursor = collection.find(eq("email", email)).iterator();
        mongoConnector.closeConnection();
        while (cursor.hasNext()) {
            doc = cursor.next();
            client.add(convertDocumentToClient(doc));
            logger.info("getclientemail end");
            return client;
        }
        return client;
    }

    @Override
    public void updateClient(ClientPOJO client) {
        logger.info("updateclient Start");
        collection = mongoConnector.makeConnection().getCollection("client");
        collection.findOneAndReplace(eq("id", client.getClientID()), convertClientToDocument(client));
        mongoConnector.closeConnection();
        logger.info("updateclient end");
    }

    @Override
    public void deleteClient(ClientPOJO client) {
        logger.info("deleteClient Start");
        OrderMongoDAO orderdao = new OrderMongoDAO();
        try {
            MongoCollection<Document> collection2 = mongoConnector.makeConnection().getCollection("order");
            Document doc2 = collection2.find(eq("clientid", client.getClientID())).first();
            OrderPOJO thisOrder = orderdao.convertDocumentToOrder(doc2);
            if (thisOrder.getClientID() == 0) {
                MongoCollection<Document> collection3 = mongoConnector.makeConnection().getCollection("client");
                collection3.findOneAndDelete(eq("id", client.getClientID()));
            } else {
                System.out.println("Cheese is currently in use, delete not possible.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoConnector.closeConnection();
        logger.info("deleteClient End");
    }
    
      @Override
    public void finalize(){};
}
