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

import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.decode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.swing.JMenuBar;
import static labr_client.GUI.custom_classes.Dynamic_swing.questionBox;
import labr_client.GUI.forms.MainWindow;
import static labr_client.GUI.forms.MainWindow.deleteProfile;
import static labr_client.GUI.forms.MainWindow.ehealth_start_session;
import static labr_client.GUI.forms.MainWindow.optionWindow;
import static labr_client.GUI.forms.MainWindow.queries;
import static labr_client.Public.PublicVars.getUserData;
import static labr_client.Public.PublicVars.setIsEdit;
import static labr_client.Public.PublicVars.setProfielID;
import static labr_client.Public.PublicVars.setProfielNamen;
import static labr_client.xml.ObjToXML.saveProfile;
import static labr_client.xml.XMLToObj.loadProfile;

/**
 *
 * @author De Mey Matthias
 */
public class CustomJMenuBar extends JMenuBar {

    Color bgColor;

    private CustomJMenuItem jMenuItemNewProfile;
    private CustomJMenuItem jMenuItemEhealth;
    private CustomJMenuItem jMenuItemDeleteProfile;
    private CustomJMenuItem jMenuItemExitApplication;
    private CustomJMenuItem jMenuItemOptions;
    private CustomJMenuItem jMenuItemRenameProfile;
    private CustomJMenuItem jMenuItemSaveCurrentProfile;
    private CustomJCheckBoxMenuItem jCheckBoxMenuItemEditProfile;

    private CustomJMenu jMenuProfile;
    private CustomJMenu jMenuKL;
    private CustomJMenu jMenuProfiles;
    private CustomJMenu jMenuLabR;
    private CustomJMenu jMenuConnect;
    private CustomJMenu jMenuOutbox;
    private LabRequestPanel labRequestPanel;
    private PanelGraphics PG;

