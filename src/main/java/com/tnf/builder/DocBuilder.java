package com.tnf.builder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DocBuilder {
    public static String xmlPath = "tfo-journal-metrics.xml";
    public static String startTag = "publication";
    public static String attribute = "doi";
    public static String attributeName = "name";
    public static String tagName = "custom-property";

    public static NodeList getValues() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(xmlPath));
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName(startTag);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
