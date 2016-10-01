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

import be.ehealth.businessconnector.ehbox.api.domain.Addressee;
import be.ehealth.businessconnector.ehbox.api.domain.exception.EhboxBusinessConnectorException;
import be.ehealth.technicalconnector.exception.ConnectorException;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.utils.IdentifierType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import labr_client.GUI.custom_classes.ComponentMover;
import labr_client.GUI.custom_classes.Dynamic_swing;
import labr_client.GUI.custom_classes.PanelGraphics;
import labr_client.Public.PublicVars;
import labr_client.SQLite.SQLiteQueries;
import labr_client.SQLite.SQLiteQueryProcesser;
import labr_client.SQLite.SqLiteSessionManager;
import labr_client.ehealth.e_health;
import labr_client.ehealth.e_health_consultation;
import labr_client.ehealth.e_health_key_depot;
import labr_client.ehealth.e_health_publication;
import labr_client.ehealth.e_health_session_manager;
import labr_client.kmehrObjects.KmehrLabRequest;
import labr_client.server.lab_server;
import labr_client.xml.Kmehr.KmehrMessage;
import labr_client.xml.ObjToXML;
import org.bouncycastle.cms.CMSException;

/**
 *
 * @author familie
 */
public class MainWindow extends javax.swing.JFrame {

    private lab_server server_con;
    public static e_health_session_manager e_health_session_manager;
    private e_health e_health;
    private Dynamic_swing gui_components;
    private KmehrLabRequest LabRequest1;
    private SQLiteQueryProcesser SQL;
    private e_health_key_depot keyDepot;
    private SqLiteSessionManager l;
    public static SQLiteQueries queries;
    private LabRequestViewer labRequestViewer;
    public static ComponentMover cm;
    public static OptionWindow optionWindow;
    public PanelGraphics labRequestPanel;
    static PatientInfo patientInfoDialog;
    static boolean isRemovingItem = false;

