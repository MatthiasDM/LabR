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
package labr_client.Public;

import be.ehealth.businessconnector.ehbox.api.domain.Addressee;
import java.awt.Component;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.swing.JPanel;
import labr_client.GUI.custom_classes.ComponentMover;
import labr_client.SQLite.SQLiteQueries;

/**
 *
 * @author De Mey Matthias
 */
public class PublicVars {

    private static String senderName, senderApplicationName, receiverName, receiverApplicationName;
    private static List<String> patientInformation = new ArrayList();
    private static String[] userData;
    private static Addressee sender;
    private static List<Addressee> receiver;
    private static String titel, mimeType;
    private static String username;
    private static String userID;
    private static List<String[]> sentMessages;
    private static List<String[]> profielNamen;
    private static List<String[]> eHealthMessageInfo;
    private static List<String[]> requestsInProfile;
    private static List<String[]> labelsInProfile;
    private static String profielID;
    private static Component selectComponentOnLabRequestPanel;
    private static Point popUpClick;
    private static String currentGroupLabel;
    private static URL programURL;
    private static Properties properties = new Properties();
    private static Properties eHealthProperties = new Properties();
    private static String eHealthPropertiesLocation;
    private static String md5pass;
    private static SQLiteQueries queries;
    private static ComponentMover CM;
    private static JPanel labRequestPanel;
    private static Boolean isEdit = false;

    public static Boolean getIsEdit() {
        return isEdit;
    }

    public static void setIsEdit(Boolean isEdit) {
        PublicVars.isEdit = isEdit;
    }

    public static Properties geteHealthProperties() {
        return eHealthProperties;
    }

    public static void seteHealthProperties(Properties eHealthProperties) {
        PublicVars.eHealthProperties = eHealthProperties;
    }
       
    
    
    
    public static JPanel getLabRequestPanel() {
        return labRequestPanel;
    }

    public static void setLabRequestPanel(JPanel labRequestPanel) {
        PublicVars.labRequestPanel = labRequestPanel;
    }
    
       
    public static ComponentMover getCM() {
        return CM;
    }

    public static void setCM(ComponentMover CM) {
        PublicVars.CM = CM;
    }   
    
    
    public static SQLiteQueries getQueries() {
        return queries;
    }

    public static void setQueries(SQLiteQueries queries) {
        PublicVars.queries = queries;
    }
    
    
    
    public static String getMd5pass() {
        return md5pass;
    }

    public static void setMd5pass(String md5pass) {
        PublicVars.md5pass = md5pass;
    }    
       
    public static String[] getUserData() {
        return userData;
    }

    public static void setUserData(String[] userData) {
        PublicVars.userData = userData;
    }

    public static String geteHealthPropertiesLocation() {
        return eHealthPropertiesLocation;
    }

    public static void seteHealthPropertiesLocation(String eHealthPropertiesLocation) {
        PublicVars.eHealthPropertiesLocation = eHealthPropertiesLocation;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PublicVars.properties = properties;
    }

    public static URL getProgramURL() {
        return programURL;
    }

    public static void setProgramURL(URL programURL) {
        PublicVars.programURL = programURL;
    }

    public static String getCurrentGroupLabel() {
        return currentGroupLabel;
    }

    public static void setCurrentGroupLabel(String currentGroupLabel) {
        PublicVars.currentGroupLabel = currentGroupLabel;
    }

    public static List<String[]> getLabelsInProfile() {
        return labelsInProfile;
    }

    public static void setLabelsInProfile(List<String[]> labelsInProfile) {
        PublicVars.labelsInProfile = labelsInProfile;
    }

    public static Point getPopUpClick() {
        return popUpClick;
    }

    public static void setPopUpClick(Point popUpClick) {
        PublicVars.popUpClick = popUpClick;
    }

    public static Component getSelectComponentOnLabRequestPanel() {
        return selectComponentOnLabRequestPanel;
    }

    public static void setSelectComponentOnLabRequestPanel(Component selectComponentOnLabRequestPanel) {
        PublicVars.selectComponentOnLabRequestPanel = selectComponentOnLabRequestPanel;
    }

//    public static boolean isEditRequestForm() {
//        return editRequestForm;
//    }
//
//    public static void setEditRequestForm(boolean editRequestForm) {
//        PublicVars.editRequestForm = editRequestForm;
//    }
    public static List<String[]> getRequestsInProfile() {
        return requestsInProfile;
    }

    public static void setRequestsInProfile(List<String[]> requestsInProfile) {
        PublicVars.requestsInProfile = requestsInProfile;
    }

    public static List<String[]> geteHealthMessageInfo() {
        return eHealthMessageInfo;
    }

    public static void seteHealthMessageInfo(List<String[]> eHealthMessageInfo) {
        PublicVars.eHealthMessageInfo = eHealthMessageInfo;
    }

    public static String getProfielID() {
        return profielID;
    }

    public static void setProfielID(String profiel) {
        //List<String[]> lines = queries.selectProfileID(profileName);
//        PublicVars.profielID = Integer.parseInt(lines.get(0)[0]);
        PublicVars.profielID = (profiel);
    }

    public static List<String[]> getProfielNamen() {
        return profielNamen;
    }

    public static void setProfielNamen(List<String[]> profielNamen) {
        PublicVars.profielNamen = profielNamen;
    }

    public static String getSenderName() {

        return sender.getId();
    }

    public static void setSenderName(String senderName) {
        PublicVars.senderName = senderName;
    }

    public static String getSenderApplicationName() {
        return senderApplicationName;
    }

    public static void setSenderApplicationName(String senderApplicationName) {
        PublicVars.senderApplicationName = senderApplicationName;
    }

    public static String getReceiverName() {
        return receiver.get(0).getId();
    }

    public static void setReceiverName(String receiverName) {
        PublicVars.receiverName = receiverName;
    }

    public static String getReceiverApplicationName() {
        return receiverApplicationName;
    }

    public static void setReceiverApplicationName(String receiverApplicationName) {
        PublicVars.receiverApplicationName = receiverApplicationName;
    }

    public static List<String> getPatientInformation() {
        return patientInformation;
    }

    public static void setPatientInformation(List<String> patientInformation) {
        PublicVars.patientInformation = patientInformation;
    }

    public static Addressee getSender() {
        return sender;
    }

    public static void setSender(Addressee sender) {
        PublicVars.sender = sender;
    }

    public static List<Addressee> getReceiver() {
        return receiver;
    }

    public static void setReceiver(List<Addressee> receiver) {
        PublicVars.receiver = receiver;
    }

    public static String getTitel() {
        return titel;
    }

    public static void setTitel(String titel) {
        PublicVars.titel = titel;
    }

    public static String getMimeType() {
        return mimeType;
    }

    public static void setMimeType(String mimeType) {
        PublicVars.mimeType = mimeType;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PublicVars.username = username;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        PublicVars.userID = userID;
    }

    public static List<String[]> getSentMessages() {
        return sentMessages;
    }

    public static void setSentMessages(List<String[]> sentMessages) {
        PublicVars.sentMessages = sentMessages;
    }

}
