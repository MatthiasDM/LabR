/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.Kmehr.elements.*;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "header")
public class KmehrHeader {

    String date, time;

    public String getDate() {
        return date;
    }

    @XmlElement
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    @XmlElement
    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement
    public Id id = new Id();

    @XmlElement
    public Standard standard = new Standard();

    @XmlElement
    public Sender sender = new Sender();
    @XmlElement
    public Receiver receiver = new Receiver();

}
