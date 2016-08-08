/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author labbl
 */
public class CustomJLabel extends JLabel {

    Graphics2D g2, bbg, bbg2;
    int width, height;
    BufferedImage backBuffer;
    Rectangle r;

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2 = (Graphics2D) g;
        Color transparent = new Color(0, 0, 0, 0);
        height = this.getHeight();
        width = this.getHeight();
        bbg = (Graphics2D) this.getGraphics();
        bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        bbg.setColor(Color.red);
        if (r != null) {
            bbg.drawRect(r.x - this.getX(), r.y-this.getY(), r.width, r.height);
        }

    }


}
