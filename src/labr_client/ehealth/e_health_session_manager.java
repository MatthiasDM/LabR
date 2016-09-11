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

import be.ehealth.businessconnector.ehbox.api.domain.Addressee;
import static be.ehealth.technicalconnector.config.ConfigFactory.getConfigValidator;
import static be.ehealth.technicalconnector.config.ConfigFactory.setConfigLocation;
import be.ehealth.technicalconnector.config.ConfigValidator;
import be.ehealth.technicalconnector.exception.SessionManagementException;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.service.sts.SAMLTokenFactory;
import be.ehealth.technicalconnector.service.sts.security.SAMLToken;
import be.ehealth.technicalconnector.service.sts.security.impl.KeyStoreCredential;
import static be.ehealth.technicalconnector.service.sts.utils.SAMLConverter.toElement;
import static be.ehealth.technicalconnector.service.sts.utils.SAMLConverter.toXMLString;
import be.ehealth.technicalconnector.session.Session;
import be.ehealth.technicalconnector.session.SessionItem;
import be.ehealth.technicalconnector.session.SessionManager;
import be.ehealth.technicalconnector.utils.CertificateParser;
import static be.ehealth.technicalconnector.utils.IdentifierType.NIHII_LABO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static labr_client.GUI.custom_classes.Dynamic_swing.infoBox;
import static labr_client.Public.PublicVars.getUserData;
import static labr_client.Public.PublicVars.geteHealthPropertiesLocation;
import static labr_client.Public.PublicVars.setSender;
import static labr_client.Public.encryption.decodeBase64Aes;
import org.apache.commons.io.IOUtils;
import static org.apache.commons.io.IOUtils.write;
import org.w3c.dom.Element;

/**
 *
 * @author De Mey Matthias
 */
public class e_health_session_manager {



    private static String type, id, app;

    public e_health_session_manager() {
        File config_file = new File("data\\" + geteHealthPropertiesLocation());      
        try {
            setConfigLocation(config_file.getAbsoluteFile().getAbsolutePath());
            setConfigLocation("data/tmp/eHealthProps.properties");
         
            //ConfigFactory.getConfigValidator().setProperty("endpoint.sts", "https://services-int.ehealth.fgov.be/IAM/Saml11TokenService/Legacy/v1"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.ehbox.consultation.v3", "https://services-int.ehealth.fgov.be/ehBoxConsultation/v3"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.ehbox.publication.v3", "https://services-int.ehealth.fgov.be/ehBoxPublication/v3"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.etk", "https://services-int.ehealth.fgov.be/EtkDepot/v1"); 

        } catch (TechnicalConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);

        }

    }

    public Addressee getSender() throws TechnicalConnectorException {
        CertificateParser certParser = new CertificateParser(Session.getInstance().getSession().getSAMLToken().getCertificate());
        Addressee addressee = new Addressee(NIHII_LABO);
        addressee.setId(id);//90060717196   83166909
        addressee.setQuality("LABO");
        addressee.setApplicationId(app);
        return addressee;
    }

    public boolean start_session() {
        boolean successfull = init_session();
        if (successfull) {
            SessionManager sessionmgmt = Session.getInstance();
            try {
                CertificateParser certParser = new CertificateParser(Session.getInstance().getSession().getSAMLToken().getCertificate());
                type = certParser.getType();
                app = certParser.getApplication();
                id = certParser.getValue();
                setSender(getSender());
            } catch (TechnicalConnectorException ex) {
                getLogger(e_health_key_depot.class.getName()).log(SEVERE, null, ex);
            }

        }
        return successfull;
    }

    public boolean init_session() {
        String hokPassword = null;
        try {
            hokPassword = decodeBase64Aes(getUserData()[7]);
        } catch (GeneralSecurityException ex) {
            getLogger(e_health_session_manager.class.getName()).log(SEVERE, null, ex);
        }
        String[] userData = getUserData();
        getConfigValidator().setProperty("user.firstname", userData[2]); 
        getConfigValidator().setProperty("user.lastname", userData[3]); 
        getConfigValidator().setProperty("user.inss", userData[4]); 
        getConfigValidator().setProperty("user.nihii", userData[5]); 
        getConfigValidator().setProperty("sessionmanager.holderofkey.keystore", userData[6]);
        getConfigValidator().setProperty("sessionmanager.identification.keystore", userData[6]);
        getConfigValidator().setProperty("sessionmanager.encryption.keystore", userData[6]);
        SessionManager sessionmgmt = Session.getInstance();
        Map<String, String> keystores = new HashMap<>(3);
        boolean valid_session = false;
        try {
            valid_session = sessionmgmt.hasValidSession();
            if (!valid_session) {
                sessionmgmt.createFallbackSession(hokPassword);
                //sessionmgmt.createSession(hokPassword, hokPassword);
            } else {
                if (infoBox("A Session already exists, do you want to close the old session and create a new one?", "Session already exists") == 0) {
                    sessionmgmt.unloadSession();
                    //sessionmgmt.createSession(hokPassword, persPassword);
                    sessionmgmt.createFallbackSession(hokPassword, hokPassword, hokPassword);
                }
            }
            valid_session = sessionmgmt.hasValidSession();
        } catch (SessionManagementException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        } catch (TechnicalConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        } catch (Exception ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        }

        return valid_session;
    }

    private static void storeAndReload(String indentPwd, String hokPwd, String encPwd, SessionManager sessionmgmt) throws IOException, TechnicalConnectorException {
        SessionItem item = sessionmgmt.getSession();

        Element originalAssertion = item.getSAMLToken().getAssertion();
        String serializedToken = toXMLString(originalAssertion);

        File temp = new File("tempToken");
        temp.deleteOnExit();

        write(serializedToken.getBytes(), new FileOutputStream(temp));

        sessionmgmt.unloadSession();

        Element savedAssertion = toElement(IOUtils.toString(new FileReader(temp)));

        ConfigValidator config = getConfigValidator();

        String hokKeystore = config.getProperty("sessionmanager.holderofkey.keystore");
        String hokAlias = config.getProperty("sessionmanager.holderofkey.alias", "authentication");

        SAMLToken token = SAMLTokenFactory.getInstance().createSamlToken(savedAssertion, new KeyStoreCredential(hokKeystore, hokAlias, hokPwd));

        sessionmgmt.loadSession(token, hokPwd, encPwd);
    }

    public void close_session() {
        SessionManager sessionmgmt = Session.getInstance();
        sessionmgmt.unloadSession();
    }

    public static String getType() {
        return type;
    }

    public static void setType(String _type) {
        type = type;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String _id) {
        id = id;
    }

}
