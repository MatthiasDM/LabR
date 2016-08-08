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
@XmlRootElement(name = "transaction")
public class Transaction {

    String date, time;
    private List<Item> item = new ArrayList<Item>();

    @XmlElement
    public Id id = new Id();
    @XmlElement
    public Cd cd = new Cd();
    @XmlElement
    public Author author = new Author();
    @XmlElement
    public Boolean iscomplete;
    @XmlElement
    public Boolean isvalidated;

    public String getDate() {
        return date;
    }

    @XmlElement
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    @XmlElement
    public void setTime(String time) {
        this.time = time;
    }

    public List<Item> getItem() {
        return item;
    }

    @XmlElement
    public void setItem(List<Item> item) {
        this.item = item;
    }

}
