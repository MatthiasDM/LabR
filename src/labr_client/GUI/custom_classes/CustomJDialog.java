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

import javax.swing.JDialog;

/**
 *
 * @author De Mey Matthias
 */
public class CustomJDialog extends JDialog{

    CustomJPanel cjp;

    public CustomJDialog(int w, int h) {
        this.setSize(w, h);
        cjp = new CustomJPanel(this.getWidth(), this.getHeight(), "#FFFFFF");
        this.add(cjp);
        cjp.setVisible(true);
        this.setVisible(true);
    }

}
