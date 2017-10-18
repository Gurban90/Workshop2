/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDao;

import DatabaseConnector.MongoConnector;
import Interface.AddressDAOInterface;
import POJO.AddressPOJO;
import POJO.AddressTypePOJO;
import POJO.ClientPOJO;
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
public class AddressMongoDao implements AddressDAOInterface {

    private MongoConnector mongoConnector;
    Logger logger = Logger.getLogger(AddressMongoDao.class.getName());

    public AddressMongoDao() {
        mongoConnector = new MongoConnector();
    }

    private AddressPOJO convertDocumentToAddress(Document doc) {
        AddressPOJO returnAddress = new AddressPOJO();
        try {
            returnAddress = new AddressPOJO(doc.getInteger("AddressId"), doc.getString("StreetName"), doc.getInteger("HouseNumber"),
                    doc.getString("HouseNubmerAddition"), doc.getString("PostalCode"), doc.getString("City"), doc.getInteger("ClientID"),
                    doc.getInteger("AddressTypeID"));
        } catch (NullPointerException e) {
            System.out.println("Address not found.");
        }
        return returnAddress;
    }

    private AddressTypePOJO convertDocumentToAddressType(Document doc) {
        AddressTypePOJO returnAddressType = new AddressTypePOJO();
        try {
            returnAddressType = new AddressTypePOJO(doc.getInteger("AddressTypeID"), doc.getString("AddressType"));
        } catch (NullPointerException e) {
            System.out.println("Must be an existing AddressType.");
        }
        return returnAddressType;
    }

    private Document convertAddressToDocument(AddressPOJO address) {
        Document document = new Document();
        document.append("AddressId", address.getAddressID());
        document.append("StreetName", address.getStreetName());
        document.append("HouseNumber", address.getHouseNumber());
        document.append("HouseNubmerAddition", address.getHouseNumberAddition());
        document.append("PostalCode", address.getPostalCode());
        document.append("City", address.getCity());
        document.append("ClientID", address.getClientIdentifier());
        document.append("AddressTypeID", address.getAddressTypeIdentifier());
        return document;
    }

    private Document convertAddressTypeToDocument(AddressTypePOJO addresstype) {
        Document document = new Document();
        document.append("AddressTypeID", addresstype.getAddressTypeID());
        document.append("AddressType", addresstype.getAddressType());

        return document;
    }

    private Integer getNextId() {
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        if (collection.count() > 0) {
            Document highestId = collection.find().sort(orderBy(descending("AddressId"))).first();
            id = highestId.getInteger("AddressId") + 1;
        } else {
            id = 1;
        }
        mongoConnector.closeConnection();
        return id;
    }

    private Integer getNextAddressTypeId() {
        int id = 0;
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("addressType");
        if (collection.count() > 0) {
            Document highestId = collection.find().sort(orderBy(descending("AddressTypeID"))).first();
            id = highestId.getInteger("AddressTypeID") + 1;
        } else {
            id = 1;
        }
        mongoConnector.closeConnection();
        return id;
    }

