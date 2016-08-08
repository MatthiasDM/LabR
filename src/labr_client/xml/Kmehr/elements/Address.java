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
@XmlRootElement(name = "address")
public class Address {

    String zip, city, streetandnumber;
    @XmlElement
    public Country country = new Country();
    @XmlElement
    public Cd cd = new Cd();

    public String getZip() {
        return zip;
    }

    @XmlElement
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetandnumber() {
        return streetandnumber;
    }

    @XmlElement
    public void setStreetandnumber(String streetandnumber) {
        this.streetandnumber = streetandnumber;
    }

}
