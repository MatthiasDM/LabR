/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.xml;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author familie
 */
public class XmlParser {

    Document xmlDocument;

    public XmlParser(String xml) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
        xmlDocument = builder.parse(input);

    }

    public List<String> searchDocumentForValues(String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        List<String> results = new ArrayList<String>();        
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            results.add(item.getFirstChild().getNodeValue());        
        }

        return results;
    }

}
