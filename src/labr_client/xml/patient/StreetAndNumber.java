/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.patient;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import labr_client.xml.LabrXMLPatient;

/**
 *
 * @author LABBL
 */
@XmlRootElement(name = "streetAndNumber")
public class StreetAndNumber{

    int x;
    int y;
    int width;
    int length;
    int fontSize = 12;
      String value;

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
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

    public int getWidth() {
        return width;
    }

    @XmlAttribute
    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    @XmlAttribute
    public void setLength(int length) {
        this.length = length;
    }

    public int getFontSize() {
        return fontSize;
    }

    @XmlAttribute
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
