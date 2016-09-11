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
import static java.awt.Color.decode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author De Mey Matthias
 */
public class CustomJPanel extends JPanel {

    BufferedImage backBuffer;
    Graphics backG;
    Graphics2D g2, bbg;
    int width, height, headerRounding = 20;
    Color c = decode("#EDEBE5");
    Color colorHeading = decode("#EDEBE5");;
    public CustomJPanel(int w, int h, String cHeading){
    this.setSize(w, h);
    colorHeading = decode(cHeading);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        height = this.getHeight();
        width = this.getWidth();
        this.setSize(width, height);
        backBuffer = (BufferedImage) createImage(width, height);
        bbg = (Graphics2D) backBuffer.createGraphics();
        bbg.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
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
        bbg.setColor(decode("#FFFFFF"));
        int hoogte = height - 130;
        bbg.fillRect((int) (width * 0.05), 80, (int) (width * 0.90), hoogte);
    }

    public void drawFooter() {
        bbg.setColor(decode("#FFFFFF"));
        int ypos = height - 120 + 80;
        bbg.fillRoundRect((int) (width * 0.05), ypos - (headerRounding * 2), (int) (width * 0.90), 60, headerRounding, headerRounding);
    }
}
