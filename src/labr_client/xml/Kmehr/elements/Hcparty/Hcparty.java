/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements.Hcparty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.Kmehr.elements.Cd;
import labr_client.xml.Kmehr.elements.Id;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "hcparty")
public class Hcparty {

    String firstname, familyname;
    @XmlElement
    public Cd cd = new Cd();
    @XmlElement
    public Id id = new Id();

    public String getFirstname() {
        return firstname;
    }
@XmlElement
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFamilyname() {
        return familyname;
    }
@XmlElement
    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }
    
    
    
}
