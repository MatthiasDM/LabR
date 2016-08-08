/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.ehealth;


import be.ehealth.businessconnector.ehbox.v3.session.EhealthBoxServiceV3;
import be.ehealth.businessconnector.ehbox.v3.session.ServiceFactory;
import be.ehealth.technicalconnector.config.ConfigFactory;

import be.ehealth.technicalconnector.generic.session.GenericService;
import be.ehealth.technicalconnector.generic.session.GenericSessionServiceFactory;

import be.ehealth.technicalconnector.ws.domain.GenericRequest;
import be.ehealth.technicalconnector.ws.domain.GenericResponse;
import be.ehealth.technicalconnector.ws.domain.TokenType;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.GetBoxInfoRequest;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.GetBoxInfoResponse;



import org.junit.Assert;

/**
 *
 * @author labbl
 */
public class e_health_consultation {

    private static final String ENDPOINT = ConfigFactory.getConfigValidator().getProperty("endpoint.ehbox.consultation.v3");

    public static void XMLRequest(String request) throws Exception {
        GenericService service = GenericSessionServiceFactory.getGenericService();
        String payload = request;
        GenericRequest fullRequest = new GenericRequest();
        fullRequest.setEndpoint("https://services-acpt.ehealth.fgov.be/ehBoxConsultation/v3");
        fullRequest.setPayload(payload);
        fullRequest.setCredentialFromSession(TokenType.SAML);
        GenericResponse response = service.send(fullRequest);
        Assert.assertNotNull(response);
    }

    public static void getBoxInfo() throws Exception {
        EhealthBoxServiceV3 service = ServiceFactory.getEhealthBoxServiceV3();
        GetBoxInfoRequest getBoxInfoRequest = new GetBoxInfoRequest();
        GetBoxInfoResponse response = service.getBoxInfo(getBoxInfoRequest);
        Assert.assertEquals("100", response.getStatus().getCode());
        Assert.assertNotNull(response.getBoxId());
    }


}
