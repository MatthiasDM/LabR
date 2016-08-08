/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.SQLite;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.Public.PublicVars;
import org.sqlite.JDBC;
/**
 *
 * @author matmey
 */
public class SQLite {

    private String DBName = "LabR";
    private Connection c;

    public String getDBName() {
        return DBName;
    }

    public Connection getC() {
        if (c != null) {
            return c;
        } else {
            startConnection();
            return c;
        }
    }

    public void closeC() {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startConnection() {
        String path = PublicVars.getProperties().getProperty("database_location");
        try {
            Class.forName("org.sqlite.JDBC");
            //String classUrl = this.getClass().getResource("org.sqlite.JDBC").getPath();
       
            c = (DriverManager.getConnection("jdbc:sqlite:" + path)); //Start connection to database in a connection variable called 'c' 
        } catch (SQLException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

}
