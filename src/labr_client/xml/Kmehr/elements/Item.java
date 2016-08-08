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
@XmlRootElement(name = "item")
public class Item {

    @XmlElement
    public Id id = new Id();
    @XmlElement
    public Cd cd = new Cd();
    @XmlElement
    public Content content = new Content();
    
    

}
