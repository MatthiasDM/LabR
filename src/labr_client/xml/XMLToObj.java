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
package labr_client.xml;

import java.io.File;
import static java.io.File.createTempFile;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.xml.bind.JAXBContext;
import static javax.xml.bind.JAXBContext.newInstance;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import static labr_client.Public.PublicVars.getUserData;

/**
 *
 * @author De Mey Matthias
 */
public class XMLToObj {

    public static LabrRequest XMLToJaxBObject() {
        try {
            JAXBContext jaxbContext = newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest request = (LabrRequest) unmarshaller.unmarshal(new File("C:\\Users\\labbl\\Documents\\test.xml"));
            out.println("Succesfully unmarshalled");
            return request;
        } catch (JAXBException ex) {
            getLogger(XMLToObj.class.getName()).log(SEVERE, null, ex);
            return null;
        }
    }
    public static final String PREFIX = "loadrequest";
    public static final String SUFFIX = ".tmp";

    public static LabrRequest loadRequest(String request) {
        //File req = new File("data/tmp/tmp1.xml");
        File req = new File("");
        try {
            req = createTempFile(PREFIX, SUFFIX);
            FileOutputStream out = new FileOutputStream(req);
            out.write(request.getBytes());
        } catch (FileNotFoundException ex) {
            getLogger(XMLToObj.class.getName()).log(SEVERE, null, ex);
        } catch (IOException ex) {
            getLogger(XMLToObj.class.getName()).log(SEVERE, null, ex);
        }

        JAXBContext jaxbContext;
        try {
            jaxbContext = newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest labrRequest = (LabrRequest) unmarshaller.unmarshal(req);
            return labrRequest;
        } catch (JAXBException ex) {
            getLogger(XMLToObj.class.getName()).log(SEVERE, null, ex);
            return null;
        }

    }

    public static LabrRequest loadProfile(String profile) {
        try {
            JAXBContext jaxbContext = newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest request = (LabrRequest) unmarshaller.unmarshal(new File(getUserData()[9] + "\\" + profile + ".xml"));
            out.println("Succesfully unmarshalled");
            return request;
        } catch (JAXBException ex) {
            getLogger(XMLToObj.class.getName()).log(SEVERE, null, ex);
            return null;
        }
    }

}
