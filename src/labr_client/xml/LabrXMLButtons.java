/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import labr_client.xml.buttons.*;

/**
 *
 * @author labbl
 */
@XmlRootElement(name = "buttons")
public class LabrXMLButtons {

    @XmlElement
    public Save save = new Save();
 //   @XmlElement
   // public LookupPatient lookUpPatient = new LookupPatient();
//    @XmlElement
//    public SaveAndSend saveAndSend = new SaveAndSend();
//    @XmlElement
//    public Print print = new Print();

}