    /**
     * Creates new form GUI
     */
    public MainWindow() {
        initComponents();
        try {
            onload();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    public static void OpenPatientSearch(Component[] comps) {

    }

    public void OpenAddRequestsList() {
        AddNewRequests addNewRequestsInstance = new AddNewRequests();
        //this.pack();
    }

    public void ClickRequests() {

        for (String[] request : PublicVars.getRequestsInProfile()) {
            if (PublicVars.getCurrentGroupLabel().equals(request[6])) {

                for (Component c : labRequestPanel.getComponents()) {
                    if (c instanceof JCheckBox) {
                        if (((JCheckBox) c).getMnemonic() == Integer.parseInt(request[5])) {
                            if (((JCheckBox) c).isSelected()) {
                                ((JCheckBox) c).setSelected(false);
                            } else {
                                ((JCheckBox) c).setSelected(true);
                            }
                        }
                    }
                }
            }
        }
    }

    class SamplePredicate<t> implements Predicate<t> {

        t varc1;

        public boolean test(t varc) {
            String[] testBepaling = (String[]) varc;
            String[] testProfiel = (String[]) varc1;
            if (testBepaling[0].equals(testProfiel[5])) {
                return true;
            }
            return false;
        }

    }

    public void onload() throws IOException {

        //p.load(new FileInputStream(PublicVars.getDefaultPropertiesLocation()));
        //PublicVars.setProperties(p);   
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = Integer.parseInt(PublicVars.getProperties().getProperty("width"));
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;

        if (w > screenSize.width) {
            w = screenSize.width;
        }
        int h = Integer.parseInt(PublicVars.getProperties().getProperty("height"));
        if (h > screenSize.height - taskBarSize) {
            h = screenSize.height - taskBarSize;
        }
        this.setSize(w, h);
        init_objects();
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public void init_objects() {

        gui_components = new Dynamic_swing();
        e_health_session_manager = new e_health_session_manager();
        e_health = new e_health();
//        keyDepot = new e_health_key_depot();
        queries = PublicVars.getQueries();
        labRequestViewer = new LabRequestViewer(this, true);
        optionWindow = new OptionWindow(this, true);

        //jTabbedPane1.setVisible(false);
        //jMenuBar1.setVisible(false);
        //---------------------------------------------------------------
        labRequestPanel = new PanelGraphics(getWidth() - 17, getHeight() - 34);
        this.add(labRequestPanel);
        labRequestPanel.setVisible(true);

        //---------------------------------------------------------------       
        cm = PublicVars.getCM();
        l = new SqLiteSessionManager();
        jLabelUser.setText(PublicVars.getUsername());
        infoTimer.start();
//        loadProfiles();
        //      loadSentMessages();
    }

    public void send_to_server() {
        server_con.server_send("test");
    }

    public static void ehealth_start_session() throws TechnicalConnectorException {

        if (e_health_session_manager.start_session()) {
            String name = null;
            jLabelSessionStatus.setForeground(Color.green);
            jLabelSessionStatus.setText("Online");
        }
    }

    public static List<Addressee> getReceivers(KmehrMessage message) {
        List<Addressee> addresseeList = new ArrayList<Addressee>();
        Addressee addressee = new Addressee(IdentifierType.NIHII_LABO);
        addressee.setId("83166909");//90060717196   83166909
        addressee.setQuality("LABO");
        addresseeList.add(addressee);
        return addresseeList;
    }

    public void get_ehealth_user_info() {
        keyDepot.checkCertificate();
        e_health.get_ehealth_user_info();
    }

    public void application_close_events() {
        e_health_session_manager.close_session();
        System.exit(0);
    }

    public String createLabRequest() {
        // PublicVars.setReceiver(getReceivers());
        String xml = "";
        LabRequest1 = new KmehrLabRequest();
        xml = LabRequest1.printLabRequest(gui_components.getSelectedCheckboxLabTest(labRequestPanel.getComponents()));
        return xml;
    }

    public void check_LabRequest(String xml) {
        if (!PublicVars.getSenderName().isEmpty()) {
            save_LabRequest(xml, PublicVars.getSenderName());
        } else {
            save_LabRequest(xml, PublicVars.getUsername());
        }
    }

    public void save_LabRequest(String xml, String senderName) {
//        List<String> patientInfo = PublicVars.getPatientInformation();
//        try {
//            write_file(xml);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
//            String date = dateFormat.format(Calendar.getInstance().getTime());
//            PublicVars.setTitel("LABR-" + senderName + "-" + date);
//            PublicVars.setMimeType("text/xml");
//            List<String[]> lines = queries.selectPatient(patientInfo);
//            if (!lines.isEmpty()) {
//                insert_LabRequestInDatabase(lines, xml);
//            } else if (!patientInfo.isEmpty()) {
//                queries.insertPatient(patientInfo);
//                lines = queries.selectPatient(patientInfo);
//                insert_LabRequestInDatabase(lines, xml);
//            } else {
//                Dynamic_swing.infoBox("No patient information found.", "Patient");
//            }
//
//            Dynamic_swing.infoBox("Lab request created", "Lab request");
//        } catch (IOException ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void insert_LabRequestInDatabase(List<String[]> lines, String xml) {
//        lines = queries.selectPatient(PublicVars.getPatientInformation());
//        String sender = PublicVars.getSenderName();
//        String receiver = PublicVars.getReceiverName();
//        String titel = PublicVars.getTitel();
//        String mimeType = PublicVars.getMimeType();
//        for (String[] line : lines) {
//            queries.insertLabRequest(xml, Integer.parseInt(line[9]), Integer.parseInt(PublicVars.getUserID()), titel, mimeType, sender, receiver);
//        }
    }

    public void send_LabRequest(String xml) {
        try {
            e_health_publication.sendDocumentMessage(xml, PublicVars.getReceiver());
        } catch (EhboxBusinessConnectorException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CMSException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectorException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendToEhealth(KmehrMessage kmehrMessage) {
        String xml = ObjToXML.marshallRequest(kmehrMessage);
        try {
            e_health_publication.sendDocumentMessage(xml, PublicVars.getReceiver());
        } catch (EhboxBusinessConnectorException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CMSException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectorException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write_file(String text) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("kmehr.xml"), "utf-8"))) {
            writer.write(text);
        }
    }

//    public void loadLabRequestPanel() {
//
//        if (labRequestPanel.getComponents() != null) {
//            labRequestPanel.removeAll();
//        }
//
//        PublicVars.setRequestsInProfile(queries.loadRequestPanel());
//        boolean selected = false;
//
//        for (String[] values : PublicVars.getRequestsInProfile()) {
//            if (values[4].equals("1")) {
//                selected = true;
//            } else {
//                selected = false;
//            }
//
//            JCheckBox ckbx = gui_components.getCkbx(Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[0], values[1], selected, Integer.parseInt(values[5]));
//            labRequestPanel.add(ckbx);
//            cm.registerComponent(ckbx);
//        }
//        jScrollPane_LabRequests.getVerticalScrollBar().setUnitIncrement(16);
//        labRequestPanel.repaint();
//
//    }
//    public static void loadProfiles() {
//        int i = jComboBoxRequestProfile.getItemCount();
//        if (i > 0) {
//            for (i = 3; i >= 0; i--) {
//                jComboBoxRequestProfile.removeItemAt(i);
//            }
//        }
//
//        jComboBoxRequestProfile.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (renderer instanceof JLabel && value instanceof String[]) {
//                    // Here value will be of the Type 'CD'
//                    String[] values = (String[]) value;
//                    ((JLabel) renderer).setText("test");
//                }
//                return renderer;
//            }
//        });
//
//        List<String[]> lines = queries.loadProfiles();
//        PublicVars.setProfielNamen(lines);
//
//        for (String[] values : lines) {
//
//            JLabel item = new JLabel();
//            item.setName(values[0]);
//            item.setText(values[1]);
//
//            jComboBoxRequestProfile.addItem(item);
//
//        }
//
//    }
    private Object makeObj(final String item) {
        return new Object() {
            public String toString() {
                return item;
            }
        };
    }

    public String getRequest() {
        // return jTextAreaRequest.getText();
        return "";
    }

    public void send_ehealth_request() {

        try {
            e_health_consultation.XMLRequest(getRequest());
            //e_health_consultation.getBoxInfo();
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showPatientInfo() {
//        List<String> patientInfo = PublicVars.getPatientInformation();
//        if (patientInfo.size() > 0) {
//            jLabelClickToAddPatient.setFont(new Font(jLabelClickToAddPatient.getFont().getName(), Font.BOLD, 12));
//            jLabelClickToAddPatient.setText("<html>" + patientInfo.get(0) + " " + patientInfo.get(1) + "<br>" + patientInfo.get(2) + "<br>" + patientInfo.get(3) + "<br>" + patientInfo.get(4) + "</html>");
//        }
    }

    public static void hideAddPatientLabel() {
        //   jLabelClickToAddPatient.setVisible(false);
    }

//    public void loadSentMessages() {
////        jTableSentRequests.getColumnModel().getColumn(0).setMinWidth(300);
////        jTableSentRequests.getColumnModel().getColumn(1).setMinWidth(60);
////        jTableSentRequests.getColumnModel().getColumn(2).setMinWidth(60);
////        jTableSentRequests.getColumnModel().getColumn(3).setMinWidth(50);
////        jTableSentRequests.getColumnModel().getColumn(4).setMinWidth(30);
////        jTableSentRequests.getColumnModel().getColumn(5).setMinWidth(0);
////        jTableSentRequests.getColumnModel().getColumn(5).setMaxWidth(0);
////        jTableSentRequests.getColumnModel().getColumn(5).setPreferredWidth(0);
////        DefaultTableModel model = (DefaultTableModel) jTableSentRequests.getModel();
////        int rows = model.getRowCount();
////        for (int i = rows - 1; i >= 0; i--) {
////            model.removeRow(i);
////        }
//
//        List<Integer[]> columnData = asList(
//                new Integer[]{0, 300, -1, -1},
//                new Integer[]{1, 60, -1, -1},
//                new Integer[]{2, 60, -1, -1},
//                new Integer[]{3, 50, -1, -1},
//                new Integer[]{4, 30, -1, -1},
//                new Integer[]{5, 0, 0, 0}
//        );
//
//        PopulateTable1(jTableSentRequests, columnData);
//        PublicVars.setSentMessages(queries.selectSentRequests());
//
//        for (String[] line : PublicVars.getSentMessages()) {
//            //loadSentMessages(line[6], line[1] + " " + line[2], line[5], line[7], "", line[8]);
//            List<String> formattedLines = asList(line[6], line[1] + " " + line[2], line[5], line[7], "", line[8]);
//            PopulateTable2(jTableSentRequests, formattedLines);
//        }
//
//    }
    public void selectRequestPanel() {

//        for (Component comp : MainWindow.jPanelGraphics.getComponents()) {
//            Class<? extends Component> c = comp.getClass();
//            if (c.getSimpleName().equals("LabRequestPanel")) {
//                JPanel labRequestPanel = (JPanel) comp;
//                PublicVars.setLabRequestPanel(labRequestPanel);
//            }
//
//        }
    }

    public static void PopulateTable1(JTable table, List<Integer[]> columns) {
        //colums is van volgend formaat: Integer['kolomnummer','minWidth','Maxwidth','prefferedWidth']
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
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rows = model.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

//    public void PopulateTable2(JTable table, List<String> lines) {
//        DefaultTableModel model = (DefaultTableModel) jTableSentRequests.getModel();
//        Object rowData[] = new Object[lines.size()];
//        int i = 0;
//        for (i = 0; i < lines.size(); i++) {
//            rowData[i] = lines.get(i);
//        }
//        model.addRow(rowData);
//    }
//    public void loadSentMessages(String str1, String str2, String str3, String str4, String str5, String str6) {
//        DefaultTableModel model = (DefaultTableModel) jTableSentRequests.getModel();
//        model.addRow(new Object[]{str1, str2, str3, str4, str5, str6});
//    }
    public static void deleteProfile() {
        //popup "Weet u zeker"?
        //remove profile
        //remove testen die aan profile hangen
        //labels die aan profile hangen
        //andere dingen die in profile hangen

    }

    public void ramUsage() {
        long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        mem /= 1000000;
        jLabelRAM.setText(String.valueOf(mem));
    }

    Timer infoTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ramUsage();
        }     
    });

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuRequestOptions = new javax.swing.JPopupMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPopupMenuRequestPanelOptions = new javax.swing.JPopupMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItemAddLabel = new javax.swing.JMenuItem();
        jPopupMenuLabel = new javax.swing.JPopupMenu();
        jMenuItemEditLabel = new javax.swing.JMenuItem();
        jMenuItemDeleteLabel = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabelEIDName = new javax.swing.JLabel();
        jLabelUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelSessionStatus = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelSentToLab = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0));
        jLabel2 = new javax.swing.JLabel();
        jLabelRAM = new javax.swing.JLabel();

        jMenuItem3.setText("Delete item");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenuRequestOptions.add(jMenuItem3);

        jMenuItem5.setText("Add items");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenuRequestPanelOptions.add(jMenuItem5);

        jMenuItemAddLabel.setText("Add label");
        jMenuItemAddLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddLabelActionPerformed(evt);
            }
        });
        jPopupMenuRequestPanelOptions.add(jMenuItemAddLabel);

        jMenuItemEditLabel.setText("Edit label");
        jMenuItemEditLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditLabelActionPerformed(evt);
            }
        });
        jPopupMenuLabel.add(jMenuItemEditLabel);

        jMenuItemDeleteLabel.setText("Delete label");
        jMenuItemDeleteLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteLabelActionPerformed(evt);
            }
        });
        jPopupMenuLabel.add(jMenuItemDeleteLabel);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);

        jLabel1.setText("  User: ");
        jLabel1.setToolTipText("");
        jToolBar1.add(jLabel1);

        jLabelEIDName.setToolTipText("");
        jToolBar1.add(jLabelEIDName);
        jToolBar1.add(jLabelUser);

        jLabel3.setText("            Ehealth session: ");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jLabel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel3KeyPressed(evt);
            }
        });
        jToolBar1.add(jLabel3);

        jLabelSessionStatus.setText("offline");
        jLabelSessionStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSessionStatusMouseClicked(evt);
            }
        });
        jToolBar1.add(jLabelSessionStatus);

        jLabel14.setText("        Sent to lab:  ");
        jToolBar1.add(jLabel14);

        jLabelSentToLab.setText("default");
        jToolBar1.add(jLabelSentToLab);
        jToolBar1.add(filler1);

        jLabel2.setText("RAM: ");
        jToolBar1.add(jLabel2);

        jLabelRAM.setText("jLabel4");
        jToolBar1.add(jLabelRAM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelSessionStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSessionStatusMouseClicked
        // TODO add your handling code here:
        get_ehealth_user_info();
    }//GEN-LAST:event_jLabelSessionStatusMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        application_close_events();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        JCheckBox target = (JCheckBox) PublicVars.getSelectComponentOnLabRequestPanel();
