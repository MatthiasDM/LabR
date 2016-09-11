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

import static java.lang.Class.forName;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static labr_client.Public.PublicVars.getProperties;
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
            getLogger(SQLite.class.getName()).log(SEVERE, null, ex);
        }
    }

    public void startConnection() {
        String path = getProperties().getProperty("database_location");
        try {
            forName("org.sqlite.JDBC");
            //String classUrl = this.getClass().getResource("org.sqlite.JDBC").getPath();
       
            c = (getConnection("jdbc:sqlite:" + path)); //Start connection to database in a connection variable called 'c' 
        } catch (SQLException | ClassNotFoundException ex) {
            getLogger(SQLite.class.getName()).log(SEVERE, null, ex);
            
        }
    } 

}
