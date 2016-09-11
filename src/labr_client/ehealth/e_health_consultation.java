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


import be.ehealth.businessconnector.ehbox.v3.session.EhealthBoxServiceV3;
import static be.ehealth.businessconnector.ehbox.v3.session.ServiceFactory.getEhealthBoxServiceV3;
import static be.ehealth.technicalconnector.config.ConfigFactory.getConfigValidator;
import be.ehealth.technicalconnector.generic.session.GenericService;
import static be.ehealth.technicalconnector.generic.session.GenericSessionServiceFactory.getGenericService;
import be.ehealth.technicalconnector.ws.domain.GenericRequest;
import be.ehealth.technicalconnector.ws.domain.GenericResponse;
import static be.ehealth.technicalconnector.ws.domain.TokenType.SAML;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.GetBoxInfoRequest;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.GetBoxInfoResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author De Mey Matthias
 */
public class e_health_consultation {

    private static final String ENDPOINT = getConfigValidator().getProperty("endpoint.ehbox.consultation.v3");

    public static void XMLRequest(String request) throws Exception {
        GenericService service = getGenericService();
        String payload = request;
        GenericRequest fullRequest = new GenericRequest();
        fullRequest.setEndpoint("https://services-acpt.ehealth.fgov.be/ehBoxConsultation/v3");
        fullRequest.setPayload(payload);
        fullRequest.setCredentialFromSession(SAML);
        GenericResponse response = service.send(fullRequest);
        assertNotNull(response);
    }

    public static void getBoxInfo() throws Exception {
        EhealthBoxServiceV3 service = getEhealthBoxServiceV3();
        GetBoxInfoRequest getBoxInfoRequest = new GetBoxInfoRequest();
        GetBoxInfoResponse response = service.getBoxInfo(getBoxInfoRequest);
        assertEquals("100", response.getStatus().getCode());
        assertNotNull(response.getBoxId());
    }


}
