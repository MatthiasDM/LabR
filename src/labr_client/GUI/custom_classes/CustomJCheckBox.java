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

import static java.awt.Color.red;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JCheckBox;

/**
 *
 * @author De Mey Matthias
 */
public class CustomJCheckBox extends JCheckBox {
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
        height = this.getHeight();
        width = this.getHeight();
        bbg = (Graphics2D) this.getGraphics();
        //bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bbg.setColor(red);
        if (r != null) {
            bbg.drawRect(r.x - this.getX(), r.y-this.getY(), r.width, r.height);
        }

    }
}
