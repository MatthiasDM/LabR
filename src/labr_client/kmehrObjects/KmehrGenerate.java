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
package labr_client.kmehrObjects;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.HashMap;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static labr_client.kmehrObjects.xmlMethods.AddElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
            getLogger(KmehrGenerate.class.getName()).log(SEVERE, null, ex);
            return null;
        }
    }
   
    public Document AddHeader(Document doc){
        HashMap<String, String> attr = new HashMap<>();
        attr.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        attr.put("xsi:schemaLocation", "kmehr_xschema\\ehealth-kmehr\\XSD\\kmehr_elements-1_14.xsd");
        return AddElement(doc,"kmehrmessage",attr);
        
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
            getLogger(KmehrGenerate.class.getName()).log(SEVERE, null, ex);
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
			
            	
            } catch (ParserConfigurationException | TransformerException pce) {
		pce.printStackTrace();
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
