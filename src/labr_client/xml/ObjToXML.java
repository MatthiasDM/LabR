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

import be.ehealth.technicalconnector.enumeration.Charset;
import java.awt.Component;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import labr_client.Public.PublicVars;
import labr_client.xml.Kmehr.KmehrMessage;
import labr_client.xml.Kmehr.elements.Cd;
import labr_client.xml.Kmehr.elements.Hcparty.Hcparty;
import labr_client.xml.Kmehr.elements.Item;
import labr_client.xml.Kmehr.elements.Transaction;

/**
 *
 * @author De Mey Matthias
 */
public class ObjToXML {

    public static void jaxbObjectToXML(LabrRequest request, HashMap<String, String> patient) {

        try {
            JAXBContext context = JAXBContext.newInstance(LabrRequest.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //----------PATIENT----------------------------
            request.patient.name.setValue("De Mey");
            request.patient.name.setX(20);
            request.patient.name.setY(50);
            request.patient.firstName.setValue("Matthias");
            request.patient.firstName.setX(150);
            request.patient.firstName.setY(50);
            request.patient.birthDate.setValue("07/06/1990");
            request.patient.birthDate.setX(20);
            request.patient.birthDate.setY(80);
            request.patient.gender.setValue("M");
            request.patient.gender.setX(150);
            request.patient.gender.setY(80);
            request.patient.straatAndNumber.setValue("Hemelbeekstraat 16");
            request.patient.straatAndNumber.setX(20);
            request.patient.straatAndNumber.setY(110);
            request.patient.zip.setValue("8000");
            request.patient.zip.setX(20);
            request.patient.zip.setY(140);
            request.patient.city.setValue("Brugge");
            request.patient.city.setX(120);
            request.patient.city.setY(140);

            request.patient.country.setValue("Belgie");
            request.patient.country.setX(20);
            request.patient.country.setY(170);
            request.patient.nationalNumber.setValue("001-12345-223");
            request.patient.nationalNumber.setX(120);
            request.patient.nationalNumber.setY(210);
            //----------ATTRIBUTEN-------------------------
            request.attributes.setAge(26);
            //----------LABELS-----------------------------
            LabrXMLLabel l = new LabrXMLLabel();
            l.setColor("#110000");
            l.setSize(12);
            l.setId("1");
            l.setValue("Hematologie");
            request.labels.getLabel().add(l);
            //---------KNOPPEN-----------------------------
            request.buttons.save.setX(300);
            request.buttons.save.setY(100);
            request.buttons.save.setColor(-65536);
            request.buttons.save.setWidth(100);
            request.buttons.save.setValue("Save");
            //----------AANVRAGEN--------------------------
            LabrXMLRequest r = new LabrXMLRequest();
            r.setComment("Dit is commentaar");
            r.setLoinc("Kalium");
            request.requests.getRequest().add(r);
            r.setLoinc("Natrium");
            request.requests.getRequest().add(r);
            //----------AANVRAGEN--------------------------
            // Write to System.out for debugging
            // m.marshal(emp, System.out);            
            // Write to File
            m.marshal(request, new File("C:\\Users\\labbl\\Documents\\test.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void jaxbObjectToXML(KmehrMessage kmehrMessage) {
        try {
            JAXBContext context = JAXBContext.newInstance(KmehrMessage.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //----------HEADER--------------------------
            kmehrMessage.header.id.setS("ID-KMEHR");
            kmehrMessage.header.id.setSV("1.15");
            kmehrMessage.header.id.setValue("0000000");
            kmehrMessage.header.setDate("20160519");
            kmehrMessage.header.setTime("09:40:31");
            kmehrMessage.header.standard.cd.setS("CD-STANDARD");
            kmehrMessage.header.standard.cd.setSV("1.15");
            kmehrMessage.header.standard.cd.setValue("20150901");
            //----------HEADER-ZENDER-------------------------
            kmehrMessage.header.sender.hcparty.id.setS("ID-HCPARTY");
            kmehrMessage.header.sender.hcparty.id.setSV("1.15");
            kmehrMessage.header.sender.hcparty.id.setValue("000000");
            kmehrMessage.header.sender.hcparty.cd.setS("CD-HCPARTY");
            kmehrMessage.header.sender.hcparty.cd.setSV("1.15");
            kmehrMessage.header.sender.hcparty.cd.setValue("persphysician");
            kmehrMessage.header.sender.hcparty.setFamilyname("Pieters");
            kmehrMessage.header.sender.hcparty.setFirstname("Piet");
            //----------HEADER-ONTVANGER(S)--------------------
            Hcparty receiver = new Hcparty();
            receiver.id.setS("ID-HCPARTY");
            receiver.id.setSV("1.15");
            receiver.id.setValue("000000");
            receiver.cd.setS("CD-HCPARTY");
            receiver.cd.setSV("1.15");
            receiver.cd.setValue("persphysician");
            receiver.setFamilyname("Pieters");
            receiver.setFirstname("Piet");
            kmehrMessage.header.receiver.getHcparty().add(receiver);
            //----------FOLDER-------------------------
            kmehrMessage.folder.id.setS("ID-HCPARTY");
            kmehrMessage.folder.id.setSV("1.15");
            kmehrMessage.folder.patient.setFamilyname("De Mey");
            kmehrMessage.folder.patient.setFirstname("Matthias");
            kmehrMessage.folder.patient.birthdate.setDate("1990/06/07");

            kmehrMessage.setXmlns("http://www.ehealth.fgov.be/standards/kmehr/schema/v1");

            m.marshal(kmehrMessage, new File("C:\\Users\\labbl\\Documents\\testKMEHR.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void saveProfile(Component[] comps, String profile) {

        try {
            if (comps != null) {
                JAXBContext context = JAXBContext.newInstance(LabrRequest.class);
                Marshaller m = context.createMarshaller();
                //for pretty-print XML in JAXB
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                LabrRequest request = new LabrRequest();
                for (Component comp : comps) {

                    if (comp.getName() != null) {

                        if (comp.getName().equals("name")) {
                            request.patient.name.setValue(((JTextField) comp).getText());
                            request.patient.name.setX(comp.getX());
                            request.patient.name.setY(comp.getY());
                            request.patient.name.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("firstName")) {
                            request.patient.firstName.setValue(((JTextField) comp).getText());
                            request.patient.firstName.setX(comp.getX());
                            request.patient.firstName.setY(comp.getY());
                            request.patient.firstName.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("birthDate")) {
                            request.patient.birthDate.setValue(((JFormattedTextField) comp).getText());
                            request.patient.birthDate.setX(comp.getX());
                            request.patient.birthDate.setY(comp.getY());
                            request.patient.birthDate.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("gender")) {
                            request.patient.gender.setValue((String) (((JComboBox) comp).getSelectedItem()));
                            request.patient.gender.setX(comp.getX());
                            request.patient.gender.setY(comp.getY());
                        } else if (comp.getName().equals("straatAndNumber")) {
                            request.patient.straatAndNumber.setValue(((JTextField) comp).getText());
                            request.patient.straatAndNumber.setX(comp.getX());
                            request.patient.straatAndNumber.setY(comp.getY());
                            request.patient.straatAndNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("zip")) {
                            request.patient.zip.setValue(((JTextField) comp).getText());
                            request.patient.zip.setX(comp.getX());
                            request.patient.zip.setY(comp.getY());
                            request.patient.zip.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("city")) {
                            request.patient.city.setValue(((JTextField) comp).getText());
                            request.patient.city.setX(comp.getX());
                            request.patient.city.setY(comp.getY());
                            request.patient.city.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("country")) {
                            request.patient.country.setValue((String) (((JComboBox) comp).getSelectedItem()));
                            request.patient.country.setX(comp.getX());
                            request.patient.country.setY(comp.getY());
                        } else if (comp.getName().equals("nationalNumber")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("Save")) {
                            JButton jbut = (JButton) comp;
                            ImageIcon icon = (ImageIcon) jbut.getIcon();
                            request.buttons.save.setX(comp.getX());
                            request.buttons.save.setY(comp.getY());
                            request.buttons.save.setValue("Save");
                            if (icon != null) {
                                request.buttons.save.setIcon(icon.getDescription());
                            }
                        } else if (comp.getName().equals("Search")) {
                        } else if (comp.getName().equals("saveAndSend")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("print")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else {
                            Class<? extends Component> c = comp.getClass();
                            if (c.getSimpleName().equals("JLabel")) {
                                JLabel lbl = (JLabel) comp;
                                LabrXMLLabel l = new LabrXMLLabel();
                                l.setColor(String.valueOf(lbl.getForeground().getRGB()));
                                l.setSize(lbl.getFont().getSize());
                                l.setId(lbl.getName());
                                l.setValue(lbl.getText());
                                l.setX(lbl.getX());
                                l.setY(lbl.getY());
                                request.labels.getLabel().add(l);
                            };
                            if (c.getSimpleName().equals("JCheckBox")) {
                                JCheckBox chbx = (JCheckBox) comp;
                                LabrXMLRequest req = new LabrXMLRequest();
                                req.setX(chbx.getX());
                                req.setY(chbx.getY());
                                req.setLoinc(chbx.getName());
                                req.setValue(chbx.getText());
                                req.setSelected(chbx.isSelected());
                                request.requests.getRequest().add(req);
                            };
                            if (c.getSimpleName().equals("JTextBox")) {

                            };
                        }
                    }

                }
                m.marshal(request, new File(PublicVars.getUserData()[9] + "\\" + profile + ".xml"));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static String marshallRequest(LabrRequest request) {
        String xml = "";
        String date = String.format("%1$tY%1$tm%1$td", new Date());
        String time = String.format("%1$tH%1$tM%1$tS", new Date());
        try {
            JAXBContext context = JAXBContext.newInstance(LabrRequest.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File labrRequest = new File("data/tmp/" + date + time + ".xml");
            m.marshal(request, labrRequest);
            xml = FileUtils.readFileToString(labrRequest);

        } catch (JAXBException | IOException ex) {
            Logger.getLogger(ObjToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xml;
    }

    public static String marshallRequest(KmehrMessage message) {
        String xml = "";
        String date = String.format("%1$tY%1$tm%1$td", new Date());
        String time = String.format("%1$tH%1$tM%1$tS", new Date());
        try {
            JAXBContext context = JAXBContext.newInstance(LabrRequest.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File kmehrMessage = new File("data/tmp/kmehr" + date + time + ".xml");
            m.marshal(message, kmehrMessage);
            xml = FileUtils.readFileToString(kmehrMessage);

        } catch (JAXBException | IOException ex) {
            Logger.getLogger(ObjToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xml;
    }

    public static LabrRequest saveLabrRequest(Component[] comps) {

        try {
            if (comps != null) {
                JAXBContext context = JAXBContext.newInstance(LabrRequest.class);
                Marshaller m = context.createMarshaller();
                //for pretty-print XML in JAXB
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                LabrRequest request = new LabrRequest();
                for (Component comp : comps) {

                    if (comp.getName() != null) {

                        if (comp.getName().equals("name")) {
                            request.patient.name.setValue(((JTextField) comp).getText());
                            request.patient.name.setX(comp.getX());
                            request.patient.name.setY(comp.getY());
                            request.patient.name.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("firstName")) {
                            request.patient.firstName.setValue(((JTextField) comp).getText());
                            request.patient.firstName.setX(comp.getX());
                            request.patient.firstName.setY(comp.getY());
                            request.patient.firstName.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("birthDate")) {
                            request.patient.birthDate.setValue(((JFormattedTextField) comp).getText());
                            request.patient.birthDate.setX(comp.getX());
                            request.patient.birthDate.setY(comp.getY());
                            request.patient.birthDate.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("gender")) {
                            request.patient.gender.setValue((String) (((JComboBox) comp).getSelectedItem()));
                            request.patient.gender.setX(comp.getX());
                            request.patient.gender.setY(comp.getY());
                        } else if (comp.getName().equals("straatAndNumber")) {
                            request.patient.straatAndNumber.setValue(((JTextField) comp).getText());
                            request.patient.straatAndNumber.setX(comp.getX());
                            request.patient.straatAndNumber.setY(comp.getY());
                            request.patient.straatAndNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("zip")) {
                            request.patient.zip.setValue(((JTextField) comp).getText());
                            request.patient.zip.setX(comp.getX());
                            request.patient.zip.setY(comp.getY());
                            request.patient.zip.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("city")) {
                            request.patient.city.setValue(((JTextField) comp).getText());
                            request.patient.city.setX(comp.getX());
                            request.patient.city.setY(comp.getY());
                            request.patient.city.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("country")) {
                            request.patient.country.setValue((String) (((JComboBox) comp).getSelectedItem()));
                            request.patient.country.setX(comp.getX());
                            request.patient.country.setY(comp.getY());
                        } else if (comp.getName().equals("nationalNumber")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("Save")) {
                            request.buttons.save.setX(comp.getX());
                            request.buttons.save.setY(comp.getY());
                            request.buttons.save.setValue("Save");
                        } else if (comp.getName().equals("Search")) {
                        } else if (comp.getName().equals("saveAndSend")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else if (comp.getName().equals("print")) {
                            request.patient.nationalNumber.setValue(((JTextField) comp).getText());
                            request.patient.nationalNumber.setX(comp.getX());
                            request.patient.nationalNumber.setY(comp.getY());
                            request.patient.nationalNumber.setWidth(comp.getWidth());
                        } else {
                            Class<? extends Component> c = comp.getClass();
                            if (c.getSimpleName().equals("JLabel")) {
                                JLabel lbl = (JLabel) comp;
                                LabrXMLLabel l = new LabrXMLLabel();
                                l.setColor(String.valueOf(lbl.getForeground().getRGB()));
                                l.setSize(lbl.getFont().getSize());
                                l.setId(lbl.getName());
                                l.setValue(lbl.getText());
                                l.setX(lbl.getX());
                                l.setY(lbl.getY());
                                request.labels.getLabel().add(l);
                            };
                            if (c.getSimpleName().equals("JCheckBox")) {
                                JCheckBox chbx = (JCheckBox) comp;
                                LabrXMLRequest req = new LabrXMLRequest();
                                req.setX(chbx.getX());
                                req.setY(chbx.getY());
                                req.setLoinc(chbx.getName());
                                req.setValue(chbx.getText());
                                req.setSelected(chbx.isSelected());
                                request.requests.getRequest().add(req);
                            };
                            if (c.getSimpleName().equals("JTextBox")) {

                            };
                        }
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
                String date = dateFormat.format(Calendar.getInstance().getTime());
                request.attributes.setTitle("LABR-" + PublicVars.getUserData()[5] + "-" + date);
                return request;
            }
            return null;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveKmehrRequest(LabrRequest request) {
        String date = String.format("%1$tY%1$tm%1$td", new Date());
        String time = String.format("%1$tH%1$tM%1$tS", new Date());
        try {
            KmehrMessage kmehrMessage = new KmehrMessage();
            JAXBContext context = JAXBContext.newInstance(KmehrMessage.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //----------HEADER--------------------------
            kmehrMessage.header.id.setS("ID-KMEHR");
            kmehrMessage.header.id.setSV("1.15");
            kmehrMessage.header.id.setValue(request.attributes.getTitle());
            kmehrMessage.header.setDate(date);
            kmehrMessage.header.setTime(time);
            kmehrMessage.header.standard.cd.setS("CD-STANDARD");
            kmehrMessage.header.standard.cd.setSV("1.15");
            kmehrMessage.header.standard.cd.setValue("20150901");
            //----------HEADER-ZENDER-------------------------
            kmehrMessage.header.sender.hcparty.id.setS("ID-HCPARTY");
            kmehrMessage.header.sender.hcparty.id.setSV("1.15");
            kmehrMessage.header.sender.hcparty.id.setValue(request.attributes.getSenderID());
            kmehrMessage.header.sender.hcparty.cd.setS("CD-HCPARTY");
            kmehrMessage.header.sender.hcparty.cd.setSV("1.15");
            kmehrMessage.header.sender.hcparty.cd.setValue("persphysician");
            kmehrMessage.header.sender.hcparty.setFamilyname("Pieters");
            kmehrMessage.header.sender.hcparty.setFirstname("Piet");
            //----------HEADER-ONTVANGER(S)--------------------
            Hcparty receiver = new Hcparty();
            receiver.id.setS("ID-HCPARTY");
            receiver.id.setSV("1.15");
            receiver.id.setValue(request.attributes.receiver1ID);
            receiver.cd.setS("CD-HCPARTY");
            receiver.cd.setSV("1.15");
            receiver.cd.setValue("persphysician");
            receiver.setFamilyname("Pieters");
            receiver.setFirstname("Piet");
            kmehrMessage.header.receiver.getHcparty().add(receiver);
            //----------FOLDER-PATIENT-------------------------
            kmehrMessage.folder.id.setS("ID-HCPARTY");
            kmehrMessage.folder.id.setSV("1.15");
            kmehrMessage.folder.id.setValue(request.patient.nationalNumber.getValue());
            kmehrMessage.folder.patient.setFamilyname(request.patient.name.getValue());
            kmehrMessage.folder.patient.setFirstname(request.patient.firstName.getValue());
            kmehrMessage.folder.patient.birthdate.setDate(request.patient.birthDate.getValue());
            kmehrMessage.folder.patient.sex.cd.setS("CD-SEX");
            kmehrMessage.folder.patient.sex.cd.setSV("1.15");
            kmehrMessage.folder.patient.sex.cd.setValue("M");
            kmehrMessage.folder.patient.address.cd.setS("CD-ADDRESS");
            kmehrMessage.folder.patient.address.cd.setSV("1.15");
            kmehrMessage.folder.patient.address.country.cd.setS("CD-COUNTRY");
            kmehrMessage.folder.patient.address.country.cd.setSV("1.15");
            kmehrMessage.folder.patient.address.country.cd.setValue(request.patient.country.getValue());
            kmehrMessage.folder.patient.address.setCity(request.patient.city.getValue());
            kmehrMessage.folder.patient.address.setStreetandnumber(request.patient.straatAndNumber.getValue());
            kmehrMessage.folder.patient.address.setZip(request.patient.zip.getValue());
            Transaction t = new Transaction();
            t.id.setS("ID-KMEHR");
            t.id.setSV("1.15");
            t.id.setValue("1");
            t.cd.setS("CD-TRANSACTION");
            t.cd.setSV("1.15");
            t.cd.setValue("request");
            t.setDate(date);
            t.setTime(time);
            t.author.hcparty.id.setS("ID-HCPARTY");
            t.author.hcparty.id.setSV("1.15");
            t.author.hcparty.id.setValue(request.attributes.getSenderID());
            t.author.hcparty.cd.setS("CD-HCPARTY");
            t.author.hcparty.cd.setSV("1.15");
            t.author.hcparty.cd.setValue("persphysician");
            t.author.hcparty.setFamilyname("Pieters");
            t.author.hcparty.setFirstname("Piet");
            Item i = new Item();
            i.id.setS("ID-KMEHR");
            i.id.setSV("1.15");
            i.id.setValue("1");
            i.cd.setS("CD-ITEM");
            i.cd.setSV("1.15");
            i.cd.setValue("lab");
            List<LabrXMLRequest> requests = request.requests.getRequest();
            for (LabrXMLRequest req : requests) {
                if (req.isSelected() & req.getLoinc() != null) {
                    Cd cd = new Cd();
                    cd.setS("CD-LAB");
                    cd.setSV("1.15");
                    cd.setValue(req.getLoinc());
                    i.content.getCd().add(cd);
                }

            }
            t.getItem().add(i);
            kmehrMessage.folder.getTransaction().add(t);
            kmehrMessage.setXmlns("http://www.ehealth.fgov.be/standards/kmehr/schema/v1");

            //START------ZOEK DE PATIENT OP EN BEWAAR AANVRAAG IN DATABASE-------------------------      
            File KmehrRequest = new File("data/tmp/" + date + time + ".xml");
            m.marshal(kmehrMessage, KmehrRequest);
            List<String[]> patientID = PublicVars.getQueries().selectPatient(request.patient.firstName.getValue(), request.patient.name.getValue(), request.patient.birthDate.getValue());
            if (patientID.size() == 0) {
                PublicVars.getQueries().insertPatient(request);
                patientID = PublicVars.getQueries().selectPatient(request.patient.firstName.getValue(), request.patient.name.getValue(), request.patient.birthDate.getValue());
                PublicVars.getQueries().insertLabRequest(marshallRequest(request), FileUtils.readFileToString(KmehrRequest), Integer.parseInt(patientID.get(0)[0]), Integer.parseInt(PublicVars.getUserData()[1]), kmehrMessage.header.id.getValue());
            } else {
                PublicVars.getQueries().insertLabRequest(marshallRequest(request), FileUtils.readFileToString(KmehrRequest), Integer.parseInt(patientID.get(0)[0]), Integer.parseInt(PublicVars.getUserData()[1]), kmehrMessage.header.id.getValue());
            }
            //EIND---------------------------------------------------------------------------------

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ObjToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
