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
import be.ehealth.businessconnector.ehbox.api.utils.QualityType;
import be.ehealth.businessconnector.ehbox.v3.builders.BuilderFactory;
import be.ehealth.businessconnector.ehbox.v3.builders.SendMessageBuilder;
import be.ehealth.businessconnector.ehbox.v3.session.EhealthBoxServiceV3;
import be.ehealth.businessconnector.ehbox.v3.session.ServiceFactory;
import be.ehealth.technicalconnector.exception.ConnectorException;
import be.ehealth.technicalconnector.exception.TechnicalConnectorException;
import be.ehealth.technicalconnector.utils.ConnectorXmlUtils;
import be.ehealth.technicalconnector.utils.IdentifierType;
import be.fgov.ehealth.ehbox.consultation.protocol.v3.Message;
import be.fgov.ehealth.ehbox.core.v3.CustomMetaType;
import be.fgov.ehealth.ehbox.publication.protocol.v3.SendMessageRequest;
import be.fgov.ehealth.ehbox.publication.protocol.v3.SendMessageResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import labr_client.Public.PublicVars;
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
            SendMessageBuilder builder = be.ehealth.businessconnector.ehbox.v3.builders.BuilderFactory.getSendMessageBuilder();
            NewsMessage<Message> news = new NewsMessage<Message>();
            news.getDocument().setTitle("Encrypted news " + new Date());
            news.getDocument().setContent(message.getBytes());
            news.getDocument().setFilename("LABR-123456789-123.txt");
            news.getDocument().setMimeType("text/xml");
            news.setDestinations(PublicVars.getReceiver());
            news.setImportant(true);

            news.setSender(PublicVars.getSender());
            SendMessageRequest request = builder.buildMessage(news);

            EhealthBoxServiceV3 service = ServiceFactory.getEhealthBoxServiceV3();
            ConnectorXmlUtils.logXmlObject(request);
            SendMessageResponse response = service.sendMessage(request);
            Assert.assertEquals("100", response.getStatus().getCode());
            Assert.assertNotNull(response.getId());
        } catch (IOException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EhboxBusinessConnectorException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TechnicalConnectorException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectorException ex) {
            Logger.getLogger(e_health.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendDocumentMessage(String message, List<Addressee> Destinations) throws TechnicalConnectorException, EhboxBusinessConnectorException, IOException, CMSException, ConnectorException {
        SendMessageBuilder builder = BuilderFactory.getSendMessageBuilder();         

        
        
        DocumentMessage<Message> documentMsg = buildEhealthMessage(message,Destinations);        
        SendMessageRequest request = builder.buildMessage(documentMsg);
        EhealthBoxServiceV3 service = ServiceFactory.getEhealthBoxServiceV3();
        ConnectorXmlUtils.logXmlObject(request);
        SendMessageResponse response = service.sendMessage(request);
        Assert.assertEquals("100", response.getStatus().getCode());
        Assert.assertNotNull(response.getId());
    }

    public static DocumentMessage<Message> buildEhealthMessage(String message,List<Addressee> Destinations) throws TechnicalConnectorException{
        DocumentMessage<Message> documentMsg = new DocumentMessage<Message>();
        documentMsg.setDestinations(Destinations);
        documentMsg.setSender(PublicVars.getSender());
        documentMsg.setImportant(false);        
        documentMsg.generatePublicationId();
        //documentMsg.setUsePublicationReceipt(true);
        //documentMsg.setUseReadReceipt(true);
        //documentMsg.setUseReceivedReceipt(true);
        documentMsg.setEncrypted(false);
        
        Document doc = new Document();
        doc.setTitle(PublicVars.getTitel());
        InputStream stream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        doc.setContent(stream);
        doc.setMimeType(PublicVars.getMimeType());
        doc.setFilename(PublicVars.getTitel());
        documentMsg.setDocument(doc);
        return documentMsg;
    }

    

    

    
    

}
