/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnector;

/**
 *
 * @author Gerben
 */
import java.sql.*;

public class Connector {
    private Connection connection;
    private String url;  
    private String driver;  
    private String user; 
    private String pass;
    private static final Connector Jdbc = new Connector();
       
    private Connector(){
		DomXML parser = new DomXML();
		this.driver = parser.getDriverName();
		this.url = parser.getUrl();
		this.user = parser.getUsername();
		this.pass = parser.getPassword();
                
    }
    private void makeConnection(){
        
        try {
            Class.forName(driver);
        }
       
        catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class");
            System.exit(1);
        }            
        
        try{
            connection = DriverManager.getConnection(url , user , pass);
        }
        
        catch (SQLException ex){
            System.out.println("Error: check url, user and password");
            System.exit(1);
        }
    }
    public static Connection getConnection() {
        try {
            if (Jdbc.connection == null) {
                Jdbc.makeConnection();
            } else if (Jdbc.connection.isClosed()) {
                Jdbc.makeConnection();
            }
        } catch (SQLException e) {
            System.out.println(e);
            
        }
        return Jdbc.connection;
    }
}


        
            
        
                
    
           
  

