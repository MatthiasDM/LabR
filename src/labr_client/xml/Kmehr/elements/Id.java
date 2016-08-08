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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author De Mey Matthias
 */
@XmlRootElement(name = "id")
public class Id {
    String S;
    String SV;
    String value;

    public String getValue() {
        return value;
    }
    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }
    
    
    public String getS() {
        return S;
    }
    @XmlAttribute
    public void setS(String S) {
        this.S = S;
    }

    public String getSV() {
        return SV;
    }
    @XmlAttribute
    public void setSV(String SV) {
        this.SV = SV;
    }
    
    
    
}
