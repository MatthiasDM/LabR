/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "request")
public class LabrXMLRequest {

    int x;
    int y;
    String loinc;
    String comment;
    String value;
    boolean selected;

    public String getValue() {
        return value;
    }

    @XmlAttribute
    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    @XmlAttribute
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getX() {
        return x;
    }

    @XmlAttribute
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    @XmlAttribute
    public void setY(int y) {
        this.y = y;
    }

    public String getLoinc() {
        return loinc;
    }

    @XmlAttribute
    public void setLoinc(String loinc) {
        this.loinc = loinc;
    }

    public String getComment() {
        return comment;
    }

    @XmlElement
    public void setComment(String comment) {
        this.comment = comment;
    }

}
