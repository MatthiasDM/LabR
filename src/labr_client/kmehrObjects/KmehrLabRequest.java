/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.kmehrObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import labr_client.GUI.custom_classes.Dynamic_swing;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Calendar;
import labr_client.GUI.forms.MainWindow;
import labr_client.Public.PublicVars;

/**
 *
 * @author labbl
 */
public class KmehrLabRequest extends KmehrGenerate {

    //--------------------------------------------------------'
    private Document doc;
    private String xmlToString;
    private List<Element> elements = new ArrayList<Element>();
    private int counter = 0;
    private static final String SV = "1.15";
    private static final String STANDARD_VERSION = "20150901";
    //---------------------------------------------------------

    private List<String> requests = new ArrayList<String>();
    private List<String> subject = new ArrayList<String>();
    private String sender = null;
    private String receiver = null;
    private String title = null;
    
    public KmehrLabRequest() {
        
        subject = PublicVars.getPatientInformation();
        doc = createDoc();

    }

    public String getXmlToString() {
        xmlToString = XmlToString(doc);
        return xmlToString;
    }

    public Element AddElement(String name, String id, Element parent, HashMap<String, String> attrs) {
        Element e = doc.createElement(name);
        //e.setAttribute("id", id + counter);
        counter++;
        if (attrs != null) {
            for (Map.Entry<String, String> entry : attrs.entrySet()) {
                e.setAttribute(entry.getKey(), entry.getValue());
            }
        }
        //
        if (parent != null) {
            parent.appendChild(e);
        } else {
            doc.appendChild(e);
        }
        return e;
    }

    public String printLabRequest(List<String> reqs) {
        requests = reqs;
        HashMap<String, String> attr = new HashMap<String, String>();
        attr.put("xmlns", "http://www.ehealth.fgov.be/standards/kmehr/schema/v1");
        elements.add(AddElement("kmehrmessage", "kmehrmessage", null, attr));//////////////////////////0
        addHeader();///////////////////////////////////////////////////////////////////////////////////1-14
        addFolder(0);//////////////////////////////////////////////////////////////////////////////////15,16        
        
        return getXmlToString();
    }

    public void addHeader() {
        HashMap<String, String> attr = new HashMap<String, String>();
        elements.add(AddElement("header", "header", elements.get(elements.size() - 1), null)); //////////////////////////1
        elements.add(AddElement("standard", "standard", elements.get(elements.size() - 1), null));///////////////////////2
        //attr.put("SV", SV);
        // attr.put("S", "CD-STANDARD");
        //elements.add(AddElement("cd", "cd", elements.get(elements.size() - 1), attr));///////////////////////////////////3
        //elements.get(3).setTextContent(STANDARD_VERSION);
        addCD(elements.size() - 1, "CD-STANDARD", STANDARD_VERSION);
        addID(1, "ID-KMEHR", "0000000");///////////////////////////////////////////////////////////////////////4
        addDate(1);
        addTime(1);
        ///////////////SENDER//////////////////////////////////////////////////////////////////        
        elements.add(AddElement("sender", "sender", elements.get(elements.size() - 6), null));///////////////////////////7
        addHcParty(elements.size() - 1, "000000", "persphysician", "Piet", "Pieters");
        ///////////////RECEIVER////////////////////////////////////////////////////////////////
        elements.add(AddElement("receiver", "receiver", elements.get(elements.size() - 12), null));///////////////////////11
        addHcParty(elements.size() - 1, "000000", "persphysician", "Piet", "Pieters");

    }

    public void addFolder(int parent) {
        elements.add(AddElement("folder", "folder", elements.get(parent), null));
        addID(elements.size() - 1, "ID-KMEHR", "1");
        addPatient(elements.size() - 2);////////////////////////////////////////////////////////////////////////////////17-18-19-20-21-22
        addTransaction(elements.size() - 17);
    }

