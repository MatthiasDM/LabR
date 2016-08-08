/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matmey
 */
public class SQLiteQueryProcesser extends SQLite {

    public void parseUpdateStatement(String sql) {
        try {
            Connection c = getC();
            //Statement stmt = c.createStatement();
            PreparedStatement ps = c.prepareStatement(sql);

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
            ps.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteQueryProcesser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Select MNEMONIC from bepalingen inner join Aanvraagprofielen on bepalingen.ID = Aanvraagprofielen.BepalingID WHERE Aanvraagprofielen.ProfielID = 1

    public List<String[]> parseSelectStatement(String sql, String[] attributes) {
        try {
            Connection c = getC();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<String[]> lines = new ArrayList<String[]>();
            ArrayList<String> values = new ArrayList<String>();

            while (rs.next()) {

                for (String attr : attributes) {
                    values.add(rs.getString(attr));
                }
                String[] stringArray = Arrays.copyOf(values.toArray(), values.toArray().length, String[].class);
                lines.add(stringArray);
                values.removeAll(values);
                values.clear();
            }
            rs.close();
            stmt.close();
           
            return lines;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteQueryProcesser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
