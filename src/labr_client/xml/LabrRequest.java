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

/**
 *
 * @author labbl
 */
@XmlRootElement
public class LabrRequest {

    @XmlElementRef(name = "patient")
    public LabrXMLPatient patient = new LabrXMLPatient();

    @XmlElement(name = "attributes")
    public LabrXMLAttributes attributes = new LabrXMLAttributes();

    @XmlElement(name = "labels")
    public LabrXMLLabels labels = new LabrXMLLabels();
    
    @XmlElement(name = "buttons")
    public LabrXMLButtons buttons = new LabrXMLButtons();

    @XmlElement(name = "requests")
    public LabrXMLRequests requests = new LabrXMLRequests();

}
