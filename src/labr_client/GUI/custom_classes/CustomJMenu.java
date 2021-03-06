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
import static java.awt.Color.white;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import javax.swing.JMenu;
import labr_client.Public.PublicFunctions;

/**
 *
 * @author De Mey Matthias
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
        this.setFont(new Font("Arial", BOLD, fontSize));

    }

    public CustomJMenu(Color c, String t, int width, int fs, int offsetX, int offsetY) {
        bgColor = c;
        text = t;
        fontSize = fs;
        stringLoc = new Point(offsetX, offsetY);
        setPreferredSize(new Dimension(width, getHeight()));
        setMinimumSize(new Dimension(width, getHeight()));
        this.setFont(new Font("Arial", PLAIN, fontSize));

    }

    public CustomJMenu(Color c, String t, int width, int fs, int height) {
        bgColor = c;
        text = t;
        fontSize = fs;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setSize(getWidth(), height);
        this.setFont(new Font("Arial", BOLD, fontSize));

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(white);
        Font font = new Font("Arial", BOLD, fontSize);
        int fontWidth = (int) PublicFunctions.getWidth(text, font, g2);
        if (stringLoc != null) {
            g2.drawString(text, stringLoc.x, stringLoc.y);
        } else {
            g2.drawString(text, (getWidth() - fontWidth) / 2, (int) (getHeight() / 1.65));
        }
    }
}