    @Override
    public Integer addAddress(AddressPOJO adress) {
        logger.info("addAddress Start");
        ClientMongoDao clientMongo = new ClientMongoDao();
        try {
            MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("client");
            Document doc = collection.find(eq("id", adress.getClientIdentifier())).first();
            ClientPOJO thisClient = clientMongo.convertDocumentToClient(doc);
            if (thisClient.getClientID() == adress.getClientIdentifier()) {
                try {
                    MongoCollection<Document> collection2 = mongoConnector.makeConnection().getCollection("addressType");
                    Document doc2 = collection2.find(eq("AddressTypeID", adress.getAddressTypeIdentifier())).first();
                    AddressTypePOJO thisAddressType = convertDocumentToAddressType(doc2);
                    if (thisAddressType.getAddressTypeID() == adress.getAddressTypeIdentifier()) {
                        adress.setAddressID(getNextId());
                        MongoCollection<Document> collection3 = mongoConnector.makeConnection().getCollection("address");
                        collection3.insertOne(convertAddressToDocument(adress));
                    } else {
                        System.out.println("AddressType must first be created.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Client must first be created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoConnector.closeConnection();
        logger.info("addAddress end");
        return adress.getAddressID();
    }

    @Override
    public List<AddressPOJO> getAllAddress() {
        logger.info("getAllAddress Start");
        List<AddressPOJO> addresses = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            addresses.add(convertDocumentToAddress(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllAddress end");
        return addresses;
    }

    @Override
    public AddressPOJO getAddress(AddressPOJO address) {
        logger.info("getAddress Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        Document doc = collection.find(eq("AddressId", address.getAddressID())).first();
        mongoConnector.closeConnection();
        logger.info("getAddress end");
        return convertDocumentToAddress(doc);
    }

    @Override
    public void updateAddress(AddressPOJO address) {
        logger.info("updateAddress Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        collection.findOneAndReplace(eq("AddressId", address.getAddressID()), convertAddressToDocument(address));
        mongoConnector.closeConnection();
        logger.info("updateAddress Start");
    }

    @Override
    public void deleteAddress(AddressPOJO address) {
        logger.info("deleteAddress Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        collection.findOneAndDelete(eq("AddressId", address.getAddressID()));
        mongoConnector.closeConnection();
        logger.info("deleteAddress Start");
    }

    @Override
    public Integer addAddressType(AddressTypePOJO addressType
    ) {
        logger.info("addAddressType Start");
        addressType.setAddressTypeID(getNextAddressTypeId());
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("addressType");
        collection.insertOne(convertAddressTypeToDocument(addressType));
        mongoConnector.closeConnection();
        logger.info("addAddressType end");
        return addressType.getAddressTypeID();
    }

    @Override
    public List<AddressPOJO> getAddressWithClient(ClientPOJO client) {
        logger.info("getAddressWithClient Start");
        List<AddressPOJO> addresses = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
        MongoCursor<Document> cursor = collection.find(eq("ClientID", client.getClientID())).iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            addresses.add(convertDocumentToAddress(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAddressWithClient end");
        return addresses;
    }

    @Override
    public List<AddressTypePOJO> getAllAddressType() {
        logger.info("getAllAddressType Start");
        List<AddressTypePOJO> addresstypes = new ArrayList<>();
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("addressType");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            addresstypes.add(convertDocumentToAddressType(doc));
        }
        mongoConnector.closeConnection();
        logger.info("getAllAddressType end");
        return addresstypes;
    }

    @Override
    public AddressTypePOJO getAddressType(AddressTypePOJO addressType
    ) {
        logger.info("getAddressType Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("addressType");
        Document doc = collection.find(eq("AddressTypeID", addressType.getAddressTypeID())).first();
        mongoConnector.closeConnection();
        logger.info("getAddressType end");
        return convertDocumentToAddressType(doc);
    }

    @Override
    public void updateAddressType(AddressTypePOJO addressType
    ) {
        logger.info("updateAddressType Start");
        MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("addressType");
        collection.findOneAndReplace(eq("AddressTypeID", addressType.getAddressTypeID()), convertAddressTypeToDocument(addressType));
        mongoConnector.closeConnection();
        logger.info("updateAddressType End");
    }

    @Override
    public void deleteAddressType(AddressTypePOJO addressType
    ) {
        logger.info("deleteAddressType Start");
        try {
            MongoCollection<Document> collection = mongoConnector.makeConnection().getCollection("address");
            Document doc = collection.find(eq("AddressTypeID", addressType.getAddressTypeID())).first();
            AddressPOJO thisAddress = convertDocumentToAddress(doc);
            if (thisAddress.getAddressTypeIdentifier() == 0) {
                MongoCollection<Document> collection2 = mongoConnector.makeConnection().getCollection("addressType");
                collection2.findOneAndDelete(eq("AddressTypeID", addressType.getAddressTypeID()));
            } else {
                System.out.println("AddressType is currently in use, delete not possible.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mongoConnector.closeConnection();
        logger.info("deleteAddressType End");
    }

      @Override
    public void finalize(){};
}
