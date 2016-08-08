/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.Kmehr.elements.Hcparty.Hcparty;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "author")
public class Author {
    @XmlElement
    public Hcparty hcparty = new Hcparty();
}