    public CustomJMenuBar(Color c, LabRequestPanel lrp, PanelGraphics frame) {
        bgColor = c;
        labRequestPanel = lrp;
        PG = frame;
        initMenu();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public void initMenu() {

        //---------------------------------------------------------------------------------
        jMenuKL = new CustomJMenu(BLACK, "Labr", 100, 32, 10, 40);
        jMenuLabR = new CustomJMenu(decode("#DDB300"), "Order entry", 100, 16);
        jMenuItemOptions = new CustomJMenuItem(BLACK, "Options", 100, 12);
        jMenuItemEhealth = new CustomJMenuItem(BLACK, "Ehealth", 100, 12);
        jMenuItemExitApplication = new CustomJMenuItem(BLACK, "Exit", 100, 12);
        //---------------------------------------------------------------------------------               
        jMenuConnect = new CustomJMenu(decode("#A41931"), "Connect", 100, 16);
        //---------------------------------------------------------------------------------  
        jMenuProfile = new CustomJMenu(decode("#DDB300"), "Profile > ", 100, 12, 35);
        jMenuProfiles = new CustomJMenu(decode("#DDB300"), "Profiles >", 100, 12, 35);
        jMenuItemSaveCurrentProfile = new CustomJMenuItem(decode("#DDB300"), "Save profile", 100, 12);
        jMenuItemRenameProfile = new CustomJMenuItem(decode("#DDB300"), "Rename profile", 100, 12);
        jMenuItemDeleteProfile = new CustomJMenuItem(decode("#DDB300"), "Delete profile", 100, 12);
        jMenuItemNewProfile = new CustomJMenuItem(decode("#DDB300"), "New profile", 100, 12);
        jCheckBoxMenuItemEditProfile = new CustomJCheckBoxMenuItem(decode("#DDB300"), "Edit profile", 100, 12);
        //---------------------------------------------------------------------------------  
        jMenuOutbox = new CustomJMenu(new Color(50, 138, 171), "Database", 100, 16);
        jMenuKL.setText("Labr");
        jMenuKL.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                PG.setLrpVisible();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        jMenuItemOptions.setText("Options");
        jMenuItemOptions.addActionListener(this::jMenuItemOptionsActionPerformed);
        jMenuKL.add(jMenuItemOptions);

        jMenuItemEhealth.setText("E-health");
        jMenuItemEhealth.addActionListener(this::jMenuItemEhealthActionPerformed);
        jMenuKL.add(jMenuItemEhealth);

        jMenuItemExitApplication.setText("Exit");
        jMenuItemExitApplication.addActionListener(this::jMenuItemExitApplicationActionPerformed);
        jMenuKL.add(jMenuItemExitApplication);
        this.add(jMenuKL);

        //------------------------------------------------
        jMenuProfile.setText("Profile > ");
        jMenuProfile.addActionListener((java.awt.event.ActionEvent evt) -> {
            PG.setLrpVisible();
        });
        jMenuProfiles.setText("Profiles >");
        jMenuProfile.add(jMenuProfiles);
        loadProfiles();
        jMenuItemSaveCurrentProfile.setText("Save profile");
        jMenuItemSaveCurrentProfile.addActionListener(this::jMenuItemSaveCurrentProfileActionPerformed);
        jMenuProfile.add(jMenuItemSaveCurrentProfile);
        jMenuItemRenameProfile.setText("Rename profile");
        jMenuItemRenameProfile.addActionListener(this::jMenuItemRenameProfileActionPerformed);
        jMenuProfile.add(jMenuItemRenameProfile);
        jMenuItemDeleteProfile.setText("Delete profile");
        jMenuItemDeleteProfile.addActionListener(this::jMenuItemDeleteProfileActionPerformed);
        jMenuProfile.add(jMenuItemDeleteProfile);
        jMenuItemNewProfile.setText("New Profile");
        jMenuItemNewProfile.addActionListener(this::jMenuItemNewProfileActionPerformed);
        jMenuProfile.add(jMenuItemNewProfile);
        jCheckBoxMenuItemEditProfile.setText("Edit Profile");
        jCheckBoxMenuItemEditProfile.addActionListener(this::jCheckBoxMenuItemEditProfileActionPerformed);
        jMenuProfile.add(jCheckBoxMenuItemEditProfile);

        jMenuLabR.add(jMenuProfile);
        jMenuLabR.setText("Order entry");
        jMenuLabR.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                PG.setLrpVisible();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.add(jMenuLabR);

        //------------------------------------------------
        jMenuOutbox.setText("Database");
        jMenuOutbox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                PG.setOutboxVisible();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        this.add(jMenuOutbox);
        //------------------------------------------------
        //setJMenuBar(this);
        this.getAccessibleContext().setAccessibleName("");
        this.getAccessibleContext().setAccessibleDescription("");
        this.getAccessibleContext().setAccessibleParent(this);

    }

    private void loadProfiles() {
//        List<String[]> lines = queries.loadProfiles();
//        for (String[] values : lines) {
//            CustomJMenuItem menuItem = new CustomJMenuItem(new Color(0, 131, 117), values[1], 100, 12);
//            menuItem.setMnemonic(Integer.parseInt(values[0]));
//            menuItem.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    PublicVars.setProfielID(values[0]);
//                    labRequestPanel.loadLabRequestPanel();
//                    labRequestPanel.loadLabels();
//                }
//            });
//            jMenuProfiles.add(menuItem);
//        }
        List<String> results = new ArrayList<>();

        File[] files = new File(getUserData()[9]).listFiles();
//If this pathname does not denote a directory, then listFiles() returns null. 

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName().replace(".xml", "");
                CustomJMenuItem menuItem = new CustomJMenuItem(decode("#DDB300"), fileName, 100, 12);
                menuItem.setName(fileName);
                menuItem.addActionListener((java.awt.event.ActionEvent evt) -> {
                    setProfielID(fileName);
                    //                        labRequestPanel.loadLabRequestPanel();
//                        labRequestPanel.loadLabels();
labRequestPanel.removeAll();
labRequestPanel.loadRequestOrProfile(loadProfile(((CustomJMenuItem) evt.getSource()).getName()));
                });
                jMenuProfiles.add(menuItem);
                results.add(file.getName());
            }
        }

    }

    private void refreshProfiles() {
        jMenuProfiles.removeAll();
        loadProfiles();
    }

    public void renameProfile() {
        String profielNaam = questionBox("Please input new name of profile: ");
        if (!profielNaam.isEmpty()) {
            // isRemovingItem = true;
            queries.updateProfileName(profielNaam);
            refreshProfiles();
            // isRemovingItem = false;

        }
    }

    private void addProfiles() {

        List<String[]> lines = queries.loadProfiles();
        setProfielNamen(lines);

    }

    private void jMenuItemRenameProfileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        renameProfile();

    }

    private void jMenuItemOptionsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        optionWindow.setVisible(true);

    }

    private void jMenuItemDeleteProfileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        deleteProfile();

    }

    private void jMenuItemNewProfileActionPerformed(java.awt.event.ActionEvent evt) {

        //insert into profielen --> nieuw profiel
        //loadlabels
        //loadpanel
    }

    private void jCheckBoxMenuItemEditProfileActionPerformed(java.awt.event.ActionEvent evt) {

        if (((CustomJCheckBoxMenuItem) evt.getSource()).isSelected()) {
            setIsEdit(TRUE);
            labRequestPanel.registerComponentMove();
        } else {
            setIsEdit(FALSE);
            labRequestPanel.unregisterComponentMove();
        }

    }

    private void jMenuItemExitApplicationActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //e_health_session_manager.close_session();
        exit(0);
    }

    private void jMenuItemEhealthActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // TODO add your handling code here:
            ehealth_start_session();

        } catch (TechnicalConnectorException ex) {
            getLogger(MainWindow.class.getName()).log(SEVERE, null, ex);
        }
    }

    private void jMenuItemSaveCurrentProfileActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        saveProfile(labRequestPanel.getComponents(), "default");
        labRequestPanel.removeAll();
        labRequestPanel.loadRequestOrProfile(loadProfile("default"));
//        labRequestPanel.updateRequestPositions();
//        labRequestPanel.updateLabelPositions();
//        labRequestPanel.loadLabRequestPanel();
//        labRequestPanel.loadLabels();
    }

}
