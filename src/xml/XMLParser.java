package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * This class handles parsing XML files and returning a completed object.
 * @author Yanbo Fang
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLParser {
    // name of root attribute that notes the type of file expecting to parse
    public static final String TYPE_ATTRIBUTE = "type";

    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();


    /**
     * Get the data contained in this XML file as an object
     */
    public XMLSimulation getSimulation (File dataFile, File configurationFile) {
        Element root = getRootElement(dataFile);
        Element configurationRoot = getRootElement(configurationFile);
        if (! isValidFile(root, XMLSimulation.DATA_TYPE)) {
            throw new XMLException("XML file does not represent %s", XMLSimulation.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        Map<String, String> results = new HashMap<>();
        for (String field : XMLSimulation.DATA_FIELDS) {
            results.put(field, getTextValue(root, field));
        }
        for(String field : XMLSimulation.CONFIGURATION_FIELDS){
        	results.put(field, getTextValue(configurationRoot, field));
        }
        return new XMLSimulation(results);
    }


    // Get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // Returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // Get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // Get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    // Helper method to do the boilerplate code needed to make a documentBuilder.
    private static DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}