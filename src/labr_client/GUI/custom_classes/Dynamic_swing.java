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

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import labr_client.GUI.forms.MainWindow;
import static labr_client.GUI.forms.MainWindow.queries;
import labr_client.SQLite.SQLiteQueries;
import labr_client.Public.PublicVars;

/**
 *
 * @author De Mey Matthias
 */
public class Dynamic_swing {

    static JCheckBox ckbx;
    static JLabel lbl;
    JButton btn;

    private int gridSizeX = 60;
    private int gridSizeY = 60;
    private int snapThreshold = 10;

//    public void doPopIndividualRequest(MouseEvent e) {
//        MainWindow.jPopupMenuRequestOptions.show(e.getComponent(), e.getX(), e.getY());
//
//    }
//
//    public void doPopLabel(MouseEvent e) {
//        MainWindow.jPopupMenuLabel.show(e.getComponent(), e.getX(), e.getY());
//
//    }
//
//    public void doPopRequestPanel(MouseEvent e) {
//
//        MainWindow.jPopupMenuRequestPanelOptions.show(e.getComponent(), e.getX(), e.getY());
//
//    }
//
//    public JCheckBox getCkbx(int x, int y, String txt, String name, Boolean selected, Integer id) {
//        ckbx = new CustomJCheckBox();
//        ckbx.setLocation(x, y);
//        ckbx.setText(txt);
//        ckbx.setName(name);
//        ckbx.setSelected(selected);
//        ckbx.setBackground(Color.white);
//        ckbx.setMnemonic(id);
//        dynamicComponentSize(ckbx, txt);
//        ckbx = addCheckBoxListeners(ckbx);
//        return ckbx;
//    }
//
//    public JCheckBox addCheckBoxListeners(JCheckBox j) {
//        j.addMouseListener(
//                new MouseAdapter() {
//            private Color background;
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
//                    doPopIndividualRequest(e);
//                }
//                if (e.getButton() == MouseEvent.BUTTON1) {
//                    if (j.isSelected()) {
//                        j.setSelected(false);
//                    } else {
//                        j.setSelected(true);
//                    }
//
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
//                    doPopIndividualRequest(e);
//                }
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
////                        if (e.getButton() == 3 && PublicVars.isEditRequestForm()) {
////                            JCheckBox target = (JCheckBox) e.getSource();
////                            GUI_interface.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
////                            //Dynamic_swing.infoBox(target.getName(), target.getText());
////                            GUI_interface.jPanel_aanvraagformulier.remove(target);
////                            GUI_interface.jPanel_aanvraagformulier.revalidate();
////                            GUI_interface.jPanel_aanvraagformulier.repaint();
////                        }
//            }
//
//        });
//        return j;
//    }

//    public JLabel getLbl(int x, int y, String txt, int letterGrootte, String kleur) {
//        lbl = new CustomJLabel();
//        lbl.setLocation(x, y);
//        lbl.setText(txt);
//        lbl.setFont(new java.awt.Font("Tahoma", 1, letterGrootte));
//        lbl.setForeground(Color.decode(kleur));
//        dynamicComponentSize(lbl, txt);
//        lbl = addLabelisteners(lbl);
//
//        return lbl;
//    }

//    public JLabel addLabelisteners(JLabel j) {
//        j.addMouseListener(
//                new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                JLabel currentLbl = (JLabel) e.getComponent();
//                List<String[]> Labels = queries.selectLabel(currentLbl.getText());
//                if (Labels.size() > 0) {
//                    PublicVars.setCurrentGroupLabel(Labels.get(0)[0]);
//                    if (e.isPopupTrigger()) {
//                        PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
//                        PublicVars.setPopUpClick(e.getLocationOnScreen());
//                        doPopLabel(e);
//                    } else {
//                        MainWindow.ClickRequests();
//                    }
//                }
//
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
////                        if (e.getButton() == 3 && PublicVars.isEditRequestForm()) {
////                            JCheckBox target = (JCheckBox) e.getSource();
////                            GUI_interface.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
////                            //Dynamic_swing.infoBox(target.getName(), target.getText());
////                            GUI_interface.jPanel_aanvraagformulier.remove(target);
////                            GUI_interface.jPanel_aanvraagformulier.revalidate();
////                            GUI_interface.jPanel_aanvraagformulier.repaint();
////                        }
//            }
//
//        });
//        return j;
//    }

    public JButton getBtn(int x, int y, String txt) {
        btn = new JButton();
        btn.setText(txt);
        dynamicComponentSize(btn, txt);
        btn.setLocation(x, y);
        btn.setText(txt);
        return btn;
    }

    public static void dynamicComponentSize(JComponent cmp, String txt) {
        FontMetrics metrics = cmp.getFontMetrics(cmp.getFont());
        int width = metrics.stringWidth(txt);
        int height = metrics.getHeight();
        Dimension newDimension = new Dimension(width + 40, height + 10);
        cmp.setPreferredSize(newDimension);
        cmp.setBounds(new Rectangle(cmp.getLocation(), cmp.getPreferredSize()));

    }

    public static int infoBox(String infoMessage, String titleBar) {
        return JOptionPane.showConfirmDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.OK_CANCEL_OPTION);
    }

    public static String questionBox(String question) {
        return JOptionPane.showInputDialog(question);
    }

    public List<String> getSelectedCheckboxLabTest(Component[] comps) {
        List<String> requests = new ArrayList<String>();

        for (Component comp : comps) {
            Class<? extends Component> c = comp.getClass();

            if (c.getSimpleName().equals("JCheckBox")) {
                JCheckBox chbx = (JCheckBox) comp;
                if (chbx.isSelected()) {
                    //infoBox(chbx.getName(), "Selected CheckBox");
                    requests.add(chbx.getName());
                }

            };

        }
        return requests;
    }

}
