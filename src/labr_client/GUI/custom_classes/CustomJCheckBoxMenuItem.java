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
import java.awt.Insets;
import javax.swing.JCheckBoxMenuItem;
/**
 *
 * @author De Mey Matthias
 */
public class CustomJCheckBoxMenuItem extends JCheckBoxMenuItem{
     Color bgColor;
    String text;
    int fontSize;
    public CustomJCheckBoxMenuItem(Color c, String t, int width, int fs) {
        bgColor = c;
        text = t;
        fontSize = fs;
        setPreferredSize(new Dimension(width, 35));
        setMinimumSize(new Dimension(width, 35));
        this.setFont(new Font("Arial", BOLD, fontSize));
        this.setBorder(null);
        this.setBorderPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setBackground(bgColor);
        Font font = new Font("Arial", BOLD, fontSize);
        this.setFont(font);
        this.setForeground(white);
        this.setText(text);
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(bgColor);
//        g2.fillRect(0, 0, getWidth(), 35);   
//        g2.setColor(Color.white);
//        Font font = new Font("Arial", Font.BOLD, fontSize);
//        int fontWidth = (int) PublicFunctions.getWidth(text, font, g2);        
//        g2.drawString(text, (getWidth()-fontWidth)/2, (int) (getHeight()/1.65));
//    }
}
