/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static labr_client.GUI.forms.MainWindow.queries;
import labr_client.Public.PublicFunctions;
import labr_client.Public.PublicVars;
import labr_client.xml.XMLToObj;

/**
 *
 * @author labbl
 */
public class customOutbox extends JTable {

    LabRequestPanel lrp;
PanelGraphics pg;
    public customOutbox(LabRequestPanel lp, PanelGraphics p) {
        lrp = lp;
        pg = p;
        String[] headings = {"Subject", "Patient", "Receiver", "Date", "ID"};
        this.setModel(new DefaultTableModel(headings, 5));
        this.setBorder(null);
        this.setBackground(Color.white);
        this.setFillsViewportHeight(true);
        this.setShowGrid(false);
        this.setRowHeight(30);
        
        loadSentMessages();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();                  
                    String id = (String) target.getValueAt(row, 3);
                    String req = PublicVars.getQueries().selectLabrRequest(Integer.parseInt(id)).get(0)[0];
                    lrp.removeAll();
                    lrp.loadRequestOrProfile(XMLToObj.loadRequest(req));
                    
                    pg.setLrpVisible();
                }
            }

        });

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    ;
    
    public void loadSentMessages() {
//        jTableSentRequests.getColumnModel().getColumn(0).setMinWidth(300);
//        jTableSentRequests.getColumnModel().getColumn(1).setMinWidth(60);
//        jTableSentRequests.getColumnModel().getColumn(2).setMinWidth(60);
//        jTableSentRequests.getColumnModel().getColumn(3).setMinWidth(50);
//        jTableSentRequests.getColumnModel().getColumn(4).setMinWidth(30);
//        jTableSentRequests.getColumnModel().getColumn(5).setMinWidth(0);
//        jTableSentRequests.getColumnModel().getColumn(5).setMaxWidth(0);
//        jTableSentRequests.getColumnModel().getColumn(5).setPreferredWidth(0);
//        DefaultTableModel model = (DefaultTableModel) jTableSentRequests.getModel();
//        int rows = model.getRowCount();
//        for (int i = rows - 1; i >= 0; i--) {
//            model.removeRow(i);
//        }

        List<Integer[]> columnData = asList(
                new Integer[]{0, 300, -1, -1},
                new Integer[]{1, 60, -1, -1},
                new Integer[]{2, 60, -1, -1},
                new Integer[]{3, 0, 0, 0}
        );
        String[] headings = {"Subject", "Patient", "Date", "ID"};
        List<String[]> formattedLines = new ArrayList<String[]>();
        PublicVars.setSentMessages(queries.selectSentRequests());
        for (String[] line : PublicVars.getSentMessages()) {
            formattedLines.add(new String[]{line[4], line[1] + " " + line[2], line[5], line[6]});
        }
        PublicFunctions.populateTable(this, formattedLines, 1, columnData, headings);

//        for (String[] line : PublicVars.getSentMessages()) {
//            //loadSentMessages(line[6], line[1] + " " + line[2], line[5], line[7], "", line[8]);
//            List<String> formattedLines = asList(line[6], line[1] + " " + line[2], line[5], line[7], "", line[8]);
//            PopulateTable2(jTableSentRequests, formattedLines);
//        }
    }

}
