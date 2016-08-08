/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import labr_client.Public.PublicVars;

/**
 *
 * @author LABBL
 */
public class XMLToObj {

    public static LabrRequest XMLToJaxBObject() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest request = (LabrRequest) unmarshaller.unmarshal(new File("C:\\Users\\labbl\\Documents\\test.xml"));
            System.out.println("Succesfully unmarshalled");
            return request;
        } catch (JAXBException ex) {
            Logger.getLogger(XMLToObj.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static final String PREFIX = "loadrequest";
    public static final String SUFFIX = ".tmp";

    public static LabrRequest loadRequest(String request) {
        //File req = new File("data/tmp/tmp1.xml");
        File req = new File("");
        try {
            req = File.createTempFile(PREFIX, SUFFIX);
            FileOutputStream out = new FileOutputStream(req);
            out.write(request.getBytes());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLToObj.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLToObj.class.getName()).log(Level.SEVERE, null, ex);
        }

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest labrRequest = (LabrRequest) unmarshaller.unmarshal(req);
            return labrRequest;
        } catch (JAXBException ex) {
            Logger.getLogger(XMLToObj.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static LabrRequest loadProfile(String profile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LabrRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LabrRequest request = (LabrRequest) unmarshaller.unmarshal(new File(PublicVars.getUserData()[9] + "\\" + profile + ".xml"));
            System.out.println("Succesfully unmarshalled");
            return request;
        } catch (JAXBException ex) {
            Logger.getLogger(XMLToObj.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
