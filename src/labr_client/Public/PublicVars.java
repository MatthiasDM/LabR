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

    public static void setIsEdit(Boolean _isEdit) {
        isEdit = _isEdit;
    }

    public static Properties geteHealthProperties() {
        return eHealthProperties;
    }

    public static void seteHealthProperties(Properties _eHealthProperties) {
        eHealthProperties = _eHealthProperties;
    }
       
    
    
    
    public static JPanel getLabRequestPanel() {
        return labRequestPanel;
    }

    public static void setLabRequestPanel(JPanel _labRequestPanel) {
        labRequestPanel = _labRequestPanel;
    }
    
       
    public static ComponentMover getCM() {
        return CM;
    }

    public static void setCM(ComponentMover _CM) {
        CM = _CM;
    }   
    
    
    public static SQLiteQueries getQueries() {
        return queries;
    }

    public static void setQueries(SQLiteQueries _queries) {
        queries = _queries;
    }
    
    
    
    public static String getMd5pass() {
        return md5pass;
    }

    public static void setMd5pass(String _md5pass) {
        md5pass = _md5pass;
    }    
       
    public static String[] getUserData() {
        return userData;
    }

    public static void setUserData(String[] _userData) {
        userData = _userData;
    }

    public static String geteHealthPropertiesLocation() {
        return eHealthPropertiesLocation;
    }

    public static void seteHealthPropertiesLocation(String _eHealthPropertiesLocation) {
        eHealthPropertiesLocation = _eHealthPropertiesLocation;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties _properties) {
        properties = _properties;
    }

    public static URL getProgramURL() {
        return programURL;
    }

    public static void setProgramURL(URL _programURL) {
        programURL = _programURL;
    }

    public static String getCurrentGroupLabel() {
        return currentGroupLabel;
    }

    public static void setCurrentGroupLabel(String _currentGroupLabel) {
        currentGroupLabel = _currentGroupLabel;
    }

    public static List<String[]> getLabelsInProfile() {
        return labelsInProfile;
    }

    public static void setLabelsInProfile(List<String[]> _labelsInProfile) {
        labelsInProfile = _labelsInProfile;
    }

    public static Point getPopUpClick() {
        return popUpClick;
    }

    public static void setPopUpClick(Point _popUpClick) {
        popUpClick = _popUpClick;
    }

    public static Component getSelectComponentOnLabRequestPanel() {
        return selectComponentOnLabRequestPanel;
    }

    public static void setSelectComponentOnLabRequestPanel(Component _selectComponentOnLabRequestPanel) {
        selectComponentOnLabRequestPanel = _selectComponentOnLabRequestPanel;
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

    public static void setRequestsInProfile(List<String[]> _requestsInProfile) {
        requestsInProfile = _requestsInProfile;
    }

    public static List<String[]> geteHealthMessageInfo() {
        return eHealthMessageInfo;
    }

    public static void seteHealthMessageInfo(List<String[]> _eHealthMessageInfo) {
        eHealthMessageInfo = _eHealthMessageInfo;
    }

    public static String getProfielID() {
        return profielID;
    }

    public static void setProfielID(String _profiel) {
        //List<String[]> lines = queries.selectProfileID(profileName);
//        PublicVars.profielID = Integer.parseInt(lines.get(0)[0]);
        profielID = (_profiel);
    }

    public static List<String[]> getProfielNamen() {
        return profielNamen;
    }

    public static void setProfielNamen(List<String[]> _profielNamen) {
        profielNamen = _profielNamen;
    }

    public static String getSenderName() {

        return sender.getId();
    }

    public static void setSenderName(String _senderName) {
        senderName = _senderName;
    }

    public static String getSenderApplicationName() {
        return senderApplicationName;
    }

    public static void setSenderApplicationName(String _senderApplicationName) {
        senderApplicationName = _senderApplicationName;
    }

    public static String getReceiverName() {
        return receiver.get(0).getId();
    }

    public static void setReceiverName(String _receiverName) {
        receiverName = _receiverName;
    }

    public static String getReceiverApplicationName() {
        return receiverApplicationName;
    }

    public static void setReceiverApplicationName(String _receiverApplicationName) {
        receiverApplicationName = _receiverApplicationName;
    }

    public static List<String> getPatientInformation() {
        return patientInformation;
    }

    public static void setPatientInformation(List<String> _patientInformation) {
        patientInformation = _patientInformation;
    }

    public static Addressee getSender() {
        return sender;
    }

    public static void setSender(Addressee _sender) {
        sender = _sender;
    }

    public static List<Addressee> getReceiver() {
        return receiver;
    }

    public static void setReceiver(List<Addressee> _receiver) {
        receiver = _receiver;
    }

    public static String getTitel() {
        return titel;
    }

    public static void setTitel(String _titel) {
        titel = _titel;
    }

    public static String getMimeType() {
        return mimeType;
    }

    public static void setMimeType(String _mimeType) {
        mimeType = _mimeType;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String _username) {
        username = _username;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String _userID) {
        userID = _userID;
    }

    public static List<String[]> getSentMessages() {
        return sentMessages;
    }

    public static void setSentMessages(List<String[]> _sentMessages) {
        sentMessages = _sentMessages;
    }

}
