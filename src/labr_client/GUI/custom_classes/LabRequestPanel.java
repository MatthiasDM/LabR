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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import labr_client.GUI.forms.AddNewRequests;
import labr_client.GUI.forms.MainWindow;
import static labr_client.GUI.forms.MainWindow.cm;

import labr_client.GUI.forms.PatientInfo;

import labr_client.Public.PublicFunctions;

import labr_client.Public.PublicVars;
import labr_client.xml.LabrRequest;
import labr_client.xml.LabrXMLLabel;
import labr_client.xml.LabrXMLRequest;
import labr_client.xml.ObjToXML;
import labr_client.xml.XMLToObj;

/**
 *
 * @author De Mey Matthias
 */
public final class LabRequestPanel extends JPanel implements DropTargetListener {

    Boolean drawSelectionBox = false;
    LabrRequest currentRequest;
PanelGraphics pg;
    public LabRequestPanel(PanelGraphics p) {

        DropTarget dropTarget = new DropTarget(this, this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDown = true;
                startMousePostion = e.getPoint();
                e.consume();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPopRequestPanel(e);
                    PublicVars.setPopUpClick(e.getPoint());
                }

                selectComponentsInSquare(startMousePostion, currentMousePostion);

                bbg.setColor(c);

                JPanel parent = (JPanel) e.getSource();
                bbg.fillRect(0, 0, parent.getWidth(), parent.getHeight());

                drawSelectionBox = false;
                repaint();
                e.consume();
                startMousePostion = null;
                currentMousePostion = null;
                e.consume();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                deselectComponents();
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                currentMousePostion = e.getPoint();
                JPanel parent = (JPanel) e.getSource();
                int x = e.getX();
                int y = e.getY();
                bbg.setColor(c);
                bbg.fillRect(0, 0, parent.getWidth(), parent.getHeight());

                drawSelectionBox = true;
                bbg.setColor(c);
                repaint();
                if (r != null) {
                    bbg.fillRect(300, 370, 100, 100);
                    bbg.fillRect(200, 370, 100, 100);
                    bbg.setColor(Color.BLACK);
                    bbg.drawString("X: " + r.x + "Y: " + r.y, 300, 400);
                    bbg.drawString("X: " + currentMousePostion.x + "Y: " + currentMousePostion.y, 200, 400);
                    bbg.setColor(c);
                }
                e.consume();

//                Point p1 = new Point(startMousePostion.x, startMousePostion.y);
//                Point p2 = new Point(currentMousePostion.x, currentMousePostion.y);
//                selectIntersectingComponents(p1, p2);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentMousePostion = e.getPoint();
                repaint();
                bbg.fillRect(200, 370, 100, 100);
                bbg.setColor(Color.BLACK);
                bbg.drawString("X: " + currentMousePostion.x + "Y: " + currentMousePostion.y, 200, 400);
                bbg.setColor(c);
                e.consume();
            }

        });
        this.setLayout(null);
        height = this.getHeight();
        width = this.getHeight();
        pg = p;
        c = Color.white;
        javax.swing.GroupLayout groupLayout = new javax.swing.GroupLayout(this);
        this.setLayout(groupLayout);
        File[] files = new File(PublicVars.getUserData()[9]).listFiles();
        String fileName = files[0].getName().replace(".xml", "");
        loadRequestOrProfile(XMLToObj.loadProfile(fileName));

    }

    //START--------GRAPHIC RELATED VARIABLES----------------
    Point currentMousePostion, startMousePostion, buffer;
    Graphics backG;
    Graphics2D g2, bbg, g3;
    BufferedImage backBuffer;
    boolean mouseDown = false;
    int width, height;
    Color c;
    Rectangle r;
    //END----------------------------------------------------

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        height = this.getHeight();
        width = this.getWidth();
        c = Color.white;
        if (backBuffer == null) {
            height = this.getHeight();
            width = this.getHeight();
            backBuffer = (BufferedImage) createImage(2000, 2000);
            bbg = (Graphics2D) backBuffer.createGraphics();
            bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            bbg.setColor(c);
            bbg.fillRect(0, 0, 2000, 2000);
            bbg.setComposite(AlphaComposite.SrcOver.derive(0.8f));
        }
        g2.drawImage(backBuffer, 0, 0, this);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g3 = (Graphics2D) g;
        //g3 = (Graphics2D) this.getGraphics();
        g3.setColor(Color.GRAY);
        if (startMousePostion != null && currentMousePostion != null) {
            int[] pointsX = {startMousePostion.x, startMousePostion.x, currentMousePostion.x, currentMousePostion.x, startMousePostion.x};
            int[] pointsY = {startMousePostion.y, currentMousePostion.y, currentMousePostion.y, startMousePostion.y, startMousePostion.y};
            g3.drawPolyline(pointsX, pointsY, 5);
        }
    }

    public Graphics componentPaintGraphics(Component comp, Rectangle r) {

        bbg = (Graphics2D) backBuffer.createGraphics();
        height = comp.getHeight();
        width = comp.getWidth();
        // backBuffer = (BufferedImage) createImage(width, height);
        //bbg.drawRect(r.x, r.y, r.width, r.height);
        return bbg;
    }

