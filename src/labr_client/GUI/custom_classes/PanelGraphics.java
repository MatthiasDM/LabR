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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import labr_client.GUI.forms.MainWindow;

/**
 *
 * @author De Mey Matthias
 */
public class PanelGraphics extends JPanel implements ActionListener {

    //------------GRAPHIC RELATED-------------------------
    BufferedImage backBuffer;
    Graphics backG;
    Graphics2D g2, bbg;
    int width, height, headerRounding = 20;
    Color c = Color.decode("#EDEBE5");
    Rectangle logoBounds, button1Bounds, button2Bounds;
    //-------------------------------------

    Boolean button1 = false, button2 = false, logo = false;
    Boolean menuClick = false;
    Boolean menuCleared = false;
    Timer timer;

//-------------------------------------
    JScrollPane lrpScrollContainer = new JScrollPane();

    public LabRequestPanel lrp;
    public customOutbox out;
    CustomJMenuBar jmb;
    JScrollPane outBoxScrollPane;
    //-------------------------------------
    public static javax.swing.JPopupMenu jPopupMenuLabel;

    public PanelGraphics(int width, int height) {

        //setMinimumSize(new Dimension(width, height));
        timer = new Timer(30, this);
        //timer.start();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
//                checkForClick(e, button1Bounds, 1);
//                checkForClick(e, button2Bounds, 2);
//                checkForClick(e, logoBounds, 3);
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
//                checkForHover(event, button1Bounds, 1);
//                checkForHover(event, button2Bounds, 2);
//                checkForHover(event, logoBounds, 3);
            }
        });

        lrp = new LabRequestPanel(this);
        jmb = new CustomJMenuBar(Color.decode("#71456C"), lrp, this);
        out = new customOutbox(lrp, this);
        lrpScrollContainer = new JScrollPane();
        outBoxScrollPane = new JScrollPane();
        outBoxScrollPane.setBorder(null);
        outBoxScrollPane.add(out);
        outBoxScrollPane.setViewportView(out);
        lrpScrollContainer.setBorder(null);
        lrpScrollContainer.add(lrp);
        lrp.setSize(2000, 2000);
        lrp.setPreferredSize(new Dimension(2000, 2000));
        lrpScrollContainer.setViewportView(lrp);
        lrpScrollContainer.getVerticalScrollBar().setUnitIncrement(10);
        lrpScrollContainer.getHorizontalScrollBar().setUnitIncrement(10);
        this.setLayout(null);

        this.add(jmb);
        //this.add(lrp); 
        this.add(lrpScrollContainer);
        this.add(outBoxScrollPane);

        // lrpScrollContainer.setVisible(true);
        lrp.setVisible(true);
        jmb.setVisible(true);
        out.setVisible(true);
        revalidate();

        this.setSize(new Dimension(width, height));
        this.setVisible(true);

        // backBuffer = createImage(this.getWidth(), this.getHeight());
    }

    void checkForHover(MouseEvent event, Rectangle2D rect, int id) {
        if (rect.contains(event.getPoint())) {
            if (id == 1) {
                button1 = true;
                setCursor(Cursor.getPredefinedCursor(12));
            } else if (id == 2) {
                button2 = true;
                setCursor(Cursor.getPredefinedCursor(12));
            } else if (id == 3) {
                logo = true;
                setCursor(Cursor.getPredefinedCursor(12));
            }
        } else {
            if (button1 && id == 1) {
                button1 = false;
                setCursor(Cursor.getDefaultCursor());
            }
            if (button2 && id == 2) {
                button2 = false;
                setCursor(Cursor.getDefaultCursor());
            }
            if (logo && id == 3) {
                logo = false;
                setCursor(Cursor.getDefaultCursor());
            }
        }

    }

    void checkForClick(MouseEvent event, Rectangle2D rect, int id) {
        if (rect.contains(event.getPoint())) {
            if (id == 1) {

            } else if (id == 2) {

            } else if (id == 3) {
                menuClick = true;
            }
        } else {
            menuClick = false;
        }
        if (event.getY() > rect.getMaxY()) {
            menuClick = false;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        height = this.getHeight();
        width = this.getWidth();
        this.setSize(width, height);
        backBuffer = (BufferedImage) createImage(width, height);
        bbg = (Graphics2D) backBuffer.createGraphics();
        bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bbg.setColor(c);
        bbg.fillRect(0, 0, width, height);

        drawHeading(); //80 hoog, begint op 20
        drawKLBackground();
        //drawHeaderLogo();
//        drawButton1();
//        drawButton2();
        drawFooter(); //begint op 80, is 80% hoog --> 80%- ((hoogte frame))
        drawBody();
//        drawButton1();

        g2.drawImage(backBuffer, 0, 0, this);

    }

    public void drawHeading() {
        bbg.setColor(Color.decode("#71456C"));
        bbg.fillRoundRect((int) (width * 0.025), 20, (int) (width * 0.95), 80, headerRounding, headerRounding);
        int x = (int) (width * 0.125) - headerRounding / 2;
        jmb.setLocation((int) (width * 0.025) + 10, 20);
        jmb.setSize((int) (width * 0.80), 62);
        jmb.setBackground(Color.red);

    }

    public void drawKLBackground() {
        bbg.setColor(Color.decode("#000000"));
        int logoWidth = (int) (width * 0.1);
        int x = (int) (width * 0.025);
        int y = 20;
        int h = 80;
        logoBounds = new Rectangle(x, y, logoWidth, h);
        bbg.fillRoundRect(x, y, logoWidth, h, headerRounding, headerRounding);

//        y = y + h;
//        int fontSize = (int) (h / 2);
//        Font font = new Font("Arial", Font.PLAIN, fontSize);
//        bbg.setFont(font);
//        int fontHeight = (int) getHeight("KL", font) / 2;
//        int verschuivingy = 35;
//        int fontWidth = (int) getWidth("KL", font);
//        int verschuivingx = (logoWidth - fontWidth) / 2 - (headerRounding / 4);
//        drawString("KL", x + verschuivingx, y - verschuivingy, font, "#FFFFFF");
    }

    public void drawBody() {
        bbg.setColor(Color.decode("#FFFFFF"));
        int hoogte = height - 130;
        bbg.fillRect((int) (width * 0.025), 80, (int) (width * 0.95), hoogte);
        int offsetx = lrpScrollContainer.getHorizontalScrollBar().getValue();
        int offsety = lrpScrollContainer.getVerticalScrollBar().getValue();
        lrpScrollContainer.setSize((int) (width * 0.95), hoogte);
        lrpScrollContainer.setPreferredSize(new Dimension((int) (width * 0.95), hoogte));
        lrpScrollContainer.setLocation((int) (width * 0.025), 80);
        lrp.setSize(2000, 2000);
        lrp.setLocation(offsetx*-1, offsety*-1);        
        outBoxScrollPane.setSize((int) (width * 0.95) - 10, hoogte - 10);
        outBoxScrollPane.setLocation((int) (width * 0.025) + 5, 80 + 5);

    }

    public void drawFooter() {
        bbg.setColor(Color.decode("#FFB300"));
        int ypos = height - 120 + 80;
        bbg.fillRoundRect((int) (width * 0.025), ypos - (headerRounding * 2), (int) (width * 0.95), 60, headerRounding, headerRounding);
        MainWindow.jToolBar1.setLocation((int) (width * 0.05), ypos - 7);
        MainWindow.jToolBar1.setSize((int) (width * 0.80), 20);
        MainWindow.jToolBar1.setBackground(Color.decode("#FFB300"));
    }

    public void drawString(String text, int x, int y, Font f, String color) {
        bbg.setColor(Color.decode(color));

        bbg.drawString(text, x, y);

    }

    double getWidth(String message, Font f) {

        Rectangle2D bounds = getBounds(message, f);
        return bounds.getWidth();
    }

    double getHeight(String message, Font f) {

        Rectangle2D bounds = getBounds(message, f);
        return bounds.getHeight();
    }

    Rectangle2D getBounds(String message, Font f) {
        FontRenderContext context;
        context = g2.getFontRenderContext();
        return f.getStringBounds(message, context);
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer) {
            repaint();// this will call at every 1 second
        }
    }

    public void setLrpVisible() {
        outBoxScrollPane.setVisible(false);
        //lrp.setVisible(true);
        lrpScrollContainer.setVisible(true);
    }

    public void setOutboxVisible() {
        lrpScrollContainer.setVisible(false);
        //lrp.setVisible(false);
        outBoxScrollPane.setVisible(true);

    }

//    @Override
//    public void paintChildren(Graphics g) {
//        super.paintChildren(g);
//        if (menuClick) {
//            //menuCleared = false;
//            drawMenu1();
//            drawMenu2();
//        } else {
//            clearMenu();
//        }
//    }
    //----------------------------------------------------------------------------------------------------
}
