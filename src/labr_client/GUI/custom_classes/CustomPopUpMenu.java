/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import labr_client.GUI.forms.AddNewRequests;
import labr_client.GUI.forms.LabelForm;
import labr_client.Public.PublicVars;


/**
 *
 * @author labbl
 */
public class CustomPopUpMenu extends JPopupMenu {

    public javax.swing.JPopupMenu jPopupMenuRequestPanelOptions;
    private javax.swing.JMenuItem jMenuItemAddLabel;
    private javax.swing.JMenuItem jMenuItemAddItems;
    private javax.swing.JMenuItem jMenuEditAttributes;
    private Point p;
    private LabRequestPanel parentPanel;
    public CustomPopUpMenu(Point pp, MouseEvent e) {
       // jPopupMenuRequestPanelOptions = new javax.swing.JPopupMenu();
        jMenuItemAddLabel = new javax.swing.JMenuItem();
        jMenuItemAddItems = new javax.swing.JMenuItem();
        jMenuEditAttributes = new javax.swing.JMenuItem();
        p = e.getPoint();
        parentPanel = (LabRequestPanel) e.getSource();
        addMenuItems();
        this.add(jMenuItemAddLabel);
        this.add(jMenuItemAddItems);
        this.add(jMenuEditAttributes);
    }
    
    public void addMenuItems(){
    
      jMenuItemAddLabel.setText("Add label");
        jMenuItemAddLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddLabelActionPerformed(evt);
            }
        });
        
        jMenuItemAddItems.setText("Add items");
        jMenuItemAddItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddItemsActionPerformed(evt);
            }
        });
        jMenuEditAttributes.setText("Edit attributes");
        jMenuEditAttributes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEditAttributesActionPerformed(evt);
            }
        });
        
    }
    
    private void jMenuItemAddLabelActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        LabelForm labelForm = new LabelForm(true, p, parentPanel);
    }  
    
    private void jMenuItemAddItemsActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        AddNewRequests addNewRequestsInstance = new AddNewRequests();
        
    }   
    private void jMenuEditAttributesActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        CustomJFrame cjf = new CustomJFrame(640,480);
        
        
    }   
}
