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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import labr_client.GUI.custom_classes.LabRequestPanel;
import static labr_client.GUI.forms.MainWindow.showPatientInfo;
import labr_client.ehealth.e_health;
import labr_client.Public.PublicVars;
import labr_client.xml.LabrRequest;
import labr_client.GUI.custom_classes.CustomJPanel;
/**
 *
 * @author familie
 */
public final class PatientInfo extends javax.swing.JFrame {

    LabRequestPanel lrp;
    List<String> patientDatabase;
    public PatientInfo(LabRequestPanel lp) {
  
        initComponents();
        addDocumentListener(jTextFieldSearchLastName);
        addDocumentListener(jTextFieldSearchFirstname);
        addDocumentListener(jTextFieldSearchBirthDate);
        addDocumentListener(jTextFieldSearchRijksregisternummer);
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        lrp = lp;
    }

    public void retrieveInformation() {
//        List<String> patientInfo = new ArrayList<String>();
//        patientInfo.add(jTextFieldFirstName.getText());
//        patientInfo.add(jTextFieldLastName.getText());
//        patientInfo.add(jTextFieldStreetAndNumber.getText());
//        patientInfo.add(jTextFieldPostalCode.getText());
//        patientInfo.add(jTextFieldCity.getText());
//        Enumeration<AbstractButton> allRadioButton = buttonGroupSex.getElements();
//        while (allRadioButton.hasMoreElements()) {
//            JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
//            if (temp.isSelected()) {
//                patientInfo.add(temp.getText());
//            }
//        }
//        patientInfo.add(jTextFieldBirthDate.getText());
//        patientInfo.add(jComboBoxCountries.getSelectedItem().toString());
//        patientInfo.add(jTextFieldRijksregisternummer.getText());
//        PublicVars.setPatientInformation(patientInfo);
    }

    public void setInfo(LabrRequest request) {
            
//            jTextFieldFirstName.setText(request.patient.firstName.getValue());
//            jTextFieldLastName.setText(request.patient.name.getValue());
//            jTextFieldStreetAndNumber.setText(request.patient.straatAndNumber.getValue());
//            jTextFieldPostalCode.setText(request.patient.zip.getValue());
//            jTextFieldCity.setText(request.patient.city.getValue());
//            Enumeration<AbstractButton> allRadioButton = buttonGroupSex.getElements();
//            while (allRadioButton.hasMoreElements()) {
//                JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
//                if (temp.getName() == request.patient.name.getValue()) {
//                    temp.setSelected(true);
//                }
//            }
//            jTextFieldBirthDate.setText(request.patient.birthDate.getValue());
//            jComboBoxCountries.setSelectedIndex(0);
//            jTextFieldRijksregisternummer.setText(request.patient.nationalNumber.getValue());
        
    }

    public void eid_get_info() {
        e_health e_health = new e_health();
        PublicVars.setPatientInformation(e_health.return_eID_info());
        //setInfo();

    }

    public List<String[]> searchPatientInDatabase() {
        String lastname = jTextFieldSearchLastName.getText();
        String firstname = jTextFieldSearchFirstname.getText();
        String birthDate = jTextFieldSearchBirthDate.getText();
        String nationalNumber = jTextFieldSearchRijksregisternummer.getText();
        List<String[]> lines = new ArrayList<String[]>();

        if (lastname != "" || firstname != "" || birthDate != "" || nationalNumber != "") {
            List<String> patientInfo = new ArrayList();
            patientInfo.add(lastname);
            patientInfo.add(firstname);
            patientInfo.add(birthDate);
            patientInfo.add(nationalNumber);

//        jTableSearchResults.getColumnModel().getColumn(0).setMinWidth(300);
//        jTableSearchResults.getColumnModel().getColumn(1).setMinWidth(60);
//        jTableSearchResults.getColumnModel().getColumn(2).setMinWidth(60);
//        jTableSearchResults.getColumnModel().getColumn(3).setMinWidth(60);
            lines = MainWindow.queries.selectPatient(patientInfo);
            if (lines != null) {
                DefaultTableModel model = (DefaultTableModel) jTableSearchResults.getModel();
                int rows = model.getRowCount();
                for (int i = rows - 1; i >= 0; i--) {
                    model.removeRow(i);
                }

                for (String[] line : lines) {
                    model.addRow(new Object[]{line[0], line[1], line[5]});
                }
            }

        }
        return lines;
    }

    public void addDocumentListener(JTextField myField) {

        myField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                searchPatientInDatabase();
            }

            public void removeUpdate(DocumentEvent e) {
                if (e.getLength() > 0) {
                    searchPatientInDatabase();
                }

            }

