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
import be.ehealth.businessconnector.ehbox.api.domain.Document;
import be.ehealth.businessconnector.ehbox.api.domain.DocumentMessage;
import be.ehealth.businessconnector.ehbox.api.domain.NewsMessage;
import be.ehealth.businessconnector.ehbox.api.domain.exception.EhboxBusinessConnectorException;
import static be.ehealth.businessconnector.ehbox.v3.builders.BuilderFactory.getSendMessageBuilder;
import be.ehealth.businessconnector.ehbox.v3.builders.SendMessageBuilder;
import be.ehealth.businessconnector.ehbox.v3.session.EhealthBoxServiceV3;
import static be.ehealth.businessconnector.ehbox.v3.session.ServiceFactory.getEhealthBoxServiceV3;
import be.ehealth.technicalconnector.exception.ConnectorException;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import static be.ehealth.technicalconnector.utils.ConnectorXmlUtils.logXmlObject;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.Message;
import be.fgov.ehealth.ehbox.publication.protocol.v3.SendMessageRequest;
import be.fgov.ehealth.ehbox.publication.protocol.v3.SendMessageResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Date;
import java.util.List;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static labr_client.Public.PublicVars.getMimeType;
import static labr_client.Public.PublicVars.getReceiver;
import static labr_client.Public.PublicVars.getSender;
import static labr_client.Public.PublicVars.getTitel;
import org.bouncycastle.cms.CMSException;

/**
 *
 * @author De Mey Matthias
 */
public class e_health_publication {

    public static void send_lab_request(String message) {
        send_message(message);
    }

    public static void send_message(String message) {

        try {
            SendMessageBuilder builder = getSendMessageBuilder();
            NewsMessage<Message> news = new NewsMessage<>();
            news.getDocument().setTitle("Encrypted news " + new Date());
            news.getDocument().setContent(message.getBytes());
            news.getDocument().setFilename("LABR-123456789-123.txt");
            news.getDocument().setMimeType("text/xml");
            news.setDestinations(getReceiver());
            news.setImportant(true);

            news.setSender(getSender());
            SendMessageRequest request = builder.buildMessage(news);

            EhealthBoxServiceV3 service = getEhealthBoxServiceV3();
            logXmlObject(request);
            SendMessageResponse response = service.sendMessage(request);
            assertEquals("100", response.getStatus().getCode());
            assertNotNull(response.getId());
        } catch (IOException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        } catch (EhboxBusinessConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        } catch (TechnicalConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        } catch (ConnectorException ex) {
            getLogger(e_health.class.getName()).log(SEVERE, null, ex);
        }
    }

    public static void sendDocumentMessage(String message, List<Addressee> Destinations) throws TechnicalConnectorException, EhboxBusinessConnectorException, IOException, CMSException, ConnectorException {
        SendMessageBuilder builder = getSendMessageBuilder();         

        
        
        DocumentMessage<Message> documentMsg = buildEhealthMessage(message,Destinations);        
        SendMessageRequest request = builder.buildMessage(documentMsg);
        EhealthBoxServiceV3 service = getEhealthBoxServiceV3();
        logXmlObject(request);
        SendMessageResponse response = service.sendMessage(request);
        assertEquals("100", response.getStatus().getCode());
        assertNotNull(response.getId());
    }

    public static DocumentMessage<Message> buildEhealthMessage(String message,List<Addressee> Destinations) throws TechnicalConnectorException{
        DocumentMessage<Message> documentMsg = new DocumentMessage<>();
        documentMsg.setDestinations(Destinations);
        documentMsg.setSender(getSender());
        documentMsg.setImportant(false);        
        documentMsg.generatePublicationId();
        //documentMsg.setUsePublicationReceipt(true);
        //documentMsg.setUseReadReceipt(true);
        //documentMsg.setUseReceivedReceipt(true);
        documentMsg.setEncrypted(false);
        
        Document doc = new Document();
        doc.setTitle(getTitel());
        InputStream stream = new ByteArrayInputStream(message.getBytes(UTF_8));
        doc.setContent(stream);
        doc.setMimeType(getMimeType());
        doc.setFilename(getTitel());
        documentMsg.setDocument(doc);
        return documentMsg;
    }

    

    

    
    

}
