/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "birthdate")
public class Birthdate {
    String date;

    public String getDate() {
        return date;
    }
@XmlElement
    public void setDate(String date) {
        this.date = date;
    }
    
    
}
