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
@XmlRootElement(name = "patient")
public class Patient {

    String firstname, familyname;

    @XmlElement
    public Id id = new Id();
    @XmlElement
    public Sex sex = new Sex();
    @XmlElement
    public Birthdate birthdate = new Birthdate();
    @XmlElement
    public Address address = new Address();

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
