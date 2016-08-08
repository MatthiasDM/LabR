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
package labr_client.xml.Kmehr.elements.Hcparty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.Kmehr.elements.Cd;
import labr_client.xml.Kmehr.elements.Id;

/**
 *
 * @author De Mey Matthias
 */
@XmlRootElement(name = "hcparty")
public class Hcparty {

    String firstname, familyname;
    @XmlElement
    public Cd cd = new Cd();
    @XmlElement
    public Id id = new Id();

    public String getFirstname() {
        return firstname;
    }
@XmlElement
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFamilyname() {
        return familyname;
    }
@XmlElement
    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }
    
    
    
}
