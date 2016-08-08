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
import java.awt.Insets;
import java.awt.RenderingHints;
import javafx.scene.layout.Border;
import javax.swing.JMenuItem;
import labr_client.Public.PublicFunctions;

/**
 *
 * @author labbl
 */
public class CustomJMenuItem extends JMenuItem {
    Color bgColor;
    String text;
    int fontSize;
    public CustomJMenuItem(Color c, String t, int width, int fs) {
        bgColor = c;
        text = t;
        fontSize = fs;
        setPreferredSize(new Dimension(width, 35));
        setMinimumSize(new Dimension(width, 35));
        this.setFont(new Font("Arial", Font.BOLD, fontSize));
        this.setBorder(null);
        this.setBorderPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), 35);   
        g2.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        int fontWidth = (int) PublicFunctions.getWidth(text, font, g2);        
        g2.drawString(text, (getWidth()-fontWidth)/2, (int) (getHeight()/1.65));
    }
}
