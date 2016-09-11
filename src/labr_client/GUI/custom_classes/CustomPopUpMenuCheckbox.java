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
import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;
import static labr_client.Public.PublicVars.getSelectComponentOnLabRequestPanel;


/**
 *
 * @author De Mey Matthias
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
        jMenuItemDeleteItem.addActionListener(this::jMenuItemDeleteItemActionPerformed);   
        
    }
    
    private void jMenuItemDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        JCheckBox target = (JCheckBox) getSelectComponentOnLabRequestPanel();
        //MainWindow.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
        //Dynamic_swing.infoBox(target.getName(), target.getText());
        lrp.remove(target);
        lrp.revalidate();
        lrp.repaint();
    } 
    

}