            public void changedUpdate(DocumentEvent e) {
                if (e.getLength() > 0) {
                    searchPatientInDatabase();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupSex = new javax.swing.ButtonGroup();
        jPanel1 = new CustomJPanel(480,640, "#FFFFFF");
        jLabelNewpatient = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSearchLastName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSearchFirstname = new javax.swing.JTextField();
        jTextFieldSearchRijksregisternummer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldSearchBirthDate = new javax.swing.JTextField();
        jLabelDateOfBirth1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSearchResults = new javax.swing.JTable();
        jButtonCancelPatientEntry = new javax.swing.JButton();
        jButtonConfirmPatientEntry = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel1PropertyChange(evt);
            }
        });

        jLabelNewpatient.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelNewpatient.setText("Search database");

        jLabel4.setText("Last name:");

        jTextFieldSearchLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchLastNameActionPerformed(evt);
            }
        });
        jTextFieldSearchLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchLastNameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSearchLastNameKeyTyped(evt);
            }
        });

        jLabel3.setText("First name:");

        jTextFieldSearchFirstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchFirstnameActionPerformed(evt);
            }
        });
        jTextFieldSearchFirstname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSearchFirstnameKeyTyped(evt);
            }
        });

        jTextFieldSearchRijksregisternummer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSearchRijksregisternummerKeyTyped(evt);
            }
        });

        jLabel5.setText("National number:");

        jTextFieldSearchBirthDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSearchBirthDateKeyTyped(evt);
            }
        });

        jLabelDateOfBirth1.setText("Date of birth:");

        jTableSearchResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Family name", "Fist name", "Date of birth", "National number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSearchResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSearchResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSearchResults);

        jButtonCancelPatientEntry.setText("Cancel");
        jButtonCancelPatientEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelPatientEntryActionPerformed(evt);
            }
        });

        jButtonConfirmPatientEntry.setText("Confirm");
        jButtonConfirmPatientEntry.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jButtonConfirmPatientEntryComponentHidden(evt);
            }
        });
        jButtonConfirmPatientEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmPatientEntryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNewpatient)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelDateOfBirth1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldSearchBirthDate)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldSearchRijksregisternummer, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldSearchLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldSearchFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonConfirmPatientEntry)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCancelPatientEntry)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabelNewpatient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldSearchFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jTextFieldSearchLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldSearchRijksregisternummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelDateOfBirth1)
                        .addComponent(jTextFieldSearchBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonConfirmPatientEntry)
                    .addComponent(jButtonCancelPatientEntry))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConfirmPatientEntryComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonConfirmPatientEntryComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmPatientEntryComponentHidden

    private void jButtonConfirmPatientEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmPatientEntryActionPerformed
        // TODO add your handling code here:
        retrieveInformation();
        MainWindow.showPatientInfo();
        this.setVisible(false);
    }//GEN-LAST:event_jButtonConfirmPatientEntryActionPerformed

    private void jButtonCancelPatientEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelPatientEntryActionPerformed
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCancelPatientEntryActionPerformed

    private void jTextFieldSearchLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchLastNameActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jTextFieldSearchLastNameActionPerformed

    private void jTextFieldSearchFirstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchFirstnameActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldSearchFirstnameActionPerformed

    private void jTextFieldSearchFirstnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchFirstnameKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldSearchFirstnameKeyTyped

    private void jTextFieldSearchLastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchLastNameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchLastNameKeyTyped

    private void jTextFieldSearchBirthDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchBirthDateKeyTyped
     ;
// TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchBirthDateKeyTyped

    private void jTextFieldSearchRijksregisternummerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchRijksregisternummerKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldSearchRijksregisternummerKeyTyped

    private void jTextFieldSearchLastNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchLastNameKeyReleased
        // TODO add your handling code here:
        //searchPatientInDatabase();
    }//GEN-LAST:event_jTextFieldSearchLastNameKeyReleased

    private void jTableSearchResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSearchResultsMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            JTable target = (JTable) evt.getSource();
            int row = target.getSelectedRow();            
            PublicVars.setPatientInformation(Arrays.asList(searchPatientInDatabase().get(row)));
            lrp.addPatientData();
           // setInfo();
        }
    }//GEN-LAST:event_jTableSearchResultsMouseClicked

    private void jPanel1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel1PropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel1PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupSex;
    private javax.swing.JButton jButtonCancelPatientEntry;
    public javax.swing.JButton jButtonConfirmPatientEntry;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelDateOfBirth1;
    private javax.swing.JLabel jLabelNewpatient;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSearchResults;
    private javax.swing.JTextField jTextFieldSearchBirthDate;
    private javax.swing.JTextField jTextFieldSearchFirstname;
    private javax.swing.JTextField jTextFieldSearchLastName;
    private javax.swing.JTextField jTextFieldSearchRijksregisternummer;
    // End of variables declaration//GEN-END:variables

}
