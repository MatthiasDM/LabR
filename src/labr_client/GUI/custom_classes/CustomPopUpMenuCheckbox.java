/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;
import labr_client.GUI.forms.AddNewRequests;
import labr_client.GUI.forms.LabelForm;
import labr_client.GUI.forms.MainWindow;
import labr_client.Public.PublicVars;


/**
 *
 * @author labbl
 */
public class CustomPopUpMenuCheckbox extends JPopupMenu {

    public javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JMenuItem jMenuItemDeleteItem;
    private Point p;
    private LabRequestPanel lrp;
    public CustomPopUpMenuCheckbox(Point pp, MouseEvent e, LabRequestPanel _lrp) {
       // jPopupMenuRequestPanelOptions = new javax.swing.JPopupMenu();
        jMenuItemDeleteItem = new javax.swing.JMenuItem();
        p = e.getPoint();
        lrp = _lrp;
        addMenuItems();
        this.add(jMenuItemDeleteItem);

    }
    
    public void addMenuItems(){
    
      jMenuItemDeleteItem.setText("Delete item");
        jMenuItemDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteItemActionPerformed(evt);
            }
        });   
        
    }
    
    private void jMenuItemDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        JCheckBox target = (JCheckBox) PublicVars.getSelectComponentOnLabRequestPanel();
        //MainWindow.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
        //Dynamic_swing.infoBox(target.getName(), target.getText());
        lrp.remove(target);
        lrp.revalidate();
        lrp.repaint();
    } 
    

}
