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

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import labr_client.xml.XmlParser;
import org.xml.sax.SAXException;

/**
 *
 * @author familie
 */
public class LabRequestViewer extends javax.swing.JDialog {

    /**
     * Creates new form LabRequestViewer
     */
    public RequestPanel req;
    private static String patientFirstName, patientFamilyName;
    private static List<String> requests;

    public LabRequestViewer(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setSize(450, 450);
        setPreferredSize(new Dimension(450, 450));
        this.setLocationRelativeTo(null);
        initComponents();
        req = new RequestPanel();
        this.add(req);

    }

    public void SetRequest(String xml) {
        List<String> results = new ArrayList<String>();

        try {
            XmlParser xmlParser = new XmlParser(xml);
            results = xmlParser.searchDocumentForValues("/kmehrmessage/folder/patient/firstname");
            setPatientFirstName(results.get(0));
            results = xmlParser.searchDocumentForValues("/kmehrmessage/folder/patient/familyname");
            setPatientFamilyName(results.get(0));
            results = xmlParser.searchDocumentForValues("/kmehrmessage/folder/transaction/item/content/cd");
            setRequests(results);

            req.repaint();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LabRequestViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LabRequestViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LabRequestViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(LabRequestViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(true);

    }

    public static List<String> getRequests() {
        return requests;
    }

    public static void setRequests(List<String> requests) {
        LabRequestViewer.requests = requests;
        for(String request: requests){
        
        }
    }

    public static String getPatientFirstName() {
        return patientFirstName;
    }

    public static void setPatientFirstName(String patientFirstName) {
        LabRequestViewer.patientFirstName = patientFirstName;
    }

    public static String getPatientFamilyName() {
        return patientFamilyName;
    }

    public static void setPatientFamilyName(String patientFamilyName) {
        LabRequestViewer.patientFamilyName = patientFamilyName;
    }

    class RequestPanel extends JPanel {

        private List<String> patient = new ArrayList<String>();
        private List<String> sender = new ArrayList<String>();
        private List<String> receiver = new ArrayList<String>();

        RequestPanel() {
            // set a preferred size for the custom panel.
            setSize(420, 420);
            setVisible(true);
            setPreferredSize(new Dimension(420, 420));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Patient: " + getPatientFamilyName() + " " + getPatientFirstName(), 30, 30);
            g.drawString("Lab requests:", 30, 50);

            int x = 30, y = 70;
            for (String req : getRequests()) {
                g.drawString(req, x, y);
                y += 20;
                if (y > 330) {
                    y = 70;
                    x += 100;
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Resend");

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(195, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LabRequestViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LabRequestViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LabRequestViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LabRequestViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LabRequestViewer dialog = new LabRequestViewer(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}