//        MainWindow.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
        //Dynamic_swing.infoBox(target.getName(), target.getText());
        labRequestPanel.remove(target);
        labRequestPanel.revalidate();
        labRequestPanel.repaint();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        OpenAddRequestsList();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItemAddLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddLabelActionPerformed
        // TODO add your handling code here:
        // labRequestPanel.AddLabelToProfile(PublicVars.getPopUpClick());
    }//GEN-LAST:event_jMenuItemAddLabelActionPerformed

    private void jMenuItemEditLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditLabelActionPerformed
        // TODO add your handling code here:
        //LabelForm labelForm = new LabelForm(true, true, labRequestPanel.lrp);
    }//GEN-LAST:event_jMenuItemEditLabelActionPerformed

    private void jMenuItemDeleteLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteLabelActionPerformed
        // TODO add your handling code here:
        //    DeleteLabel();
    }//GEN-LAST:event_jMenuItemDeleteLabelActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        labRequestPanel.setSize(getWidth() - 17, getHeight() - 34);
    }//GEN-LAST:event_formComponentResized

    private void jLabel3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel3KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel3KeyPressed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        EhealthSearch es = new EhealthSearch();
        try {
            es.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelEIDName;
    private javax.swing.JLabel jLabelRAM;
    private javax.swing.JLabel jLabelSentToLab;
    private static javax.swing.JLabel jLabelSessionStatus;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItemAddLabel;
    private javax.swing.JMenuItem jMenuItemDeleteLabel;
    private javax.swing.JMenuItem jMenuItemEditLabel;
    public static javax.swing.JPopupMenu jPopupMenuLabel;
    public static javax.swing.JPopupMenu jPopupMenuRequestOptions;
    public static javax.swing.JPopupMenu jPopupMenuRequestPanelOptions;
    public static javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    //-------------------------------------------------------------------
}
