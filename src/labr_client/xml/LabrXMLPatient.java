/* 
 * Copyright (C) 2016 De Mey Matthias
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * @author De Mey Matthias
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


