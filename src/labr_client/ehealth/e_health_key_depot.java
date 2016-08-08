/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.ehealth;

import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.service.ServiceFactory;
import be.ehealth.technicalconnector.service.etee.domain.EncryptionToken;
import be.ehealth.technicalconnector.service.keydepot.KeyDepotManager;
import be.ehealth.technicalconnector.service.keydepot.KeyDepotManagerFactory;
import be.ehealth.technicalconnector.service.keydepot.KeyDepotService;
import be.ehealth.technicalconnector.session.Session;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.GUI.custom_classes.Dynamic_swing;
import be.ehealth.technicalconnector.utils.CertificateParser;
import be.ehealth.technicalconnector.utils.IdentifierType;
import be.fgov.ehealth.etkdepot._1_0.protocol.GetEtkRequest;
import be.fgov.ehealth.etkdepot._1_0.protocol.GetEtkResponse;
import be.fgov.ehealth.etkdepot._1_0.protocol.MatchingEtk;
import be.fgov.ehealth.etkdepot._1_0.protocol.SearchCriteriaType;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

/**
 *
 * @author labbl
 */
public class e_health_key_depot {

    KeyDepotManager manager = KeyDepotManagerFactory.getKeyDepotManager();
    String type = "";
    String id = "";

    public boolean testsearchETK(String idType, String idValue, String appId) {
        boolean found = false;
        type = idType;
        id = idValue;
        IdentifierType type = null;

        try {
            String results = "";
            type = IdentifierType.valueOf(idType);

            if (appId == "") {
                appId = null;
            }
            manager = KeyDepotManagerFactory.getKeyDepotManager();

            Set<EncryptionToken> etk = manager.getEtkSet(type, Long.parseLong(idValue), appId);

            if (etk != null) {
                for (EncryptionToken encryptionToken : etk) {
                    results = encryptionToken.getAuthenticationCertificate().getSubjectDN().toString() + "\n";

                }

                Dynamic_swing.infoBox(results, "Result");
                found = true;
                Logger.getLogger(e_health_key_depot.class.getName()).log(Level.INFO, "Not found: [" + type + "] " + id);
            }

        } catch (TechnicalConnectorException ex) {

            Logger.getLogger(e_health_key_depot.class.getName()).log(Level.INFO, "Not found: [" + type + "] " + id);
        }
        return found;
    }

    public void getIdentifierTypes(String idValue) {
        boolean found = false;
        int teller = 0;
        String qualities = "";

        Field[] fields = IdentifierType.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
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
        Dynamic_swing.infoBox("aantal overlopen: " + teller + "\n" + qualities, "Result");
    }

    public void checkCertificate() {

        try {

            CertificateParser certParser = new CertificateParser(Session.getInstance().getSession().getSAMLToken().getCertificate());
            Dynamic_swing.infoBox("App = " + certParser.getApplication() + "\n Type = " + certParser.getType() + "\n Value =" + certParser.getValue(), "Result");
            
        } catch (TechnicalConnectorException ex) {
            Logger.getLogger(e_health_key_depot.class.getName()).log(Level.SEVERE, null, ex);
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
        List<be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType> listIdentifiers = new ArrayList<be.fgov.ehealth.etkdepot._1_0.protocol.IdentifierType>();
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
        KeyDepotService service = ServiceFactory.getKeyDepotService();
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
            Dynamic_swing.infoBox("applicationID: " + applicationID + "\n" + "type: " + type + "\n" + "value: " + value + "\n Name: " + token.getCertificate().getSubjectDN().getName(), "Result");
            return token.getCertificate().getSubjectDN().getName();
        } else {
            Dynamic_swing.infoBox("applicationID: " + applicationID + "\n" + "type: " + type + "\n" + "value: " + value, "Result");
            return applicationID;
        }

    }
}
