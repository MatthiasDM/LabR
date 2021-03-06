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
package labr_client.ehealth;

import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import static be.ehealth.technicalconnector.service.ServiceFactory.getKeyDepotService;
import be.ehealth.technicalconnector.service.etee.domain.EncryptionToken;
import be.ehealth.technicalconnector.service.keydepot.KeyDepotManager;
import static be.ehealth.technicalconnector.service.keydepot.KeyDepotManagerFactory.getKeyDepotManager;
import be.ehealth.technicalconnector.service.keydepot.KeyDepotService;
import static be.ehealth.technicalconnector.session.Session.getInstance;
import be.ehealth.technicalconnector.utils.CertificateParser;
import be.ehealth.technicalconnector.utils.IdentifierType;
import static be.ehealth.technicalconnector.utils.IdentifierType.valueOf;
import be.fgov.ehealth.etkdepot._1_0.protocol.GetEtkRequest;
import be.fgov.ehealth.etkdepot._1_0.protocol.GetEtkResponse;
import be.fgov.ehealth.etkdepot._1_0.protocol.MatchingEtk;
import be.fgov.ehealth.etkdepot._1_0.protocol.SearchCriteriaType;
import static java.lang.Long.parseLong;
import java.lang.reflect.Field;
import static java.lang.reflect.Modifier.isStatic;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static labr_client.GUI.custom_classes.Dynamic_swing.infoBox;

/**
 *
 * @author De Mey Matthias
 */
public class e_health_key_depot {

    KeyDepotManager manager = getKeyDepotManager();
    String type = "";
    String id = "";

    public boolean testsearchETK(String idType, String idValue, String appId) {
        boolean found = false;
        type = idType;
        id = idValue;
        IdentifierType type = null;

        try {
            String results = "";
            type = valueOf(idType);

            if (appId == "") {
                appId = null;
            }
            manager = getKeyDepotManager();

            Set<EncryptionToken> etk = manager.getEtkSet(type, parseLong(idValue), appId);

            if (etk != null) {
                for (EncryptionToken encryptionToken : etk) {
                    results = encryptionToken.getAuthenticationCertificate().getSubjectDN().toString() + "\n";

                }

                infoBox(results, "Result");
                found = true;
                getLogger(e_health_key_depot.class.getName()).log(INFO, "Not found: [" + type + "] " + id);
            }

        } catch (TechnicalConnectorException ex) {

            getLogger(e_health_key_depot.class.getName()).log(INFO, "Not found: [" + type + "] " + id);
        }
        return found;
    }

    public void getIdentifierTypes(String idValue) {
        boolean found = false;
        int teller = 0;
        String qualities = "";

        Field[] fields = IdentifierType.class.getDeclaredFields();
        for (Field f : fields) {
            if (isStatic(f.getModifiers())) {
                Class<?> c = f.getType();
                String name = f.getName();
                found = testsearchETK(name, idValue, null);
                qualities += name + "\n";
                teller++;
                if (found) {
                    break;
                }
            }
        }
        infoBox("aantal overlopen: " + teller + "\n" + qualities, "Result");
    }

    public void checkCertificate() {

        try {

            CertificateParser certParser = new CertificateParser(getInstance().getSession().getSAMLToken().getCertificate());
            infoBox("App = " + certParser.getApplication() + "\n Type = " + certParser.getType() + "\n Value =" + certParser.getValue(), "Result");
            
        } catch (TechnicalConnectorException ex) {
            getLogger(e_health_key_depot.class.getName()).log(SEVERE, null, ex);
        }

    }

    private be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType createIdentifier(String value, String type, String applicationId) {
        be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType identifier = new be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType();
        //identifier.setApplicationID(applicationId);
        identifier.setType(type);
        identifier.setValue(value);
        return identifier;
    }

    public void searchETK(String idType, String idValue, String appId) throws Exception {

        GetEtkRequest request = new GetEtkRequest();
        // create the search criteria
        SearchCriteriaType searchCriteria = new SearchCriteriaType();
        // the request expects a list of identifiers to get the ETK for.
        // in this example we will provide a list with 1 item.
        List<be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType> listIdentifiers = new ArrayList<>();
        // create the identifier
        be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType identifier = new be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType();
        // set the application name
        identifier.setApplicationID(appId);
        // set the type (e.g. CBE)
        identifier.setType(idType);
        // set the value associated with the type
        identifier.setValue(idValue);
        // add the identifier to the list
        listIdentifiers.add(createIdentifier(idValue, idType, appId));
        // add the list to the search criteria
        searchCriteria.getIdentifiers().addAll(listIdentifiers);
        // add the search criteria to the request
        request.setSearchCriteria(searchCriteria);

        invokeKeyDepot(request);
    }

    private String invokeKeyDepot(GetEtkRequest request) throws TechnicalConnectorException, GeneralSecurityException {
        /*
         * Invoke the technical connector framework's ETK Service's getEtk operation
         */
        KeyDepotService service = getKeyDepotService();
        GetEtkResponse response = service.getETK(request);
        List<MatchingEtk> matches = response.getMatchingEtks();
        String applicationID = null, type = null, value = null;

        for (MatchingEtk match : matches) {
            List<be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType> idTypes = match.getIdentifiers();

            for (be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType idType : idTypes) {
                applicationID = idType.getApplicationID();
                type = idType.getType();
                value = idType.getValue();
            }
        }
        if (response.getETK() != null) {
            EncryptionToken token = new EncryptionToken(response.getETK());
            infoBox("applicationID: " + applicationID + "\n" + "type: " + type + "\n" + "value: " + value + "\n Name: " + token.getCertificate().getSubjectDN().getName(), "Result");
            return token.getCertificate().getSubjectDN().getName();
        } else {
            infoBox("applicationID: " + applicationID + "\n" + "type: " + type + "\n" + "value: " + value, "Result");
            return applicationID;
        }

    }
}
