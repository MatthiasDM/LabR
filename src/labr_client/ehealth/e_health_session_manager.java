/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.ehealth;

import be.ehealth.businessconnector.ehbox.api.domain.Addressee;
import be.ehealth.technicalconnector.config.ConfigFactory;
import be.ehealth.technicalconnector.config.ConfigValidator;
import be.ehealth.technicalconnector.exception.SessionManagementException;
import be.ehealth.technicalconnector.service.sts.security.SAMLToken;
import be.ehealth.technicalconnector.service.sts.utils.SAMLConverter;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.service.sts.SAMLTokenFactory;
import be.ehealth.technicalconnector.service.sts.security.impl.KeyStoreCredential;
import be.ehealth.technicalconnector.session.Session;
import be.ehealth.technicalconnector.session.SessionItem;
import java.io.FileOutputStream;
import java.io.FileReader;
import be.ehealth.technicalconnector.session.SessionManager;
import be.ehealth.technicalconnector.utils.CertificateParser;
import org.w3c.dom.Element;
import be.ehealth.technicalconnector.utils.IdentifierType;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import labr_client.GUI.custom_classes.Dynamic_swing;
import labr_client.Public.PublicVars;
import labr_client.Public.encryption;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author labbl
 */
public class e_health_session_manager {



    private static String type, id, app;

    public e_health_session_manager() {
        File config_file = new File("data\\" + PublicVars.geteHealthPropertiesLocation());      
        try {
            ConfigFactory.setConfigLocation(config_file.getAbsoluteFile().getAbsolutePath());
            ConfigFactory.setConfigLocation("data/tmp/eHealthProps.properties");
         
            //ConfigFactory.getConfigValidator().setProperty("endpoint.sts", "https://services-int.ehealth.fgov.be/IAM/Saml11TokenService/Legacy/v1"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.ehbox.consultation.v3", "https://services-int.ehealth.fgov.be/ehBoxConsultation/v3"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.ehbox.publication.v3", "https://services-int.ehealth.fgov.be/ehBoxPublication/v3"); 
            //ConfigFactory.getConfigValidator().setProperty("endpoint.etk", "https://services-int.ehealth.fgov.be/EtkDepot/v1"); 

        } catch (TechnicalConnectorException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public Addressee getSender() throws TechnicalConnectorException {
        CertificateParser certParser = new CertificateParser(Session.getInstance().getSession().getSAMLToken().getCertificate());
        Addressee addressee = new Addressee(IdentifierType.NIHII_LABO);
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
                PublicVars.setSender(getSender());
            } catch (TechnicalConnectorException ex) {
                Logger.getLogger(e_health_key_depot.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return successfull;
    }

    public boolean init_session() {
        String hokPassword = null;
        try {
            hokPassword = encryption.decodeBase64Aes(PublicVars.getUserData()[7]);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(e_health_session_manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] userData = PublicVars.getUserData();
        ConfigFactory.getConfigValidator().setProperty("user.firstname", userData[2]); 
        ConfigFactory.getConfigValidator().setProperty("user.lastname", userData[3]); 
        ConfigFactory.getConfigValidator().setProperty("user.inss", userData[4]); 
        ConfigFactory.getConfigValidator().setProperty("user.nihii", userData[5]); 
        ConfigFactory.getConfigValidator().setProperty("sessionmanager.holderofkey.keystore", userData[6]);
        ConfigFactory.getConfigValidator().setProperty("sessionmanager.identification.keystore", userData[6]);
        ConfigFactory.getConfigValidator().setProperty("sessionmanager.encryption.keystore", userData[6]);
        SessionManager sessionmgmt = Session.getInstance();
        Map<String, String> keystores = new HashMap<String, String>(3);
        boolean valid_session = false;
        try {
            valid_session = sessionmgmt.hasValidSession();
            if (!valid_session) {
                sessionmgmt.createFallbackSession(hokPassword);
                //sessionmgmt.createSession(hokPassword, hokPassword);
            } else {
                if (Dynamic_swing.infoBox("A Session already exists, do you want to close the old session and create a new one?", "Session already exists") == 0) {
                    sessionmgmt.unloadSession();
                    //sessionmgmt.createSession(hokPassword, persPassword);
                    sessionmgmt.createFallbackSession(hokPassword, hokPassword, hokPassword);
                }
            }
            valid_session = sessionmgmt.hasValidSession();
        } catch (SessionManagementException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TechnicalConnectorException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valid_session;
    }

    private static void storeAndReload(String indentPwd, String hokPwd, String encPwd, SessionManager sessionmgmt) throws IOException, TechnicalConnectorException {
        SessionItem item = sessionmgmt.getSession();

        Element originalAssertion = item.getSAMLToken().getAssertion();
        String serializedToken = SAMLConverter.toXMLString(originalAssertion);

        File temp = new File("tempToken");
        temp.deleteOnExit();

        IOUtils.write(serializedToken.getBytes(), new FileOutputStream(temp));

        sessionmgmt.unloadSession();

        Element savedAssertion = SAMLConverter.toElement(IOUtils.toString(new FileReader(temp)));

        ConfigValidator config = ConfigFactory.getConfigValidator();

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
