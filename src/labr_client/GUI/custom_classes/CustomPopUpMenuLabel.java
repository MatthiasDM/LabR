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
 * @author De Mey Matthias
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
