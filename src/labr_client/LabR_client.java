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
package labr_client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.GUI.forms.LoginWindow;
import labr_client.Public.PublicVars;
import labr_client.xml.Kmehr.KmehrMessage;
import labr_client.xml.ObjToXML;
import labr_client.xml.LabrRequest;

/**
 *
 * @author familie
 */
public class LabR_client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//        ObjToXML.jaxbObjectToXML(new LabrRequest(), null);
//        ObjToXML.jaxbObjectToXML(new KmehrMessage());
        try {
            loadInitialProperties();
        } catch (IOException ex) {
            Logger.getLogger(LabR_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoginWindow login = new LoginWindow();

    }

    private static void loadInitialProperties() throws IOException {
        try {
            //--------LOAD JAR DIRECTORY------------------------------
            CodeSource codeSource = LabR_client.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            //-----------------------------------------------------------            
            Properties p = new Properties();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String propLocation = "properties\\config.properties";
            InputStream is = classLoader.getResourceAsStream(propLocation);
            p.load(is);
            p.setProperty("jar_path", jarDir);
            p.setProperty("current_path", classLoader.getResource("labr_client").getPath());
            
            
            
//            String path = LabR_client.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            String decodedPath = URLDecoder.decode(path, "UTF-8");
            PublicVars.setProperties(p);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LabR_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
