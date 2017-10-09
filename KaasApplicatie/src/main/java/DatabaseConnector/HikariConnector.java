/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author Gerben
 */
public class HikariConnector {

    private HikariDataSource dataSource;
    private String url;
    private String username;
    private String password;

    public HikariConnector() {
        DomXML xmlParser = new DomXML();
        url = xmlParser.getUrl();
        username = xmlParser.getUsername();
        password = xmlParser.getPassword();
    }

    private HikariDataSource createDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(3);
            config.setAutoCommit(true);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
    
    public Connection getConnection(){
        try {
            if(dataSource == null){
                return createDataSource().getConnection();
            }
            return dataSource.getConnection();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public void close(){
        if(dataSource != null){
            dataSource.close();
        }
        dataSource = null;
    }
    
}
