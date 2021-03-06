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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import labr_client.xml.Kmehr.elements.Hcparty.Hcparty;

/**
 *
 * @author De Mey Matthias
 */
public class Receiver {

    private List<Hcparty> hcparty = new ArrayList<>();

    public List<Hcparty> getHcparty() {
        return hcparty;
    }

    @XmlElement
    public void setHcparty(List<Hcparty> hcparty) {
        this.hcparty = hcparty;
    }

}
