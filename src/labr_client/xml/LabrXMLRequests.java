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
 * @author labbl
 */
public class LabrXMLRequests {

    private List<LabrXMLRequest> request = new ArrayList<LabrXMLRequest>();

    public List<LabrXMLRequest> getRequest() {
        return request;
    }

    @XmlElement
    public void setRequest(List<LabrXMLRequest> request) {
        this.request = request;
    }

}
