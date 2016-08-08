/*
 *  Copyright (C) Matthias De Mey - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by De Mey Matthias <de.mey.mat@gmail.com>, july 2016
 */
package labr_client.GUI.forms;

import be.ehealth.businessconnector.addressbook.service.AddressbookTokenService;
import be.ehealth.businessconnector.addressbook.service.impl.AddressbookTokenServiceImpl;
import be.ehealth.businessconnector.addressbook.session.AddressbookSessionService;
import be.ehealth.businessconnector.addressbook.session.AddressbookSessionServiceFactory;
import be.ehealth.technicalconnector.exception.ConnectorException;
import be.ehealth.technicalconnector.service.sts.security.SAMLToken;
import be.ehealth.technicalconnector.session.Session;
import be.ehealth.technicalconnector.validator.impl.EhealthReplyValidatorImpl;
import be.ehealth.technicalconnector.validator.impl.SAMLSessionValidator;
import be.fgov.ehealth.aa.complextype.v1.HealthCareProfessional;
import be.fgov.ehealth.addressbook.protocol.v1.GetProfessionalContactInfoRequest;
import be.fgov.ehealth.addressbook.protocol.v1.GetProfessionalContactInfoResponse;
import be.fgov.ehealth.addressbook.protocol.v1.SearchOrganizationsRequest;
import be.fgov.ehealth.addressbook.protocol.v1.SearchOrganizationsResponse;
import be.fgov.ehealth.addressbook.protocol.v1.SearchProfessionalsRequest;
import be.fgov.ehealth.addressbook.protocol.v1.SearchProfessionalsResponse;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.GUI.custom_classes.CustomJPanel;
import static labr_client.GUI.forms.MainWindow.queries;
import labr_client.Public.PublicFunctions;
import labr_client.Public.PublicVars;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;

/**
 *
 * @author labbl
 */
public class EhealthSearch extends javax.swing.JFrame {

    /**
     * Creates new form EhealthSearch
     */
    public EhealthSearch() {
        initComponents();
        this.setVisible(true);
    }

    public SearchProfessionalsResponse searchProf() throws ConnectorException {
        SearchProfessionalsResponse response = null;
        if (NumberUtils.isNumber(jTextFieldProfNihii.getText())) {
            response = searchProfessionals(jTextFieldProfNihii.getText());
        }
        return response;
    }

    public void populateTableOrgs() {

    }

    public void populateTableProfs(SearchProfessionalsResponse response) {
                List<Integer[]> columnData = asList(
                new Integer[]{0, 300, -1, -1},
                new Integer[]{1, 60, -1, -1},
                new Integer[]{2, 60, -1, -1}
                
        );
        String[] headings = {"Name", "Firstname", "SSIN"};
        List<String[]> formattedLines = new ArrayList<>();
        
        for (HealthCareProfessional prof : response.getHealthCareProfessionals()) {
            formattedLines.add(new String[]{prof.getLastName(), prof.getFirstName(), prof.getSSIN()});
        }
        PublicFunctions.populateTable(jTable1, formattedLines, 1, columnData, headings);
    }

    SAMLToken token;

    public void getProfessionalContactInfoTest(String nihii) throws Exception {
        //verifySession();
        token = Session.getInstance().getSession().getSAMLToken();
        GetProfessionalContactInfoRequest request = new GetProfessionalContactInfoRequest();
        request.setNIHII(nihii);
        request.setIssueInstant(new DateTime());
        AddressbookTokenService service = new AddressbookTokenServiceImpl(new SAMLSessionValidator(), new EhealthReplyValidatorImpl());
        GetProfessionalContactInfoResponse response = service.getProfessionalContactInfo(token, request);
        //AddressbookTestUtils.verifyResponseGetProfessionalContactInfo(response);
        String test = "1";
    }

    public SearchProfessionalsResponse searchProfessionals(String nihii) throws ConnectorException {
        SearchProfessionalsRequest request = createSearchProfessionalsRequest(nihii);
        AddressbookSessionService service = AddressbookSessionServiceFactory.getAddressbookSessionService();
        SearchProfessionalsResponse response = service.searchProfessionals(request);
        //AddressbookTestUtils.verifySearchProfessionalsResponse(response);
        return response;
    }

    public SearchProfessionalsResponse searchProfessionals(String name, String firstname) throws ConnectorException {
        SearchProfessionalsRequest request = createSearchProfessionalsRequest(firstname, name);
        AddressbookSessionService service = AddressbookSessionServiceFactory.getAddressbookSessionService();
        SearchProfessionalsResponse response = service.searchProfessionals(request);
        //AddressbookTestUtils.verifySearchProfessionalsResponse(response);
        return response;
    }

