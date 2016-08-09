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

import labr_client.GUI.custom_classes.Dynamic_swing;
import labr_client.GUI.custom_classes.ComponentMover;
import be.ehealth.businessconnector.ehbox.api.domain.Addressee;
import be.ehealth.businessconnector.ehbox.api.domain.exception.EhboxBusinessConnectorException;
import be.ehealth.technicalconnector.exception.ConnectorException;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;

import be.ehealth.technicalconnector.utils.IdentifierType;
import java.awt.BorderLayout;
import labr_client.ehealth.e_health_session_manager;
import labr_client.ehealth.e_health_consultation;
import labr_client.ehealth.e_health;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.table.DefaultTableModel;
import labr_client.SQLite.SQLiteQueries;
import labr_client.SQLite.SqLiteSessionManager;
import labr_client.SQLite.SQLiteQueryProcesser;
import labr_client.ehealth.e_health_key_depot;
import labr_client.ehealth.e_health_publication;
import labr_client.kmehrObjects.KmehrLabRequest;
import labr_client.server.lab_server;
import labr_client.Public.PublicVars;
import org.bouncycastle.cms.CMSException;

import javax.swing.WindowConstants;

import labr_client.GUI.custom_classes.PanelGraphics;

import labr_client.xml.Kmehr.KmehrMessage;

