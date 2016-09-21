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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Arrays.copyOf;
import java.util.HashMap;
import java.util.List;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author matmey
 */
public class SQLiteQueryProcesser extends SQLite {

    public void parseUpdateStatement(String sql) {
        try {
            Connection c = getC();
            // set the preparedstatement parameters
//            ps.setString(1, description);
//            ps.setString(2, author);
//            ps.setInt(3, id);
//            ps.setInt(4, seqNum);
            // call executeUpdate to execute our sql update statement
            try ( //Statement stmt = c.createStatement();
                    PreparedStatement ps = c.prepareStatement(sql)) {
                // set the preparedstatement parameters
//            ps.setString(1, description);
//            ps.setString(2, author);
//            ps.setInt(3, id);
//            ps.setInt(4, seqNum);
                // call executeUpdate to execute our sql update statement
                ps.executeUpdate();
//            String sql = "CREATE TABLE COMPANY " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " AGE            INT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " SALARY         REAL)";
                //stmt.executeUpdate(sql);
            }
        
        } catch (SQLException ex) {
            getLogger(SQLiteQueryProcesser.class.getName()).log(SEVERE, null, ex);
        }
    }
//Select MNEMONIC from bepalingen inner join Aanvraagprofielen on bepalingen.ID = Aanvraagprofielen.BepalingID WHERE Aanvraagprofielen.ProfielID = 1

    public List<String[]> parseSelectStatement(String sql, String[] attributes) {
        try {
            Connection c = getC();
            List<String[]> lines;
            try (Statement stmt = c.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                lines = new ArrayList<>();
                ArrayList<String> values = new ArrayList<>();
                while (rs.next()) {
                    for (String attr : attributes) {
                        values.add(rs.getString(attr));
                    }   String[] stringArray = copyOf(values.toArray(), values.toArray().length, String[].class);
                    lines.add(stringArray);
                    values.removeAll(values);
                    values.clear();
                }
                rs.close();
            }
           
            return lines;
        } catch (SQLException ex) {
            getLogger(SQLiteQueryProcesser.class.getName()).log(SEVERE, null, ex);
            return null;
        }

    }

    public List<HashMap> parseSelectStatement2(String sql, String[] attributes) {
        try {
            Connection c = getC();
            List<HashMap> lines;
            try (Statement stmt = c.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                lines = new ArrayList<>();
                HashMap values = new HashMap();
                
                while (rs.next()) {                    
                    for (String attr : attributes) {
                        values.put(attr, rs.getString(attr));
                    } 
                    lines.add(values);                   
                    values.clear();
                }
                rs.close();
            }
           
            return lines;
        } catch (SQLException ex) {
            getLogger(SQLiteQueryProcesser.class.getName()).log(SEVERE, null, ex);
            return null;
        }

    }
    
}
