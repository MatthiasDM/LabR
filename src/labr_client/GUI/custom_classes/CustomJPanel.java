/*
 *  Copyright (C) Matthias De Mey - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by De Mey Matthias <de.mey.mat@gmail.com>, july 2016
 */
package labr_client.GUI.custom_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import labr_client.GUI.forms.MainWindow;

/**
 *
 * @author labbl
 */
public class CustomJPanel extends JPanel {

    BufferedImage backBuffer;
    Graphics backG;
    Graphics2D g2, bbg;
    int width, height, headerRounding = 20;
    Color c = Color.decode("#EDEBE5");
    Color colorHeading = Color.decode("#EDEBE5");;
    public CustomJPanel(int w, int h, String cHeading){
    this.setSize(w, h);
    colorHeading = Color.decode(cHeading);
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
        drawHeading(); 
        drawFooter(); 
        drawBody();
        g2.drawImage(backBuffer, 0, 0, this);
    }

    public void drawHeading() {
        bbg.setColor(colorHeading);
        bbg.fillRoundRect((int) (width * 0.05), 20, (int) (width * 0.90), 80, headerRounding, headerRounding);
        int x = (int) (width * 0.125) - headerRounding / 2;

    }

    public void drawBody() {
        bbg.setColor(Color.decode("#FFFFFF"));
        int hoogte = height - 130;
        bbg.fillRect((int) (width * 0.05), 80, (int) (width * 0.90), hoogte);
    }

    public void drawFooter() {
        bbg.setColor(Color.decode("#FFFFFF"));
        int ypos = height - 120 + 80;
        bbg.fillRoundRect((int) (width * 0.05), ypos - (headerRounding * 2), (int) (width * 0.90), 60, headerRounding, headerRounding);
    }
}
