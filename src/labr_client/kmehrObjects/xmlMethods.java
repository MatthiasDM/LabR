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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author De Mey Matthias
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
