/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import javax.xml.bind.annotation.*;

/**
 *
 * @author LABBL
 */
@XmlRootElement(name = "attributes")
public class LabrXMLAttributes {

    int age;
    boolean hiv;
    boolean prostateCancer;
    String senderID;
    String receiver1ID;
    String receiver2ID;
    String receiver3ID;
    String title;

    public String getTitle() {
        return title;
    }
    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    public String getReceiver1ID() {
        return receiver1ID;
    }

    @XmlElement
    public void setReceiver1ID(String receiver1ID) {
        this.receiver1ID = receiver1ID;
    }

    public String getReceiver2ID() {
        return receiver2ID;
    }

    @XmlElement
    public void setReceiver2ID(String receiver2ID) {
        this.receiver2ID = receiver2ID;
    }

    public String getReceiver3ID() {
        return receiver3ID;
    }

    @XmlElement
    public void setReceiver3ID(String receiver3ID) {
        this.receiver3ID = receiver3ID;
    }

    public String getSenderID() {
        return senderID;
    }

    @XmlElement
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHiv() {
        return hiv;
    }

    @XmlElement
    public void setHiv(boolean hiv) {
        this.hiv = hiv;
    }

    public boolean isProstateCancer() {
        return prostateCancer;
    }

    @XmlElement
    public void setProstateCancer(boolean prostateCancer) {
        this.prostateCancer = prostateCancer;
    }

}
