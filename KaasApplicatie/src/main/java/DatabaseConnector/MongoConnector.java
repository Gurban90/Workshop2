/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnector;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


/**
 *
 * @author Gerben
 */
//OM UIT TE TESTEN
public class MongoConnector {

    private String url;
    private int port;
    private String port2;
    private String database;
    private MongoClient mongoClient;

    public MongoConnector() {
        DomXML parser = new DomXML("Mongodb");
        this.url = parser.getUrl();
        this.port2 = parser.getPort();
        this.database = parser.getDatabase();
        port = Integer.parseInt(port2);
    }

    public MongoDatabase makeConnection() {
        mongoClient = new MongoClient(url, port);
        MongoDatabase db = mongoClient.getDatabase(database);
        return db;

    }

    public void closeConnection() {
        mongoClient.close();
    }

}