//    public void selectIntersectingComponents(Point point1, Point point2) {
//
//        Point buffer = new Point();
//        if (point1.x > point2.x) {
//            buffer.x = point2.x;
//            point2.x = point1.x;
//            point1.x = buffer.x;
//        }
//        if (point1.y > point2.y) {
//            buffer.y = point2.y;
//            point2.y = point1.y;
//            point1.y = buffer.y;
//        }
//
//        Component[] comps = this.getComponents();
//        JPanel parent = (JPanel) this;
//        Rectangle selectionBoxRectangle = new Rectangle(point1.x, point1.y, point2.x - point1.x, point2.y - point1.y);
//        for (Component comp : comps) {
//
//            Rectangle compRectangle = new Rectangle(comp.getLocation(), comp.getSize());
//            Class<? extends Component> c = comp.getClass();
//            if (c.getSimpleName().equals("JLabel") || c.getSimpleName().equals("JCheckBox")) {
//
//                if (compRectangle.intersects(selectionBoxRectangle)) {
//                    r = compRectangle.intersection(selectionBoxRectangle);
//                    if (c.getSimpleName().equals("JLabel")) {
//                        JLabel compo = (JLabel) comp;
//                        //compo.setR(r);
//
//                    }
//                    if (c.getSimpleName().equals("JCheckBox")) {
//                        JCheckBox compo = (JCheckBox) comp;
//                        //compo.setR(r);
//                    }
//                    //r.setBounds(point1.x, point1.y, Math.abs(point2.x - point1.x), Math.abs(point2.y - point1.y));
//                } else if (!compRectangle.contains(currentMousePostion)) {
//                    if (c.getSimpleName().equals("JLabel")) {
//                        JLabel compo = (JLabel) comp;
//                        //compo.setR(null);
//
//                    }
//                    if (c.getSimpleName().equals("JCheckBox")) {
//                        JCheckBox compo = (JCheckBox) comp;
//                        //compo.setR(null);
//                    }
//                }
//
//            }
//
//        }
//    }
    public void drop(DropTargetDropEvent dtde) {
        Point p = dtde.getLocation();
        int selectedRowIndex = AddNewRequests.jTableAddRequestTable.getSelectedRow();
        int selectedColumnIndex = AddNewRequests.jTableAddRequestTable.getSelectedColumn();
        String testName = (String) AddNewRequests.jTableAddRequestTable.getModel().getValueAt(selectedRowIndex, 2);
        String testLoincCode = (String) AddNewRequests.jTableAddRequestTable.getModel().getValueAt(selectedRowIndex, 1);
        String testID = (String) AddNewRequests.jTableAddRequestTable.getModel().getValueAt(selectedRowIndex, 0);
        int x = (p.x / 10);
        int y = (p.y / 10);
        JCheckBox ckbx = getCkbx(x * 10, y * 10, testName, testLoincCode, Boolean.TRUE, Integer.parseInt(testID));
        ckbx.setMnemonic(Integer.parseInt(testID));
        //MainWindow.queries.insertProfileRequest(PublicVars.getProfielID(), Integer.parseInt(testID), (x * 10), (y * 10), 1, Integer.parseInt(PublicVars.getUserID()));
        this.add(ckbx);
        MainWindow.cm.registerComponent(ckbx);

//        Component[] c = {ckbx};
//        GUI_interface.queries.updateRequestProfilePositions(c);
    }

    public void loadRequestOrProfile(LabrRequest request) {

        currentRequest = request;
        //------------------------------------------------------------------------
        JTextField jtxt = getTextField(request.patient.name.getX(), request.patient.name.getY(), request.patient.name.getValue(), "name", request.patient.name.getWidth());
        this.add(jtxt);
        jtxt = (JTextField) addShortCutListener(jtxt, 99);
        //------------------------------------------------------------------------
        jtxt = getTextField(request.patient.firstName.getX(), request.patient.firstName.getY(), request.patient.firstName.getValue(), "firstName", request.patient.firstName.getWidth());
        this.add(jtxt);
        //------------------------------------------------------------------------
        JFormattedTextField jftxt = getFormattedField(request.patient.birthDate.getX(), request.patient.birthDate.getY(), request.patient.birthDate.getValue(), "birthDate", request.patient.birthDate.getWidth());
        this.add(jftxt);
        //------------------------------------------------------------------------
        JComboBox jcombo = getComboBox(request.patient.gender.getX(), request.patient.gender.getY(), new String[]{"Female", "Male"}, "Male", "gender");
        this.add(jcombo);
        //------------------------------------------------------------------------
        jtxt = getTextField(request.patient.straatAndNumber.getX(), request.patient.straatAndNumber.getY(), request.patient.straatAndNumber.getValue(), "straatAndNumber", request.patient.straatAndNumber.getWidth());
        this.add(jtxt);
        //------------------------------------------------------------------------
        jtxt = getTextField(request.patient.zip.getX(), request.patient.zip.getY(), request.patient.zip.getValue(), "zip", request.patient.zip.getWidth());
        this.add(jtxt);
        //------------------------------------------------------------------------
        jtxt = getTextField(request.patient.city.getX(), request.patient.city.getY(), request.patient.city.getValue(), "city", request.patient.city.getWidth());
        this.add(jtxt);
        //------------------------------------------------------------------------
        jcombo = getComboBox(request.patient.country.getX(), request.patient.country.getY(), new String[]{"Belgie", "Nederland"}, "Belgie", "country");
        this.add(jcombo);
        //------------------------------------------------------------------------
        jtxt = getTextField(request.patient.nationalNumber.getX(), request.patient.nationalNumber.getY(), request.patient.nationalNumber.getValue(), "nationalNumber", request.patient.city.getWidth());
        this.add(jtxt);
        //------------------------------------------------------------------------

        // JButton jbut = getButton(10, 20, request.buttons.save.getValue());
        JButton jbut;
        try {
            if (request.buttons.save.getIcon() != null) {
                BufferedImage bfImg = ImageIO.read(new File(request.buttons.save.getIcon()));
                ImageIcon icon = new ImageIcon(bfImg);
                icon.setDescription(request.buttons.save.getIcon());
                jbut = getButton(request.buttons.save.getX(), request.buttons.save.getY(), icon, "Save");
            } else {
                jbut = getButton(request.buttons.save.getX(), request.buttons.save.getY(), "Save");
            }
            this.add(jbut);
        } catch (IOException ex) {
            Logger.getLogger(LabRequestPanel.class.getName()).log(Level.SEVERE, null, ex);

        }
        //;

        for (LabrXMLLabel label : request.labels.getLabel()) {
            JLabel lbl = getLbl(label.getX(), label.getY(), label.getValue(), label.getSize(), label.getColor());
            this.add(lbl);
        }
        for (LabrXMLRequest req : request.requests.getRequest()) {
            JCheckBox ckbx = getCkbx(req.getX(), req.getY(), req.getValue(), req.getLoinc(), req.isSelected(), 10);
            this.add(ckbx);
        }

    }

    public void registerComponentMove() {
        Component[] comps = this.getComponents();
        for (Component comp : comps) {
            PublicVars.getCM().registerComponent(comp);
        }

    }

    public void unregisterComponentMove() {
        Component[] comps = this.getComponents();
        for (Component comp : comps) {
            cm.deregisterComponent(comp);
        }
    }

    public void loadLabRequestPanel() { //DIT MOET VERANDEREN ZODAT DE XML KAN WORDEN INGELEZEN

//        if (this.getComponents() != null) {
//            this.removeAll();
//        }
//
//        PublicVars.setRequestsInProfile(PublicVars.getQueries().loadRequestPanel());
//        boolean selected = false;
//
//        for (String[] values : PublicVars.getRequestsInProfile()) {
//            if (values[4].equals("1")) {
//                selected = true;
//            } else {
//                selected = false;
//            }
//
//            JCheckBox ckbx = getCkbx(Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[0], values[1], selected, Integer.parseInt(values[5]));
//            this.add(ckbx);
//            PublicVars.getCM().registerComponent(ckbx);
//        }
//        //jScrollPane_LabRequests.getVerticalScrollBar().setUnitIncrement(16);
//        this.repaint();
    }

    public void loadLabels() {

//        if (this.getComponents() != null) {
//            for (Component comp : this.getComponents()) {
//                Class<? extends Component> c = comp.getClass();
//                if (c.getSimpleName().equals("JLabel")) {
//                    this.remove(comp);
//                };
//            }
//
//        }
//        PublicVars.setLabelsInProfile(PublicVars.getQueries().loadLabels());
//        for (String[] values : PublicVars.getLabelsInProfile()) {
//
//            JLabel lbl = getLbl(Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[6], Integer.parseInt(values[4]), values[5]);
//            this.add(lbl);
//            PublicVars.getCM().registerComponent(lbl);
//        }
//        this.repaint();
    }

    public void addLabel(int x, int y, String txt, int letterGrootte, int kleur) {
        JLabel lbl = getLbl(x, y, txt, letterGrootte, String.valueOf(kleur));
        this.add(lbl);
        PublicVars.getCM().registerComponent(lbl);
        this.repaint();
        ObjToXML.saveProfile(this.getComponents(), "default");
    }

    public void editLabel(JLabel lbl, int x, int y, String txt, int letterGrootte, int kleur) {
        lbl.setLocation(x, y);
        lbl.setText(txt);
        lbl.setFont(new java.awt.Font("Tahoma", 1, letterGrootte));
        lbl.setForeground(Color.decode(String.valueOf(kleur)));
        dynamicComponentSize(lbl, txt);
        ObjToXML.saveProfile(this.getComponents(), "default");
    }

    public JLabel getLbl(int x, int y, String txt, int letterGrootte, String kleur) {
        lbl = new JLabel();
        lbl.setLocation(x, y);
        lbl.setText(txt);
        lbl.setFont(new java.awt.Font("Tahoma", 1, letterGrootte));
        lbl.setForeground(Color.decode(kleur));
        lbl.setName(" ");
        dynamicComponentSize(lbl, txt);
        lbl = addLabelisteners(lbl);

        return lbl;
    }

    public void DeleteLabel(JLabel lbl) {
        this.remove(lbl);
        ObjToXML.saveProfile(this.getComponents(), "default");
        //queries.deleteGroupLabel(Integer.parseInt(PublicVars.getCurrentGroupLabel()));
        //loadLabels();
    }

    public void selectComponentsInSquare(Point point1, Point point2) {
        if (point1 != null & point2 != null) {
            Point buffer = new Point();
            if (point1.x > point2.x) {
                buffer.x = point2.x;
                point2.x = point1.x;
                point1.x = buffer.x;
            }
            if (point1.y > point2.y) {
                buffer.y = point2.y;
                point2.y = point1.y;
                point1.y = buffer.y;
            }
            //-------------------------------------------------------------------  
            Component[] comps = this.getComponents();
            JPanel j = new JPanel(null);
            //j.setBounds(point1.x, point1.y, point2.x - point1.x, point2.y - point1.y);
            //j.setLocation(point1);
            j.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
            j.setBackground(new Color(255, 255, 255, 0));
            j.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {

                    j.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

            });
            j.setVisible(true);
            this.add(j);
            this.setComponentZOrder(j, 0);
            //-------------------------------------------------------------------
            int leftest = -1, rightest = -1, highest = -1, lowest = -1;
            Dimension dimensionBuffer = new Dimension();
            Rectangle selectionBoxRectangle = new Rectangle(point1.x, point1.y, point2.x - point1.x, point2.y - point1.y);
            for (Component comp : comps) {
                Rectangle compRectangle = new Rectangle(comp.getLocation(), comp.getSize());
                if (compRectangle.intersects(selectionBoxRectangle)) {
                    buffer = comp.getLocation();
                    dimensionBuffer = comp.getSize();

                    leftest = PublicFunctions.returnSmallest(leftest, buffer.x) - 1;
                    rightest = PublicFunctions.returnLargest(rightest, buffer.x + dimensionBuffer.width) + 1;
                    highest = PublicFunctions.returnSmallest(highest, buffer.y) - 1;
                    lowest = PublicFunctions.returnLargest(lowest, buffer.y + dimensionBuffer.height) + 1;

                }
            }
            if (leftest < 0) {
                leftest = 0;
            }
            if (rightest < 0) {
                rightest = 0;
            }
            if (highest < 0) {
                highest = 0;
            }
            if (lowest < 0) {
                lowest = 0;
            }
            // j.setLocation(leftest,highest);
            j.setBounds(leftest, highest, rightest - leftest, lowest - highest);

//        int leftest = -1, rightest = -1, highest = -1, lowest = -1;
//        Dimension dimensionBuffer = new Dimension();
//        Rectangle selectionBoxRectangle = new Rectangle(point1.x, point1.y, point2.x - point1.x, point2.y - point1.y);
            for (Component comp : comps) {
                Rectangle compRectangle = new Rectangle(comp.getLocation(), comp.getSize());
                if (compRectangle.intersects(selectionBoxRectangle)) {
                    buffer = comp.getLocation();
                    dimensionBuffer = comp.getSize();
                    j.add(comp);
                    this.remove(comp);
                    cm.deregisterComponent(comp);

//                    comp.addMouseListener(
//                            new MouseAdapter() {
//
//                        @Override
//                        public void mousePressed(MouseEvent e) {
//                            Component child = e.getComponent();
//                            Component parent = child.getParent();
//                            //transform the mouse coordinate to be relative to the parent component:
//                            int deltax = child.getX() + e.getX();
//                            int deltay = child.getY() + e.getY();
//                            MouseEvent parentMouseEvent = new MouseEvent(parent, MouseEvent.MOUSE_PRESSED, e.getWhen(), e.getModifiers(), deltax, deltay, e.getClickCount(), false); //dispatch it to the parent component
//                            parent.dispatchEvent(parentMouseEvent);
//
//                        }
//
//                        @Override
//                        public void mouseClicked(MouseEvent e) {
//                            Component child = e.getComponent();
//                            Component parent = child.getParent();
//                            //transform the mouse coordinate to be relative to the parent component:
//                            int deltax = child.getX() + e.getX();
//                            int deltay = child.getY() + e.getY();
//                            MouseEvent parentMouseEvent = new MouseEvent(parent, MouseEvent.MOUSE_CLICKED, e.getWhen(), e.getModifiers(), deltax, deltay, e.getClickCount(), false); //dispatch it to the parent component
//                            parent.dispatchEvent(parentMouseEvent);
//
//                        }
//
//                        @Override
//                        public void mouseReleased(MouseEvent e) {
//                            Component child = e.getComponent();
//                            Component parent = child.getParent();
//                            //transform the mouse coordinate to be relative to the parent component:
//                            int deltax = child.getX() + e.getX();
//                            int deltay = child.getY() + e.getY();
//                            MouseEvent parentMouseEvent = new MouseEvent(parent, MouseEvent.MOUSE_RELEASED, e.getWhen(), e.getModifiers(), deltax, deltay, e.getClickCount(), false); //dispatch it to the parent component
//                            parent.dispatchEvent(parentMouseEvent);
//
//                        }
//
//                    });
//                    comp.addMouseMotionListener(
//                            new MouseAdapter() {
//                        @Override
//                        public void mouseDragged(MouseEvent e) {
//                            Component child = e.getComponent();
//                            Component parent = child.getParent();
//                            //transform the mouse coordinate to be relative to the parent component:
//                            int deltax = child.getX() + e.getX();
//                            int deltay = child.getY() + e.getY();
//                            MouseEvent parentMouseEvent = new MouseEvent(parent, MouseEvent.MOUSE_DRAGGED, e.getWhen(), e.getModifiers(), deltax, deltay, e.getClickCount(), false); //dispatch it to the parent component
//                            parent.dispatchEvent(parentMouseEvent);
//
//                        }
//
//                        @Override
//                        public void mouseMoved(MouseEvent e) {
//                            Component child = e.getComponent();
//                            Component parent = child.getParent();
//                            //transform the mouse coordinate to be relative to the parent component:
//                            int deltax = child.getX() + e.getX();
//                            int deltay = child.getY() + e.getY();
//                            MouseEvent parentMouseEvent = new MouseEvent(parent, MouseEvent.MOUSE_MOVED, e.getWhen(), e.getModifiers(), deltax, deltay, e.getClickCount(), false); //dispatch it to the parent component
//                            parent.dispatchEvent(parentMouseEvent);
//
//                        }
//
//                    });
                    comp.setLocation(buffer.x - j.getX(), buffer.y - j.getY());
//                    Class<? extends Component> c = comp.getClass();
//                    if (c.getSimpleName().equals("JLabel")) {
//                        JLabel lbl = (JLabel) comp;
//
//                    }
//                    if (c.getSimpleName().equals("JCheckBox")) {
//                        JCheckBox ckbx = (JCheckBox) comp;
//
//                    }
//                    if (c.getSimpleName().equals("JTextField")) {
//                        JTextField txtf = (JTextField) comp;
//                    }
//                    if (c.getSimpleName().equals("JFormattedTextField")) {
//                        JFormattedTextField txtf = (JFormattedTextField) comp;
//                    }
//                    if (c.getSimpleName().equals("JComboBox")) {
//                        JComboBox cmb = (JComboBox) comp;
//                    }
                    leftest = PublicFunctions.returnSmallest(leftest, buffer.x);
                    rightest = PublicFunctions.returnLargest(rightest, buffer.x + dimensionBuffer.width);
                    highest = PublicFunctions.returnSmallest(highest, buffer.y);
                    lowest = PublicFunctions.returnLargest(lowest, buffer.y + dimensionBuffer.height);
                }

            }
            //j.setBounds(leftest, rightest, lowest - highest, rightest - leftest);
            this.revalidate();
            if (PublicVars.getIsEdit()) {
                PublicVars.getCM().registerComponent(j);
            }

        }
    }

    public void deselectComponents() {
        for (Component comp : this.getComponents()) {
            //comp.setBackground(Color.red);
            Class<? extends Component> c = comp.getClass();
            if (c.getSimpleName().equals("JPanel")) {
                JPanel panel = (JPanel) comp;
                for (Component panelComp : panel.getComponents()) {

//                    for (MouseMotionListener l : panelComp.getMouseMotionListeners()) {
//                        panelComp.removeMouseMotionListener(l);
//                        
//                    }
//                    for (MouseListener l : panelComp.getMouseListeners()) {
//                        panelComp.removeMouseListener(l);
//
//                        //panelComp.addMouseListener(m);
//                    }
                    this.add(panelComp);
                    panelComp.setLocation(panel.getX() + panelComp.getX(), panel.getY() + panelComp.getY());
                    if (PublicVars.getIsEdit()) {
                        cm.registerComponent(panelComp);
                    } else {
                        cm.deregisterComponent(panelComp);
                    }

//                    Class<? extends Component> clas = panelComp.getClass();
//                    if (clas.getSimpleName().equals("JLabel")) {
//                        JLabel lbl = (JLabel) panelComp;
////                        lbl = addLabelisteners(lbl);
////                        lbl.setBorder(null);
//                        lbl = getLbl(lbl.getX(), lbl.getY(), lbl.getText(), lbl.getFont().getSize(), String.valueOf(lbl.getForeground().getRGB()));
//
//                    }
//                    if (clas.getSimpleName().equals("JCheckBox")) {
//                        JCheckBox ckbx = (JCheckBox) panelComp;
////                        ckbx = addCheckBoxListeners(ckbx);
////                        ckbx.setBorder(null);
////                        ckbx.setBorderPainted(false);
//                        ckbx = getCkbx(ckbx.getX(), ckbx.getY(), ckbx.getText(), ckbx.getName(), ckbx.isSelected(), 1);
//                    }
//                    if (clas.getSimpleName().equals("JTextField")) {
//                        JTextField txtf = (JTextField) panelComp;
////                        txtf = addTextFieldisteners(txtf);
////                        txtf.setBorder(null);
//                        txtf = getTextField(txtf.getX(), txtf.getY(), txtf.getText(), txtf.getName(), txtf.getWidth());
//                        //txtf.setBorderPainted(false);
//                    }
//                    if (clas.getSimpleName().equals("JFormattedTextField")) {
//                        JFormattedTextField txtf = (JFormattedTextField) panelComp;
//
//                        txtf = getFormattedField(txtf.getX(), txtf.getY(), txtf.getText(), txtf.getName(), txtf.getWidth());
//                    }
                }
                this.remove(comp);
            };

        }
        this.repaint();
    }

    public void doPopIndividualRequest(MouseEvent e) {
        //MainWindow.jPopupMenuRequestOptions.show(e.getComponent(), e.getX(), e.getY());
        CustomPopUpMenuCheckbox customMenu = new CustomPopUpMenuCheckbox(e.getPoint(), e, this);
        customMenu.show(e.getComponent(), e.getX(), e.getY());
        //veranderen door customRequestPopupMenu()
    }

    public void doPopLabel(MouseEvent e) {
        //MainWindow.jPopupMenuLabel.show(e.getComponent(), e.getX(), e.getY());
        CustomPopUpMenuLabel customMenu = new CustomPopUpMenuLabel(e.getPoint(), e, this);
        customMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public static void doPopRequestPanel(MouseEvent e) {

        CustomPopUpMenu customMenu = new CustomPopUpMenu(e.getPoint(), e);
        customMenu.show(e.getComponent(), e.getX(), e.getY());

    }

    public void ClickRequests() {
//
//        for (String[] request : PublicVars.getRequestsInProfile()) {
//            if (PublicVars.getCurrentGroupLabel().equals(request[6])) {
//
//                for (Component c : this.getComponents()) {
//                    if (c instanceof JCheckBox) {
//                        if (((JCheckBox) c).getMnemonic() == Integer.parseInt(request[5])) {
//                            if (((JCheckBox) c).isSelected()) {
//                                ((JCheckBox) c).setSelected(false);
//                            } else {
//                                ((JCheckBox) c).setSelected(true);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    public void updateLabelPositions() {

        // queries.updateLabelProfilePositions(this.getComponents());
    }

    public void OpenLabelForm() {

        // LabelForm labelForm = new LabelForm(true, true, );
    }

    public void updateRequestPositions() {
        // queries.updateRequestProfilePositions(this.getComponents());

    }

    public void addTextField(int x, int y, String txt, int width, int height) {
        JTextField txtf = getTextField(x, y, txt, "", 100);
        txtf.setSize(width, height);
        this.add(txtf);
        PublicVars.getCM().registerComponent(txtf);
    }

    //---------------------------------------------------------
    static JCheckBox ckbx;
    static JLabel lbl;
    static JTextField txtf;
    static JButton btn;
    static JFormattedTextField frmttxt;
    static JComboBox<String> cmbbox;

    public static void dynamicComponentSize(JComponent cmp, String txt) {
        FontMetrics metrics = cmp.getFontMetrics(cmp.getFont());
        int width = metrics.stringWidth(txt);
        int height = metrics.getHeight();
        Dimension newDimension = new Dimension(width + 40, height + 10);
        cmp.setPreferredSize(newDimension);
        cmp.setBounds(new Rectangle(cmp.getLocation(), cmp.getPreferredSize()));

    }

    public JCheckBox addCheckBoxListeners(JCheckBox j) {
        j.addMouseListener(
                new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
                    doPopIndividualRequest(e);
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
                    doPopIndividualRequest(e);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

        });
        return j;
    }

    public JLabel addLabelisteners(JLabel j) {
        j.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JLabel currentLbl = (JLabel) e.getComponent();
                if (e.isPopupTrigger()) {
                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
                    PublicVars.setPopUpClick(new Point(e.getComponent().getLocation().x, e.getComponent().getLocation().y));
                    doPopLabel(e);
                } else {
//                            ClickRequests();
                }
                //List<String[]> Labels = queries.selectLabel(currentLbl.getText());
                //if (Labels.size() > 0) {
                //   PublicVars.setCurrentGroupLabel(Labels.get(0)[0]);
//                            if (e.isPopupTrigger()) {
//                                PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
//                                PublicVars.setPopUpClick(e.getLocationOnScreen());
//                                doPopLabel(e);
//                            } else {
//                                ClickRequests();
//                            }
                //}

            }

            @Override
            public void mouseClicked(MouseEvent e) {
//                        if (e.getButton() == 3 && PublicVars.isEditRequestForm()) {
//                            JCheckBox target = (JCheckBox) e.getSource();
//                            GUI_interface.queries.deleteProfileRequest(PublicVars.getProfielID(), target.getMnemonic(), Integer.parseInt(PublicVars.getUserID()));
//                            //Dynamic_swing.infoBox(target.getName(), target.getText());
//                            GUI_interface.jPanel_aanvraagformulier.remove(target);
//                            GUI_interface.jPanel_aanvraagformulier.revalidate();
//                            GUI_interface.jPanel_aanvraagformulier.repaint();
//                        }
            }

        });
        return j;
    }

    public JTextField addTextFieldisteners(JTextField t) {
        t.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JTextField currentTxtF = (JTextField) e.getComponent();
//                List<String[]> textFields = queries.selectLabel(currentTxtF.getText());
//                if (textFields.size() > 0) {
//                    //PublicVars.setCurrentGroupLabel(textFields.get(0)[0]);
//
//                }
                if (e.isPopupTrigger()) {
                    PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
                    PublicVars.setPopUpClick(e.getPoint());
                    doPopLabel(e);
                } else {
                    currentTxtF.requestFocus();
                    ClickRequests();
                }

            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
//          }

        });
        return t;
    }

    public void addPatientData() {
        currentRequest.patient.firstName.setValue(PublicVars.getPatientInformation().get(0));
        currentRequest.patient.name.setValue(PublicVars.getPatientInformation().get(1));
        currentRequest.patient.birthDate.setValue(PublicVars.getPatientInformation().get(6));
        currentRequest.patient.zip.setValue(PublicVars.getPatientInformation().get(3));
        currentRequest.patient.city.setValue(PublicVars.getPatientInformation().get(4));
        currentRequest.patient.gender.setValue(PublicVars.getPatientInformation().get(5));
        currentRequest.patient.nationalNumber.setValue(PublicVars.getPatientInformation().get(8));
        currentRequest.patient.straatAndNumber.setValue(PublicVars.getPatientInformation().get(2));
        this.removeAll();
        loadRequestOrProfile(currentRequest);
    }

    public Component addShortCutListener(Component c, int shortcut) {
        c.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {

                    PatientInfo searchPatient = new PatientInfo((LabRequestPanel) e.getComponent().getParent());
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        return null;
    }

    public JButton addButtonisteners(JButton b) {
        b.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                //List<String[]> buttons = queries.selectButton(currentButton.getText()); //Dit veranderen door xml zoekactie van profiel
//                if (buttons.size() > 0) {
//                    //PublicVars.setCurrentGroupLabel(textFields.get(0)[0]);
//                    if (e.isPopupTrigger()) {
//                        PublicVars.setSelectComponentOnLabRequestPanel(e.getComponent());
//                        PublicVars.setPopUpClick(e.getLocationOnScreen());
//                      //  doPopButton(e);
//                    } else {
//                        ClickRequests();
//                    }
//                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JButton currentButton = (JButton) e.getComponent();
                if (currentButton.getName().equals("Save")) {
                    ObjToXML.saveKmehrRequest(ObjToXML.saveLabrRequest(e.getComponent().getParent().getComponents()));
                    pg.out.loadSentMessages();
                }
                if (currentButton.getName().equals("Search")) {
                    MainWindow.OpenPatientSearch(((JButton) e.getSource()).getParent().getComponents());
                }
            }
//          }

        });
        return b;
    }

    public JTextField getTextField(int x, int y, String txt, String name, int width) {
        txtf = new JTextField();
        txtf.setLocation(x, y);
        txtf.setText(txt);
        txtf.setName(name);
        txtf.setSize(width, 20);
        txtf.setBackground(Color.WHITE);
        txtf.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        //dynamicComponentSize(txtf, txt);
        txtf = addTextFieldisteners(txtf);

        return txtf;
    }

    public JButton getButton(int x, int y, String txt) {
        btn = new JButton();
        btn.setLocation(x, y);
        btn.setText(txt);
        btn.setName(txt);
        dynamicComponentSize(btn, txt);
        btn = addButtonisteners(btn);
        return btn;
    }

    public JButton getButton(int x, int y, ImageIcon icon, String name) {
        btn = new JButton();
        btn.setLocation(x, y);
        btn.setName(name);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setSize(50, 50);
        btn.setIcon(icon);
        btn = addButtonisteners(btn);

        return btn;
    }

    public JCheckBox getCkbx(int x, int y, String txt, String name, Boolean selected, Integer id) {
        ckbx = new JCheckBox();
        ckbx.setLocation(x, y);
        ckbx.setText(txt);
        ckbx.setName(name);
        ckbx.setSelected(selected);
        ckbx.setBackground(Color.white);
        ckbx.setMnemonic(id);
        dynamicComponentSize(ckbx, txt);
        ckbx = addCheckBoxListeners(ckbx);
        return ckbx;
    }

    public JFormattedTextField getFormattedField(int x, int y, String txt, String name, int width) {
        MaskFormatter dateMask = null;
        try {
            dateMask = new MaskFormatter("####/##/##");
            dateMask.setPlaceholderCharacter('_');
            dateMask.setValidCharacters("0123456789");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        frmttxt = new JFormattedTextField(dateMask);
        frmttxt.setLocation(x, y);
        frmttxt.setText(txt);
        frmttxt.setSize(width, 20);
        frmttxt.setName(name);
        frmttxt.setBackground(Color.WHITE);
        frmttxt.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        return frmttxt;
    }

    public JComboBox getComboBox(int x, int y, String[] options, String value, String name) {
        //String[] bookTitles = new String[] {"Effective Java", "Head First Java","Thinking in Java", "Java for Dummies"};

        cmbbox = new JComboBox<String>(options);
        cmbbox.setSelectedItem(value);
        cmbbox.setLocation(x, y);
        cmbbox.setSize(100, 18);
        cmbbox.setName(name);
        cmbbox.setVisible(true);
        cmbbox.setBackground(Color.WHITE);
        cmbbox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));

        //dynamicComponentSize(cmbbox, value);
        return cmbbox;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