    public static SearchProfessionalsRequest createSearchProfessionalsRequest(String firstname, String name) {
        SearchProfessionalsRequest request = new SearchProfessionalsRequest();
        request.setFirstName(firstname);
        request.setLastName(name);
        request.setIssueInstant(DateTime.now());
        //request.setOffset(1);
        request.setMaxElements(10);
        return request;
    }

    public static SearchProfessionalsRequest createSearchProfessionalsRequest(String nihii) {
        SearchProfessionalsRequest request = new SearchProfessionalsRequest();
        request.setNIHII(nihii);
        request.setIssueInstant(DateTime.now());
        request.setOffset(1);
        request.setMaxElements(10);
        return request;
    }

    //---------------------------------------------------------------
    public void searchOrganizations(String nihii) throws ConnectorException {
        SearchOrganizationsRequest request = createSearchOrganizationsRequest(nihii);
        AddressbookSessionService service = AddressbookSessionServiceFactory.getAddressbookSessionService();
        SearchOrganizationsResponse response = service.searchOrganizations(request);

        String a = "1";
        //AddressbookTestUtils.verifySearchOrganizationsResponse(response);
    }

    public void searchOrganizations(String name, boolean bool) throws ConnectorException {
        SearchOrganizationsRequest request = createSearchOrganizationsRequest(name, bool);
        AddressbookSessionService service = AddressbookSessionServiceFactory.getAddressbookSessionService();
        SearchOrganizationsResponse response = service.searchOrganizations(request);
        //AddressbookTestUtils.verifySearchOrganizationsResponse(response);
    }

    public static SearchOrganizationsRequest createSearchOrganizationsRequest(String nihii) {
        SearchOrganizationsRequest request = new SearchOrganizationsRequest();
        request.setNIHII(nihii);
        request.setIssueInstant(DateTime.now());
        request.setOffset(1);
        request.setMaxElements(10);
        return request;
    }

    public static SearchOrganizationsRequest createSearchOrganizationsRequest(String name, boolean bool) {
        SearchOrganizationsRequest request = new SearchOrganizationsRequest();
        request.setInstitutionName(name);
        request.setIssueInstant(DateTime.now());
        request.setOffset(1);
        request.setMaxElements(10);
        return request;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new CustomJPanel(671,455, "#FFFFFF");
        jTextFieldOrgNihii = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jTextFieldProfNihii = new javax.swing.JTextField();
        jTextFieldProfFirstName = new javax.swing.JTextField();
        jTextFieldProfName = new javax.swing.JTextField();
        jTextFieldOrgName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextFieldOrgNihii.setText("NIHII number");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextFieldProfNihii.setText("NIHII number");

        jTextFieldProfFirstName.setText("Firstname");

        jTextFieldProfName.setText("Name");

        jTextFieldOrgName.setText("Name");

        jLabel1.setText("Search professional");

        jLabel2.setText("Search organisation");

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldProfFirstName)
                                    .addComponent(jTextFieldProfNihii, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextFieldProfName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3)))))
                        .addGap(87, 87, 87)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldOrgNihii)
                                    .addComponent(jTextFieldOrgName, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(6, 6, 6)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldProfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOrgName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldProfNihii, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jTextFieldOrgNihii, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            searchOrganizations(jTextFieldOrgNihii.getText());
        } catch (Exception ex) {
            Logger.getLogger(EhealthSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            populateTableProfs(searchProf());
        } catch (ConnectorException ex) {
            Logger.getLogger(EhealthSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            
            populateTableProfs(searchProfessionals(jTextFieldProfFirstName.getText(), jTextFieldProfName.getText()));
        } catch (ConnectorException ex) {
            Logger.getLogger(EhealthSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            searchOrganizations(jTextFieldOrgName.getText(), true);
        } catch (ConnectorException ex) {
            Logger.getLogger(EhealthSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EhealthSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EhealthSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EhealthSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EhealthSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EhealthSearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldOrgName;
    private javax.swing.JTextField jTextFieldOrgNihii;
    private javax.swing.JTextField jTextFieldProfFirstName;
    private javax.swing.JTextField jTextFieldProfName;
    private javax.swing.JTextField jTextFieldProfNihii;
    // End of variables declaration//GEN-END:variables
}
