/*
 *  Copyright (C) Matthias De Mey - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by De Mey Matthias <de.mey.mat@gmail.com>, july 2016
 */
package labr_client.GUI.custom_classes;

import javax.swing.JFrame;

/**
 *
 * @author labbl
 */
public class CustomJFrame extends JFrame {

    CustomJPanel cjp;

    public CustomJFrame(int w, int h) {
        this.setSize(w, h);        
        cjp = new CustomJPanel(this.getWidth(),this.getHeight(), "#FFFFFF");
        this.add(cjp);
        cjp.setVisible(true);
        this.setVisible(true);
    }

}