import labr_client.xml.ObjToXML;

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
        if (w > screenSize.width) {
            w = screenSize.width;
        }
        int h = Integer.parseInt(PublicVars.getProperties().getProperty("height"));
        if (h > screenSize.height) {
            h = screenSize.height;
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

        jTabbedPane1.setVisible(false);
        //jMenuBar1.setVisible(false);
        //---------------------------------------------------------------
        labRequestPanel = new PanelGraphics(getWidth() - 17, getHeight() - 34);

        this.add(labRequestPanel, BorderLayout.CENTER);
        labRequestPanel.setVisible(true);

        //---------------------------------------------------------------       
        cm = PublicVars.getCM();
        l = new SqLiteSessionManager();
        jLabelUser.setText(PublicVars.getUsername());

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
        return jTextAreaRequest.getText();
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
        List<String> patientInfo = PublicVars.getPatientInformation();
        if (patientInfo.size() > 0) {
            jLabelClickToAddPatient.setFont(new Font(jLabelClickToAddPatient.getFont().getName(), Font.BOLD, 12));
            jLabelClickToAddPatient.setText("<html>" + patientInfo.get(0) + " " + patientInfo.get(1) + "<br>" + patientInfo.get(2) + "<br>" + patientInfo.get(3) + "<br>" + patientInfo.get(4) + "</html>");
        }
    }

    public static void hideAddPatientLabel() {
        jLabelClickToAddPatient.setVisible(false);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelLabRequest = new javax.swing.JPanel();
        jButtonSaveSendRequest = new javax.swing.JButton();
        jButtonSaveRequest = new javax.swing.JButton();
        jButtonPrintRequest = new javax.swing.JButton();
        jScrollPane_LabRequests = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabelClickToAddPatient = new javax.swing.JLabel();
        jPanelSentMessages = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaRequest = new javax.swing.JTextArea();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButtonBoxInfo = new javax.swing.JButton();
        jButtonGetMessages = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabelEIDName = new javax.swing.JLabel();
        jLabelUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelSessionStatus = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelSentToLab = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 32767));

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

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setEnabled(false);
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });
        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyPressed(evt);
            }
        });

        jButtonSaveSendRequest.setText("Save & Send");
        jButtonSaveSendRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveSendRequestActionPerformed(evt);
            }
        });

        jButtonSaveRequest.setText("Save");
        jButtonSaveRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveRequestActionPerformed(evt);
            }
        });

        jButtonPrintRequest.setText("Print");
        jButtonPrintRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintRequestActionPerformed(evt);
            }
        });

        jScrollPane_LabRequests.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelClickToAddPatient.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelClickToAddPatient.setForeground(new java.awt.Color(150, 150, 150));
        jLabelClickToAddPatient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelClickToAddPatient.setText("Click to add patient");
        jLabelClickToAddPatient.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelClickToAddPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelClickToAddPatientMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClickToAddPatient, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClickToAddPatient, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelLabRequestLayout = new javax.swing.GroupLayout(jPanelLabRequest);
        jPanelLabRequest.setLayout(jPanelLabRequestLayout);
        jPanelLabRequestLayout.setHorizontalGroup(
            jPanelLabRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLabRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLabRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_LabRequests, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addGroup(jPanelLabRequestLayout.createSequentialGroup()
                        .addGroup(jPanelLabRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLabRequestLayout.createSequentialGroup()
                                .addComponent(jButtonSaveSendRequest)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSaveRequest)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPrintRequest)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelLabRequestLayout.setVerticalGroup(
            jPanelLabRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLabRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_LabRequests, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLabRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSaveSendRequest)
                    .addComponent(jButtonSaveRequest)
                    .addComponent(jButtonPrintRequest))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Create lab request", jPanelLabRequest);
        jPanelLabRequest.getAccessibleContext().setAccessibleName("");
        jPanelLabRequest.getAccessibleContext().setAccessibleDescription("");

        jPanelSentMessages.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanelSentMessagesFocusGained(evt);
            }
        });
        jPanelSentMessages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSentMessagesMouseClicked(evt);
            }
        });

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Archive selected");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSentMessagesLayout = new javax.swing.GroupLayout(jPanelSentMessages);
        jPanelSentMessages.setLayout(jPanelSentMessagesLayout);
        jPanelSentMessagesLayout.setHorizontalGroup(
            jPanelSentMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSentMessagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(532, Short.MAX_VALUE))
        );
        jPanelSentMessagesLayout.setVerticalGroup(
            jPanelSentMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSentMessagesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSentMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(462, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Saved requests", jPanelSentMessages);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton12.setText("Send request");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTextAreaRequest.setColumns(20);
        jTextAreaRequest.setLineWrap(true);
        jTextAreaRequest.setRows(5);
        jTextAreaRequest.setText("<ns2:GetBoxInfoRequest xmlns:ns2=\"urn:be:fgov:ehealth:ehbox:consultation:protocol:v3\">\n  <BoxId>\n    <Id>83166909</Id>\n    <Type>NIHII</Type>\n    <Quality>LABO</Quality>\n  </BoxId>\n</ns2:GetBoxInfoRequest>");
        jScrollPane1.setViewportView(jTextAreaRequest);

        jTextField7.setText("NIHII-LABO");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setText("83166909");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jTextField9.setText("LABOAZZENO");
        jTextField9.setToolTipText("");

        jLabel7.setText("Type");

        jLabel11.setText("Value");

        jLabel13.setText("Application");

        jButton1.setText("Search all Types");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Search person/organisation");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Send XML Request");

        jButtonBoxInfo.setText("GetBoxInfo");

        jButtonGetMessages.setText("GetMessages");
        jButtonGetMessages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetMessagesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel17)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 365, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonBoxInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonGetMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonBoxInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGetMessages)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addGap(0, 254, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("E-health services", jPanel1);

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
        jToolBar1.add(filler2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Tabpane");

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

    private void jTabbedPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1KeyPressed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained

    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void jButtonGetMessagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetMessagesActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButtonGetMessagesActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            //keyDepot.searchETK(jTextField7.getText(), jTextField8.getText(), jTextField9.getText());
            keyDepot.searchETK(jTextField7.getText(), jTextField8.getText(), jTextField9.getText());
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:.
        keyDepot.getIdentifierTypes(jTextField8.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        send_ehealth_request();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jPanelSentMessagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSentMessagesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSentMessagesMouseClicked

    private void jPanelSentMessagesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanelSentMessagesFocusGained
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSentMessagesFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // loadSentMessages();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabelClickToAddPatientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClickToAddPatientMousePressed
        // TODO add your handling code here:
//        patientInfoDialog.setInfo();
        //  patientInfoDialog.setVisible(true);
    }//GEN-LAST:event_jLabelClickToAddPatientMousePressed

    private void jButtonPrintRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintRequestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonPrintRequestActionPerformed

    private void jButtonSaveRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveRequestActionPerformed
        // TODO add your handling code here:
        String xml = createLabRequest();
        check_LabRequest(xml);
    }//GEN-LAST:event_jButtonSaveRequestActionPerformed

    private void jButtonSaveSendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveSendRequestActionPerformed
        // TODO add your handling code here:
        String xml = createLabRequest();
        check_LabRequest(xml);

        send_LabRequest(xml);
    }//GEN-LAST:event_jButtonSaveSendRequestActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonBoxInfo;
    private javax.swing.JButton jButtonGetMessages;
    private javax.swing.JButton jButtonPrintRequest;
    private javax.swing.JButton jButtonSaveRequest;
    private javax.swing.JButton jButtonSaveSendRequest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private static javax.swing.JLabel jLabelClickToAddPatient;
    private javax.swing.JLabel jLabelEIDName;
    private javax.swing.JLabel jLabelSentToLab;
    private static javax.swing.JLabel jLabelSessionStatus;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItemAddLabel;
    private javax.swing.JMenuItem jMenuItemDeleteLabel;
    private javax.swing.JMenuItem jMenuItemEditLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanelLabRequest;
    private javax.swing.JPanel jPanelSentMessages;
    public static javax.swing.JPopupMenu jPopupMenuLabel;
    public static javax.swing.JPopupMenu jPopupMenuRequestOptions;
    public static javax.swing.JPopupMenu jPopupMenuRequestPanelOptions;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JScrollPane jScrollPane_LabRequests;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaRequest;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    public static javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    //-------------------------------------------------------------------
}
