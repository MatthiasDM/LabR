/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.kmehrObjects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author labbl
 */
public class xmlMethods {
    
    public static Document AddElement(Document doc, String name, HashMap<String, String> attrs){
        Element element = doc.createElement(name);  
        
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
             element.setAttribute(entry.getKey(), entry.getValue());             
        }
        doc.appendChild(element);
        return doc;
    }
    public static Document AddElement(Document doc, Element Parent, String name, HashMap<String, String> attrs){
        Element element = doc.createElement(name);  
        
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
             element.setAttribute(entry.getKey(), entry.getValue());             
        }
        Parent.appendChild(element);
        return doc;
    }
    
    public static Document AddElement(Document doc, Element Parent, String name){
        Element element = doc.createElement(name); 
        Parent.appendChild(element);
        return doc;
    }
    public static Document AddElement(Document doc, String name){
        Element element = doc.createElement(name); 
        doc.appendChild(element);
        return doc;
    }
    
    
}
