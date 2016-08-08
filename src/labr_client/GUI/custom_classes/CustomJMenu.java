/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.GUI.custom_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import javax.swing.JMenu;
import labr_client.Public.PublicFunctions;

/**
 *
 * @author labbl
 */
public class CustomJMenu extends JMenu {

    Color bgColor;
    String text;
    int fontSize;
    Point stringLoc;

    public CustomJMenu(Color c, String t, int width, int fs) {
        bgColor = c;
        text = t;
        fontSize = fs;

        setPreferredSize(new Dimension(width, getHeight()));
        setMinimumSize(new Dimension(width, getHeight()));
        this.setFont(new Font("Arial", Font.BOLD, fontSize));

    }

    public CustomJMenu(Color c, String t, int width, int fs, int offsetX, int offsetY) {
        bgColor = c;
        text = t;
        fontSize = fs;
        stringLoc = new Point(offsetX, offsetY);
        setPreferredSize(new Dimension(width, getHeight()));
        setMinimumSize(new Dimension(width, getHeight()));
        this.setFont(new Font("Arial", Font.PLAIN, fontSize));

    }

    public CustomJMenu(Color c, String t, int width, int fs, int height) {
        bgColor = c;
        text = t;
        fontSize = fs;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setSize(getWidth(), height);
        this.setFont(new Font("Arial", Font.BOLD, fontSize));

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        int fontWidth = (int) PublicFunctions.getWidth(text, font, g2);
        if (stringLoc != null) {
            g2.drawString(text, stringLoc.x, stringLoc.y);
        } else {
            g2.drawString(text, (getWidth() - fontWidth) / 2, (int) (getHeight() / 1.65));
        }
    }
}
