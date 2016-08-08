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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author De Mey Matthias
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
