/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author LABBL
 */
public class LabrXMLLabels {

    private List<LabrXMLLabel> label = new ArrayList<LabrXMLLabel>();

    public List<LabrXMLLabel> getLabel() {
        return label;
    }

    @XmlElement
    public void setLabel(List<LabrXMLLabel> label) {
        this.label = label;
    }

}
