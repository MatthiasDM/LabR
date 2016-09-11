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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author De Mey Matthias
 */
public class LabrXMLLabel {

    String id;
    String color;
    int x;
    int y;
    int size;
    String value;

    public int getX() {
        return x;
    }

    @XmlAttribute
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    @XmlAttribute
    public void setY(int y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    @XmlAttribute
    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    @XmlAttribute
    public void setSize(int size) {
        this.size = size;
    }

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

}
