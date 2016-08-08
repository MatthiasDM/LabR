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
package labr_client.Public;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import labr_client.GUI.forms.AddNewRequests;

/**
 *
 * @author De Mey Matthias
 */
public class PublicFunctions {

    public static void populateTable(JTable table, List<String[]> things, int remove, List<Integer[]> columns, String[] headers) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Integer[] column : columns) {
            if (column[1] != -1) {
                table.getColumnModel().getColumn(column[0]).setMinWidth(column[1]);
            }
            if (column[2] != -1) {
                table.getColumnModel().getColumn(column[0]).setMaxWidth(column[2]);
            }
            if (column[3] != -1) {
                table.getColumnModel().getColumn(column[0]).setPreferredWidth(column[3]);
            }
           
            table.getColumnModel().getColumn(column[0]).setHeaderValue(headers[column[0]]);
            
        }
        
        int rows = model.getRowCount();
        if (remove == 1) {
            for (int i = rows - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }

        for (String str[] : things) {
            addTableRow(table, str);
        }
    }

       public static void populateTable(JTable table, List<String[]> things, int remove, List<Integer[]> columns) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Integer[] column : columns) {
            if (column[1] != -1) {
                table.getColumnModel().getColumn(column[0]).setMinWidth(column[1]);
            }
            if (column[2] != -1) {
                table.getColumnModel().getColumn(column[0]).setMaxWidth(column[2]);
            }
            if (column[3] != -1) {
                table.getColumnModel().getColumn(column[0]).setPreferredWidth(column[3]);
            }
        }
        
        int rows = model.getRowCount();
        if (remove == 1) {
            for (int i = rows - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }

        for (String str[] : things) {
            addTableRow(table, str);
        }
    }

    
    public static void addTableRow(JTable table, String[] str) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(str);
    }

    public static class SamplePredicate<t> implements Predicate<t> {

        public t varc1;

        public boolean test(t varc) {
            String[] testBepaling = (String[]) varc;
            String[] testProfiel = (String[]) varc1;
            if (testBepaling[0].equals(testProfiel[5])) {
                return true;
            }
            return false;
        }

    }

    public static double getWidth(String message, Font f, Graphics2D g2) {

        Rectangle2D bounds = getBounds(message, f, g2);
        return bounds.getWidth();
    }

    public static Rectangle2D getBounds(String message, Font f, Graphics2D g2) {

        FontRenderContext context;
        context = g2.getFontRenderContext();
        return f.getStringBounds(message, context);
    }
    
       public static int returnSmallest(int a, int b) {
        if (a == -1) {
            return b;
        } else if (a < b) {
            return a;
        } else {
            return b;
        }

    }

    public static int returnLargest(int a, int b) {
        if (a == -1) {
            return b;
        } else if (a > b) {
            return a;
        } else {
            return b;
        }
    }
}
