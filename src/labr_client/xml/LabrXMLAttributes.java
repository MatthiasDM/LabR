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
package labr_client.xml;

import javax.xml.bind.annotation.*;

/**
 *
 * @author De Mey Matthias
 */
@XmlRootElement(name = "attributes")
public class LabrXMLAttributes {

    int age;
    boolean hiv;
    boolean prostateCancer;
    String senderID;
    String receiver1ID;
    String receiver2ID;
    String receiver3ID;
    String title;

    public String getTitle() {
        return title;
    }
    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    public String getReceiver1ID() {
        return receiver1ID;
    }

    @XmlElement
    public void setReceiver1ID(String receiver1ID) {
        this.receiver1ID = receiver1ID;
    }

    public String getReceiver2ID() {
        return receiver2ID;
    }

    @XmlElement
    public void setReceiver2ID(String receiver2ID) {
        this.receiver2ID = receiver2ID;
    }

    public String getReceiver3ID() {
        return receiver3ID;
    }

    @XmlElement
    public void setReceiver3ID(String receiver3ID) {
        this.receiver3ID = receiver3ID;
    }

    public String getSenderID() {
        return senderID;
    }

    @XmlElement
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHiv() {
        return hiv;
    }

    @XmlElement
    public void setHiv(boolean hiv) {
        this.hiv = hiv;
    }

    public boolean isProstateCancer() {
        return prostateCancer;
    }

    @XmlElement
    public void setProstateCancer(boolean prostateCancer) {
        this.prostateCancer = prostateCancer;
    }

}
