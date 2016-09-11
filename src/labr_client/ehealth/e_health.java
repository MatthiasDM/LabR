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



import be.ehealth.business.common.domain.Patient;
import static be.ehealth.business.common.util.EidUtils.readFromEidCard;
import static be.ehealth.technicalconnector.beid.BeIDCardFactory.getBeIDCard;
import be.ehealth.technicalconnector.beid.BeIDInfo;
import static be.ehealth.technicalconnector.beid.BeIDInfo.getInstance;
import be.ehealth.technicalconnector.beid.domain.Identity;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.service.sts.security.Credential;
import be.ehealth.technicalconnector.service.sts.security.SAMLToken;
import be.ehealth.technicalconnector.session.SessionItem;
import static be.ehealth.technicalconnector.utils.SessionUtil.checkAndRetrieveSession;
import java.util.ArrayList;
import java.util.List;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static labr_client.GUI.custom_classes.Dynamic_swing.infoBox;

/**
 *
 * @author familie
 */

public class e_health {

   
    public e_health(){

    }    
    public List<String> return_eID_info(){
		BeIDInfo test;
                
		List<String> info = new ArrayList<>();
		try {         
			
                     Patient p = readFromEidCard();
                        
                        if(getBeIDCard() != null){
                            test = getInstance();
                            Identity id = test.getIdentity();                            
                            info.add(id.getFirstName());  
                            info.add(id.getName());
                            info.add(test.getAddress().getStreetAndNumber());
                            info.add(test.getAddress().getZip());
                            info.add(test.getAddress().getMunicipality());
                            info.add(id.getGender().name());
                            info.add(id.getDateOfBirth().toString());
                            info.add(id.getNationality());
                            info.add(id.getNationalNumber());
                        }
			
		} catch (TechnicalConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}   
 
    
    public void get_ehealth_user_info(){
        try {
            SessionItem item = checkAndRetrieveSession();
            Credential c = item.getHolderOfKeyCredential();           
            SAMLToken t = item.getSAMLToken();            
            infoBox("Issuer: " + t.getIssuer() + "\n" + "Qualifier: " + t.getIssuerQualifier() + "\nProvider: " + t.getProviderName(),"info");
        } catch (TechnicalConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        }
    }
    
    
   
    
}