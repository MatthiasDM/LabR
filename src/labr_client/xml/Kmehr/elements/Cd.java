/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "cd")
public class Cd {

    String S;
    String SV;
    String value;

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

    public String getS() {
        return S;
    }

    @XmlAttribute
    public void setS(String S) {
        this.S = S;
    }

    public String getSV() {
        return SV;
    }

    @XmlAttribute
    public void setSV(String SV) {
        this.SV = SV;
    }

}
