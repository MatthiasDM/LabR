/*
 *  Copyright (C) Matthias De Mey - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by De Mey Matthias <de.mey.mat@gmail.com>, july 2016
 */
package labr_client.GUI.custom_classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonListener;

/**
 *
 * @author labbl
 */
public class CustomJButton extends JButton {

    private static final float arcwidth = 16.0f;
    private static final float archeight = 16.0f;
    protected static final int focusstroke = 2;
    protected final Color fc = new Color(100, 150, 255, 200);
    protected final Color ac = new Color(230, 230, 230);
    protected final Color rc = Color.ORANGE;
    protected Shape shape;
    protected Shape border;
    protected Shape base;
    Component c;
    public CustomJButton() {

        c = this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

     
        //Border
        g2.setPaint(c.getForeground());

        g2.setColor(c.getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

    }

    
}
