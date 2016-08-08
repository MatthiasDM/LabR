/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml.Kmehr.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "content")
public class Content {

    private List<Cd> cd = new ArrayList<Cd>();

    public List<Cd> getCd() {
        return cd;
    }

    @XmlElement
    public void setCd(List<Cd> cd) {
        this.cd = cd;
    }

}
