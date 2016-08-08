/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "kmehrmessage")
public class KmehrMessage {

    String xmlns;

    public String getXmlns() {
        return xmlns;
    }

    @XmlAttribute
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    @XmlElementRef(name = "header")
    public KmehrHeader header = new KmehrHeader();

    @XmlElementRef(name = "folder")
    public KmehrFolder folder = new KmehrFolder();

}
