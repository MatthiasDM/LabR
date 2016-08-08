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
package labr_client.xml.Kmehr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.Kmehr.elements.Hcparty.Hcparty;
import labr_client.xml.Kmehr.elements.Id;
import labr_client.xml.Kmehr.elements.Patient;
import labr_client.xml.Kmehr.elements.Transaction;
import labr_client.xml.LabrXMLPatient;
import labr_client.xml.patient.Name;

/**
 *
 * @author De Mey Matthias
 */
@XmlRootElement(name = "folder")
public class KmehrFolder {

    @XmlElement
    public Id id = new Id();
    @XmlElement
    public Patient patient = new Patient();

    private List<Transaction> transaction = new ArrayList<Transaction>();

    public List<Transaction> getTransaction() {
        return transaction;
    }

    @XmlElement
    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

}
