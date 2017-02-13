package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cellsociety_team16.SimulationModel;

/**
 * Class to write a XML file from the info in the SimulationModel
 * 
 * @author Yanbo Fang
 *
 */
public class XMLWriter {

	public static final String XML_DIRECTORY = System.getProperty("user.dir") + "/data/";
	public static final String AUTHOR = "XingyuChen, Kris Elber, Yanbo Fang";

	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private SimulationModel mySimulationModel;

	public XMLWriter() {
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new XMLException("Configuration Error When Writing XML File");
		}
	}

	/**
	 * Write the info in the SimulationModel to a XML file
	 * @param SimulationModel
	 */
	public void writeToXML(SimulationModel model) {
		mySimulationModel = model;

		Element root = doc.createElement("data");
		Attr attrType = doc.createAttribute("Simulation");
		root.setAttributeNode(attrType);
		doc.appendChild(root);

		this.addName(root);
		this.addAuthor(root);
		this.addRowsAndCols(root);
		this.addPercentages(root);

		this.writeContent();
	}

	private void addName(Element root) {
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(mySimulationModel.getName()));
		root.appendChild(name);
	}

	private void addAuthor(Element root) {
		Element author = doc.createElement("author");
		author.appendChild(doc.createTextNode(AUTHOR));
		root.appendChild(author);
	}

	private void addRowsAndCols(Element root) {
		Element rows = doc.createElement("rows");
		rows.appendChild(doc.createTextNode(Integer.toString(mySimulationModel.getRows())));
		root.appendChild(rows);
		Element cols = doc.createElement("cols");
		cols.appendChild(doc.createTextNode(Integer.toString(mySimulationModel.getCols())));
		root.appendChild(cols);
	}

	private void addPercentages(Element root) {
		Element activePercentage = doc.createElement("activePercentage");
		activePercentage.appendChild(doc.createTextNode(Double.toString(mySimulationModel.getActivePercentage())));
		root.appendChild(activePercentage);
		Element inactivePercentage = doc.createElement("inactivePercentage");
		inactivePercentage.appendChild(doc.createTextNode(Double.toString(mySimulationModel.getInactivePercentage())));
		root.appendChild(inactivePercentage);
		Element emptyPercentage = doc.createElement("emptyPercentage");
		inactivePercentage.appendChild(doc.createTextNode(Double.toString(mySimulationModel.getEmptyPercentage())));
		root.appendChild(emptyPercentage);
	}

	private void writeContent() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(XML_DIRECTORY + mySimulationModel.getName() + "UserCreated.xml"));
			transformer.transform(source, result);

			// Testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			throw new XMLException("Configuration Error When Writing XML File");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			throw new XMLException("Exception Occured During the Transformation Process of Writing XML File");
		}

	}

}
