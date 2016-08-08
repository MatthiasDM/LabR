/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import labr_client.GUI.forms.AddNewRequests;
import labr_client.GUI.forms.LabelForm;
import labr_client.Public.PublicVars;


/**
 *
 * @author labbl
 */
public class CustomPopUpMenuLabel extends JPopupMenu {

    public javax.swing.JPopupMenu jPopupMenuRequestPanelOptions;
    private javax.swing.JMenuItem jMenuItemEditLabel;
    private javax.swing.JMenuItem jMenuItemDeleteLabel;
    private Point p;
    private LabRequestPanel lrp;
    private JLabel lbl;
    public CustomPopUpMenuLabel(Point pp, MouseEvent e, LabRequestPanel _lrp) {
       // jPopupMenuRequestPanelOptions = new javax.swing.JPopupMenu();
        jMenuItemEditLabel = new javax.swing.JMenuItem();
        jMenuItemDeleteLabel = new javax.swing.JMenuItem();
        lrp = _lrp;
        p = e.getPoint();  
        lbl = (JLabel) e.getSource();
        addMenuItems();
        this.add(jMenuItemEditLabel);
        this.add(jMenuItemDeleteLabel);

    }
    
    public void addMenuItems(){
    
      jMenuItemEditLabel.setText("Edit label");
        jMenuItemEditLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditLabelActionPerformed(evt);
            }
        });
        
        jMenuItemDeleteLabel.setText("Delete label");
        jMenuItemDeleteLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteLabelActionPerformed(evt);
            }
        });
        
    }
    
    private void jMenuItemEditLabelActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        LabelForm labelForm = new LabelForm(true, true, lrp, lbl);
    }  
    
    private void jMenuItemDeleteLabelActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        lrp.DeleteLabel(lbl);
        
    }   
}
