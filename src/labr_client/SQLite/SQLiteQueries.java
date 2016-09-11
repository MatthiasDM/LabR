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
package labr_client.SQLite;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.GUI.custom_classes.Dynamic_swing;
import labr_client.Public.PublicVars;
import labr_client.xml.LabrRequest;
import org.junit.Assert;

/**
 *
 * @author De Mey Matthias
 */
public class SQLiteQueries {

    // UPDATE `Aanvraagprofielen` SET `posY`=? WHERE `_rowid_`='15';
    SQLiteQueryProcesser SQL;

    public SQLiteQueries() {
        SQL = new SQLiteQueryProcesser();
    }

    public void updateProfileName(String Profielnaam) {
        String statement = "UPDATE Profielnamen SET Profielnaam = '" + Profielnaam + "' WHERE GebruikersID = " + PublicVars.getUserID() + " AND ID = " + PublicVars.getProfielID();
        SQL.parseUpdateStatement(statement);
    }

    public void deleteProfile(int profileID) {
        if (Dynamic_swing.infoBox("Are you sure you want to delete this profile?", "Profile deletion") == 0) {
            String[] statements = {
                "delete from Profielnamen WHERE ProfielID = " + profileID + " AND GebruikersID = " + PublicVars.getUserID(),
                "delete from Aanvraagprofielen WHERE ProfielID = " + profileID + " AND GebruikersID = " + PublicVars.getUserID(),
                "delete from GroupLabel WHERE ID = " + profileID + " AND GebruikersID = " + PublicVars.getUserID()
            };

            try {
                for (String statement : statements) {
                    PreparedStatement preparedStatement = SQL.getC().prepareStatement(statement);
                    preparedStatement.execute();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SQLiteQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public List<String[]> selectLabel(String Tekst) {

        String statement = "Select * from GroupLabel WHERE ProfielID = " + PublicVars.getProfielID() + " and GebruikersID = " + PublicVars.getUserID() + " and Tekst = '" + Tekst + "'";
        String[] attributes = {"ID", "ProfielID", "posX", "posY", "Lettergrootte", "Kleur", "Tekst"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

        //
    }

    public List<String[]> selectLabelByID(String ID) {

        String statement = "Select * from GroupLabel WHERE ID = " + Integer.parseInt(ID);
        String[] attributes = {"ID", "ProfielID", "posX", "posY", "Lettergrootte", "Kleur", "Tekst"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

        //
    }

    public List<String[]> selectTest(List<String> testInfo) {
        String[] identifiers = {"LABELDU"};
        //String statement = "select ID from Patienten where Voornaam LIKE '" + patientInfo.get(0) + "%' AND " + "Naam LIKE '" + patientInfo.get(1) + "' AND " + "Geboortedatum LIKE '" + patientInfo.get(2) + "'";
        String statement = createSelectStatement("Bepalingen", testInfo, identifiers);
        if (statement != "select * from Bepalingen WHERE ") {
            String[] attributes = {"ID", "LOINCCODE", "LABELDU", "LOINCSYSTEM"};
            List<String[]> lines = new ArrayList<String[]>();
            lines = SQL.parseSelectStatement(statement, attributes);

            return lines;
        }
        return null;
    }

    public List<String[]> selectPatient(List<String> patientInfo) {
        String[] identifiers = {"Voornaam", "Naam", "StraatEnNummer", "Postcode", "Stad", "Geslacht", "Geboortedatum", "Land", "Rijksregisternummer"};
        //String statement = "select ID from Patienten where Voornaam LIKE '" + patientInfo.get(0) + "%' AND " + "Naam LIKE '" + patientInfo.get(1) + "' AND " + "Geboortedatum LIKE '" + patientInfo.get(2) + "'";
        String statement = createSelectStatement("Patienten", patientInfo, identifiers);
        if (statement != "select * from Patienten WHERE ") {
            String[] attributes = {"Voornaam", "Naam", "StraatEnNummer", "Postcode", "Stad", "Geslacht", "Geboortedatum", "Land", "Rijksregisternummer", "ID"};
            List<String[]> lines = new ArrayList<String[]>();
            lines = SQL.parseSelectStatement(statement, attributes);

            return lines;
        }
        return null;
    }

    public String createSelectStatement(String table, List<String> patientInfo, String[] identifiers) {
        String statement = "select * from " + table + " WHERE ";
        int c = 0;
        boolean first = true;
        for (String item : patientInfo) {
            if (!item.isEmpty()) {
                if (c > 0 & c < patientInfo.size() & first == false) {
                    statement += " AND ";
                }
                statement += identifiers[c] + " LIKE '" + item + "%'";
                first = false;
            }

            c++;
        }
        return statement;
    }

    public List<String[]> selectPatient(String Voornaam, String Naam, String Geboortedatum) {

        String statement = "select ID from Patienten where Voornaam = '" + Voornaam + "' AND " + "Naam = '" + Naam + "' AND " + "Geboortedatum = '" + Geboortedatum + "'";
        String[] attributes = {"ID"};
        List<String[]> lines;
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectAllRequests() {

        String statement = "SELECT * FROM Bepalingen";
        String[] attributes = {"ID", "LOINCCODE", "LABELDU", "LOINCSYSTEM"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);

        Assert.assertNotNull(lines);
        return lines;

        //
    }

    public List<String[]> selectSentRequests() {
//HIER WAS IK GEEINDIGD;
        String statement = "SELECT AanvraagLabR, Voornaam, Naam, Geboortedatum, Titel, Datum, Aanvragen.ID FROM Aanvragen INNER JOIN Patienten ON Patienten.ID = Aanvragen.PatientID WHERE GebruikersID = " + PublicVars.getUserID();
        String[] attributes = {"AanvraagLabR", "Voornaam", "Naam", "Geboortedatum", "Titel", "Datum", "ID"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectLabrRequest(int ID) {
//HIER WAS IK GEEINDIGD;
        String statement = "SELECT AanvraagLabR FROM Aanvragen WHERE ID = " + ID;
        String[] attributes = {"AanvraagLabR"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectSentMessage(int ID) {
//HIER WAS IK GEEINDIGD;
        String statement = "SELECT Aanvraag, Voornaam, Naam, Geboortedatum, Sender, Receiver, Titel, Datum FROM Aanvragen INNER JOIN Patienten ON Patienten.ID = Aanvragen.PatientID WHERE Aanvragen.ID = " + ID;
        String[] attributes = {"Aanvraag", "Voornaam", "Naam", "Geboortedatum", "Sender", "Receiver", "Titel", "Datum"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectRequestName(String requestMNEMONIC) {

        String statement = "Select ID from Profielnamen where GebruikersID = " + PublicVars.getUserID() + " and Profielnaam = ''";
        String[] attributes = {"ID"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectUserInfo() {

        String statement = "Select * from Gebruikers where ID = " + PublicVars.getUserID();
        String[] attributes = {"Gebruikersnaam", "Wachtwoord"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public List<String[]> selectUserInfoPass(String md5Pass, String userName) {

        //String statement = "Select * from Gebruikers WHERE Wachtwoord LIKE '" + md5Pass + "' AND Gebruikersnaam LIKE '" + userName + "'";
        String statement = "Select * from Gebruikers WHERE Gebruikersnaam LIKE '" + userName + "'";

        String[] attributes = {"Gebruikersnaam", "ID", "firstname", "lastname", "inss", "nihii", "keystore", "ehealthpass", "keystoreLocation", "profileLocation", "Wachtwoord"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);
        return lines;

    }

    public void insertPatient(LabrRequest request) {
        String statement = "INSERT INTO 'Patienten' ('Voornaam','Naam','StraatEnNummer','Postcode','Stad','Geslacht','Geboortedatum','Land','Rijksregisternummer') VALUES  ('"
                + request.patient.firstName.getValue()
                + "','" + request.patient.name.getValue()
                + "','" + request.patient.straatAndNumber.getValue()
                + "','" + request.patient.zip.getValue()
                + "','" + request.patient.city.getValue()
                + "','" + request.patient.gender.getValue()
                + "','" + request.patient.birthDate.getValue()
                + "','" + request.patient.country.getValue()
                + "','" + request.patient.nationalNumber.getValue() + "')";
        SQL.parseUpdateStatement(statement);
    }

    public void insertLabRequest(String requestLabR, String requestKmehr, int patientID, int userID, String titel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String statement = "INSERT INTO 'Aanvragen' ('PatientID','GebruikersID','AanvraagLabR','AanvraagKmehr','Titel','Datum') VALUES  (" + patientID + "," + userID + ",'" + requestLabR + "','" + requestKmehr + "','" + titel + "','" + date + "')";

        SQL.parseUpdateStatement(statement);
    }

    public List<String[]> loadProfiles() {

        String statement = "select ID, Profielnaam from Profielnamen where GebruikersID = " + PublicVars.getUserID();
        String[] attributes = {"ID", "Profielnaam"};
        List<String[]> lines = new ArrayList<String[]>();
        lines = SQL.parseSelectStatement(statement, attributes);
        Assert.assertNotNull(lines);

        return lines;

    }

    public void addPatientRequestToDatabase(int patientID) {

    }

    public void addPatientToDatabase(List<String> patientInfo) {

    }
}
