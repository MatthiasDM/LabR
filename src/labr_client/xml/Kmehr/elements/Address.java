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
package labr_client.xml.Kmehr.elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author De Mey Matthias
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