    public void addPatient(int parent) {
        HashMap<String, String> attr = new HashMap<String, String>();       
        elements.add(AddElement("patient", "patient", elements.get(parent), null));//1
        attr.put("SV", SV);
        attr.put("S", "ID-PATIENT");
        elements.add(AddElement("id", "id", elements.get(elements.size() - 1), attr));//2
        elements.get(elements.size() - 1).setTextContent("0000000000");
        elements.add(AddElement("firstname", "firstname", elements.get(elements.size() - 2), null));//3
        if (subject.size() > 0) {
            elements.get(elements.size() - 1).setTextContent(subject.get(0));
        }
        elements.add(AddElement("familyname", "familyname", elements.get(elements.size() - 3), null));//4
        if (subject.size() > 1) {
            elements.get(elements.size() - 1).setTextContent(subject.get(1));
        }
        elements.add(AddElement("birthdate", "birthdate", elements.get(elements.size() - 4), null));//5
        elements.add(AddElement("date", "date", elements.get(elements.size() - 1), null));//6
        if (subject.size() > 6) {
            elements.get(elements.size() - 1).setTextContent(subject.get(6));
        }
        if (subject.size() > 5) {
            addSex(elements.size() - 6, subject.get(5));
        }//7,8
        elements.add(AddElement("address", "address", elements.get(elements.size() - 8), null));//9
        addCD(elements.size() - 1, "CD-ADDRESS", "home");//10//1
        elements.add(AddElement("country", "country", elements.get(elements.size() - 2), null));//11//2
        if (subject.size() > 7) {
            addCD(elements.size() - 1, "CD-FED-COUNTRY", subject.get(7));
        }//12//3
        elements.add(AddElement("zip", "zip", elements.get(elements.size() - 4), null));//13//4
        if (subject.size() > 3) {
            elements.get(elements.size() - 1).setTextContent(subject.get(3));
        }
        elements.add(AddElement("city", "city", elements.get(elements.size() - 5), null));//14//5
        if (subject.size() > 4) {
            elements.get(elements.size() - 1).setTextContent(subject.get(4));
        }
        elements.add(AddElement("straatandnumber", "straatandnumber", elements.get(elements.size() - 6), null));//15//6
        if (subject.size() > 2) {
            elements.get(elements.size() - 1).setTextContent(subject.get(2));
        }
        //elements.add(AddElement("nationality", "nationality", elements.get(parent), null));
        //addCD(elements.size() - 1, "CD-FED-COUNTRY", subject.get(7));
    }

    public void addSex(int parent, String s) {
        HashMap<String, String> attr = new HashMap<String, String>();
        elements.add(AddElement("sex", "sex", elements.get(parent), null));
        addCD(elements.size() - 1, "CD-SEX", s);

    }

    public void addItem(int parent, int id, String cd) {
        elements.add(AddElement("item", "item", elements.get(parent), null));
        addID(elements.size() - 1, "ID-KMEHR", String.valueOf(id));
        addCD(elements.size() - 2, "CD-ITEM", cd);
        elements.add(AddElement("content", "content", elements.get(elements.size() - 3), null));
        addCD(elements.size() - 1, "CD-LAB", "2951-2"); ///hierin moet de magie gebeuren
    }

    public void addLabItems(int parent, int id) {

        elements.add(AddElement("item", "item", elements.get(parent), null));
        addID(elements.size() - 1, "ID-KMEHR", String.valueOf(id));
        addCD(elements.size() - 2, "CD-ITEM", "lab");
        elements.add(AddElement("content", "content", elements.get(elements.size() - 3), null));
        int i = 1;
        for (String request : requests) {
            addCD(elements.size() - i, "CD-LAB", request); ///hierin moet de magie gebeuren
            i++;
        }

    }

