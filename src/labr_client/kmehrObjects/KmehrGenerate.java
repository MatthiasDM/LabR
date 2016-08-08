/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.kmehrObjects;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import be.ehealth.business.kmehrcommons.*;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import java.util.HashMap;

/**
 *
 * @author familie
 */
public abstract class KmehrGenerate {
    private static final String SCHEMA_PATH = "kmehr_xschema\\ehealth-kmehr\\XSD\\kmehr_elements-1_14.xsd";
    String attrName, attrValue;    
    
    
    public KmehrGenerate() {
     
    }
    
    public Document createDoc(){    
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(KmehrGenerate.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
   
    public Document AddHeader(Document doc){
        HashMap<String, String> attr = new HashMap<String, String>();
        attr.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        attr.put("xsi:schemaLocation", "kmehr_xschema\\ehealth-kmehr\\XSD\\kmehr_elements-1_14.xsd");
        return xmlMethods.AddElement(doc,"kmehrmessage",attr);
        
    }
    
    public String XmlToString(Document doc){
    
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (TransformerException ex) {
            Logger.getLogger(KmehrGenerate.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public String createLabRequest() throws SAXException, MalformedURLException{
        String xml_generated = "";
	try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();			
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();	
            Document doc = docBuilder.newDocument();		
            doc = creata_xml_sketelon(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);            
          
 
            StreamResult result = new StreamResult(new StringWriter());			
            transformer.transform(source, result);
            xml_generated =  result.getWriter().toString();            
			
            	
            } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
            } catch (TransformerException tfe) {
		tfe.printStackTrace();
        }      
      
	return xml_generated;
    }
	
    public Document creata_xml_sketelon(Document doc){

		
		Element kmehrmessage = doc.createElement("kmehrmessage");	
		kmehrmessage.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		kmehrmessage.setAttribute("xsi:schemaLocation", "kmehr_xschema\\ehealth-kmehr\\XSD\\kmehr_elements-1_14.xsd");
		doc.appendChild(kmehrmessage);
			Element header = doc.createElement("header");
			kmehrmessage.appendChild(header);		
				Element standard = doc.createElement("standard");
				header.appendChild(standard);
			Element folder = doc.createElement("folder");
			kmehrmessage.appendChild(folder);	
				Element patient = doc.createElement("patient");
				folder.appendChild(patient);
				Element transaction = doc.createElement("transaction");
				folder.appendChild(transaction);
					Element item1 = doc.createElement("item");
					transaction.appendChild(item1);
					Element item2 = doc.createElement("item");
					transaction.appendChild(item2);
					Element heading1 = doc.createElement("heading");
					transaction.appendChild(heading1);
						Element item3 = doc.createElement("item");
						heading1.appendChild(item3);
					Element heading2 = doc.createElement("heading");
					transaction.appendChild(heading2);
						Element item4 = doc.createElement("item");
						heading2.appendChild(item4);
						Element item5 = doc.createElement("item");
						heading2.appendChild(item5);
						
		return doc;
	}


}
