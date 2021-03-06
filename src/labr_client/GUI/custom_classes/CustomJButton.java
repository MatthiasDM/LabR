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
import static java.awt.Color.ORANGE;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import java.awt.Shape;
import javax.swing.JButton;

/**
 *
 * @author De Mey Matthias
 */
public class CustomJButton extends JButton {

    private static final float arcwidth = 16.0f;
    private static final float archeight = 16.0f;
    protected static final int focusstroke = 2;
    protected final Color fc = new Color(100, 150, 255, 200);
    protected final Color ac = new Color(230, 230, 230);
    protected final Color rc = ORANGE;
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
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);

    }

    
}
