/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import labr_client.xml.Kmehr.elements.Hcparty.Hcparty;

/**
 *
 * @author labbl
 */
public class Receiver {

    private List<Hcparty> hcparty = new ArrayList<Hcparty>();

    public List<Hcparty> getHcparty() {
        return hcparty;
    }

    @XmlElement
    public void setHcparty(List<Hcparty> hcparty) {
        this.hcparty = hcparty;
    }

}