    public void addTransaction(int parent) {
        elements.add(AddElement("transaction", "transaction", elements.get(parent), null));
        addID(elements.size() - 1, "ID-KMEHR", "1");
        addCD(elements.size() - 2, "CD-TRANSACTION", "request");
        addDate(elements.size() - 3);
        addTime(elements.size() - 4);
        elements.add(AddElement("author", "author", elements.get(elements.size() - 5), null));
        addHcParty(elements.size() - 1, "0000000", "persphysician", "Piet", "Pieters");
        elements.add(AddElement("iscomplete", "iscomplete", elements.get(elements.size() - 11), null));
        elements.add(AddElement("isvalidated", "isvalidated", elements.get(elements.size() - 12), null));
        //addItem(elements.size()-10);
        addLabItems(elements.size() - 13, 1);
    }

    public void addTransactions() {
    }

    public void addHcParty(int parent, String id, String cd, String firstName, String familyName) {
        int count = 0;
        HashMap<String, String> attr = new HashMap<String, String>();
        elements.add(AddElement("hcparty", "hcparty", elements.get(parent), null));/////////////////////////8
        count++;
        attr.clear();
        attr.put("SV", SV);
        attr.put("S", "ID-HCPARTY");
        elements.add(AddElement("id", "id", elements.get(elements.size() - count), attr));///////////////////////////////////9
        count++;
        elements.get(elements.size() - 1).setTextContent(id);
        attr.clear();
        attr.put("SV", SV);
        attr.put("S", "CD-HCPARTY");
        elements.add(AddElement("cd", "cd", elements.get(elements.size() - count), attr));///////////////////////////////////10
        count++;
        elements.get(elements.size() - 1).setTextContent(cd);
        if (firstName != null) {
            elements.add(AddElement("firstname", "firstname", elements.get(elements.size() - count), null));///////////////////////////////////10
            elements.get(elements.size() - 1).setTextContent(firstName);
            count++;
        }
        if (familyName != null) {
            elements.add(AddElement("familyname", "familyname", elements.get(elements.size() - count), null));///////////////////////////////////10
            elements.get(elements.size() - 1).setTextContent(familyName);
            count++;
        }

    }

    public void addID(int parent, String idName, String idValue) {
        HashMap<String, String> attr = new HashMap<String, String>();
        attr.clear();
        attr.put("SV", SV);
        attr.put("S", idName);
        elements.add(AddElement("id", "id", elements.get(parent), attr));
        elements.get(elements.size() - 1).setTextContent(idValue);
    }

    public void addCD(int parent, String idName, String idValue) {
        HashMap<String, String> attr = new HashMap<String, String>();
        attr.clear();
        attr.put("SV", SV);
        attr.put("S", idName);
        elements.add(AddElement("cd", "cd", elements.get(parent), attr));
        elements.get(elements.size() - 1).setTextContent(idValue);
    }

    public void addDate(int parent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        elements.add(AddElement("date", "date", elements.get(parent), null));///////////////////////////////5
        elements.get(elements.size() - 1).setTextContent(dateFormat.format(Calendar.getInstance().getTime()));
    }

    public void addTime(int parent) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        elements.add(AddElement("time", "time", elements.get(parent), null));///////////////////////////////6
        elements.get(elements.size() - 1).setTextContent(timeFormat.format(Calendar.getInstance().getTime()));
    }
//
    

//    public void AddElement(Element parent, String name, HashMap<String, String> attr) {
//        doc = xmlMethods.AddElement(doc, parent, name, attr);
//    }
//
//    public void AddElement(Element parent, String name) {
//        doc = xmlMethods.AddElement(doc, parent, name);
//    }
//
//    public void AddElement(String name, HashMap<String, String> attr, boolean MakeChildOfPrevious) {
//        if (MakeChildOfPrevious) {
//            Element parent = (Element) doc.getLastChild();
//            doc = xmlMethods.AddElement(doc, parent, name, attr);
//        } else {
//            doc = xmlMethods.AddElement(doc, name, attr);
//        }
//    }
//
//    public void AddElement(String name, boolean MakeChildOfPrevious) {
//        if (MakeChildOfPrevious) {
//            Element parent = (Element) doc.getLastChild();
//            doc = xmlMethods.AddElement(doc, parent, name);
//        } else {
//            doc = xmlMethods.AddElement(doc, name);
//        }
//    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
