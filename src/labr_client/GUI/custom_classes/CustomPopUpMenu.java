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
import javax.swing.JPopupMenu;
import labr_client.GUI.forms.AddNewRequests;
import labr_client.GUI.forms.LabelForm;


/**
 *
 * @author De Mey Matthias
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
        jMenuItemAddLabel.addActionListener(this::jMenuItemAddLabelActionPerformed);
        
        jMenuItemAddItems.setText("Add items");
        jMenuItemAddItems.addActionListener(this::jMenuItemAddItemsActionPerformed);
        jMenuEditAttributes.setText("Edit attributes");
        jMenuEditAttributes.addActionListener(this::jMenuEditAttributesActionPerformed);
        
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
