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
package labr_client.GUI.forms;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import labr_client.GUI.custom_classes.CustomJPanel;
import labr_client.GUI.custom_classes.LabRequestPanel;
import static labr_client.GUI.forms.MainWindow.queries;
import labr_client.Public.PublicVars;

/**
 *
 * @author familie
 */
public class LabelForm extends javax.swing.JDialog {

    public Color color;
    private Point location;
    boolean edit = false;
    LabRequestPanel parentPanel;
    JLabel lbl;
    /**
     * Creates new form LabelForm
     */
    public LabelForm(boolean modal, Point p, LabRequestPanel lrp) {
   
        initComponents();
        PublicVars.setCurrentGroupLabel(null);
        loadRequests();
        jSpinnerTextSIze.setValue((Object) 11);
        setLocation(p);
        edit = false;
        parentPanel = lrp;
        //Dynamic_swing.infoBox(PublicVars.getCurrentGroupLabel(), PublicVars.getCurrentGroupLabel());
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public LabelForm(boolean modal, boolean e, LabRequestPanel lrp, JLabel lbl) {
 
        initComponents();
        loadRequests();
        edit = e;
        parentPanel = lrp;
        this.lbl = lbl;
        if (edit) {
            //List<String[]> values = queries.selectLabelByID(PublicVars.getCurrentGroupLabel());
            jSpinnerTextSIze.setValue(lbl.getFont().getSize());
            color = lbl.getForeground();
            jTextFieldText.setText(lbl.getText());

        }
       // parentPanel = ref;
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void setLocation(Point p) {
        int x = (p.x / 10);
        int y = (p.y / 10);
        location = new Point(x * 10, y * 10);
    }

    public void loadRequests() {
        // jTableRequests.getColumnModel().getColumn(0).setMinWidth(jTableRequests.getPreferredSize().width);
        jTableRequests.getColumnModel().getColumn(1).setMinWidth(0);
        jTableRequests.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableRequests.getColumnModel().getColumn(1).setPreferredWidth(0);
        DefaultTableModel model = (DefaultTableModel) jTableRequests.getModel();
        int rows = model.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        if(PublicVars.getRequestsInProfile() != null){
        for (String[] line : PublicVars.getRequestsInProfile()) {
            addRequest(line[0], line[5], line[6]);
        }
        }
    }

    public void addRequest(String str1, String str2, String str3) {
        DefaultTableModel model = (DefaultTableModel) jTableRequests.getModel();
        ListSelectionModel selectionModel = jTableRequests.getSelectionModel();

        model.addRow(new Object[]{str1, str2});
        if (str3 != null && PublicVars.getCurrentGroupLabel() != null) {
            if (PublicVars.getCurrentGroupLabel().equals(str3)) {
                int min = model.getRowCount();
                selectionModel.addSelectionInterval(min - 1, min - 1);
            }
        }

    }

    public void addLabel() {
//        queries.insertLabel(PublicVars.getProfielID(), Integer.parseInt(PublicVars.getUserID()), (PublicVars.getPopUpClick().x/10)*10, (PublicVars.getPopUpClick().y/10)*10, (int) jSpinnerTextSIze.getValue(), toHexString(color), jTextFieldText.getText());
        selectLabel(jTextFieldText.getText());
    }

    public void updateLabel() {
   //     queries.updateLabel(PublicVars.getCurrentGroupLabel(), (int) jSpinnerTextSIze.getValue(), toHexString(color), jTextFieldText.getText());
        selectLabel(jTextFieldText.getText());
    }

    public void selectLabel(String label) {
        List<String[]> Labels = queries.selectLabel(label);
        if (Labels.size() > 0) {
            PublicVars.setCurrentGroupLabel(Labels.get(0)[0]);
        }
    }

    public void addRequestsToLabel() {
        List<String> BepalingIDs = new ArrayList<String>();
        for (int row : jTableRequests.getSelectedRows()) {
            BepalingIDs.add((String) jTableRequests.getValueAt(row, 1));
        }
//        queries.updateRequestGroupLabel(BepalingIDs, Integer.parseInt(PublicVars.getCurrentGroupLabel()));
    }

    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CustomJPanel(300,600, "#FFFFFF");
        jLabel3 = new javax.swing.JLabel();
        jSpinnerTextSIze = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRequests = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setText("Text size: ");

        jSpinnerTextSIze.setToolTipText("");

        jLabel4.setText("Choose color:");

        jButton3.setText("Color");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Enter label text:");

        jLabel2.setText("<html>Select lab requests that will automatically<br> be selected when clicking the label</html>");

        jTableRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Lab Request", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableRequests);
        if (jTableRequests.getColumnModel().getColumnCount() > 0) {
            jTableRequests.getColumnModel().getColumn(0).setResizable(false);
            jTableRequests.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerTextSIze, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinnerTextSIze, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        color = JColorChooser.showDialog(this, "Choose color", Color.black);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!edit) {
//            addLabel();
//            addRequestsToLabel();
//            PublicVars.setRequestsInProfile(queries.loadRequestPanel());
              parentPanel.addLabel((PublicVars.getPopUpClick().x/10)*10, (PublicVars.getPopUpClick().y/10)*10, jTextFieldText.getText(), (int) jSpinnerTextSIze.getValue(), color.getRGB());
     
        } else {
//            updateLabel();
//            addRequestsToLabel();
//            PublicVars.setRequestsInProfile(queries.loadRequestPanel());
              parentPanel.editLabel(lbl, (PublicVars.getPopUpClick().x/10)*10, (PublicVars.getPopUpClick().y/10)*10, jTextFieldText.getText(), (int) jSpinnerTextSIze.getValue(), color.getRGB());
     
        }
        
        //parentPanel.updateLabelPositions();
        //parentPanel.loadLabels();
         //parentPanel.revalidate();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LabelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LabelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LabelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LabelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                LabelForm dialog = new LabelForm(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerTextSIze;
    private javax.swing.JTable jTableRequests;
    private javax.swing.JTextField jTextFieldText;
    // End of variables declaration//GEN-END:variables
}
