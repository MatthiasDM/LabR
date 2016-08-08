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
import labr_client.xml.patient.*;

/**
 *
 * @author LABBL
 */
@XmlRootElement(name = "patient")
public class LabrXMLPatient{

    @XmlElement
    public Name name = new Name();
    @XmlElement
    public FirstName firstName = new FirstName();
    @XmlElement
    public StreetAndNumber straatAndNumber = new StreetAndNumber();
    @XmlElement
    public Zip zip = new Zip();
    @XmlElement
    public City city = new City();
    @XmlElement
    public Gender gender = new Gender();
    @XmlElement
    public BirthDate birthDate = new BirthDate();
    @XmlElement
    public Country country = new Country();
    @XmlElement
    public NationalNumber nationalNumber = new NationalNumber();
}


