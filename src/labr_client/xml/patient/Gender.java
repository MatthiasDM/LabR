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
package labr_client.xml.patient;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author De Mey Matthias
 */
@XmlRootElement(name = "gender")
public class Gender{

    int x;
    int y;
    int width;
    int length;
    int fontSize = 12;
    String value;

    public String getValue() {
        return value;
    }

    @XmlValue
    public void setValue(String value) {
        this.value = value;
    }

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

    public int getWidth() {
        return width;
    }

    @XmlAttribute
    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    @XmlAttribute
    public void setLength(int length) {
        this.length = length;
    }

    public int getFontSize() {
        return fontSize;
    }

    @XmlAttribute
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}