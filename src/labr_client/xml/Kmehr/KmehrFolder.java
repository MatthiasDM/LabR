/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author labbl
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
