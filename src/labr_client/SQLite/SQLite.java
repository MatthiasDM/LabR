/* 
 * Copyright (C) 2016 De Mey Matthias
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
