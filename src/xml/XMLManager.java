package xml;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import simulation_models.SimulationModel;
import cellsociety_team16.*;

/**
 * The Manager of the XML Files
 * 
 * 
 * @author Yanbo Fang
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 *
 */
public class XMLManager {
	// kind of data files to look for
	public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final String dir = System.getProperty("user.dir");
	public static final File CONFIGURATION_FILE = new File(dir + "/data/Configuration.xml");
	public static final String MODEL_PACKAGE = "simulation_models.";

	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private File dataFile;

	public void start(Stage primaryStage) throws Exception {
		dataFile = myChooser.showOpenDialog(primaryStage);
		if (dataFile != null) {
			try {
				System.out.println(new XMLParser().getSimulation(dataFile, CONFIGURATION_FILE));
			} catch (XMLException e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(String.format("ERROR reading file %s", dataFile.getPath()));
				a.showAndWait();
			}
		} else {
			// nothing selected, so quit the application
			Platform.exit();
		}
	}

	public XMLSimulation getSimulation() {
		return new XMLParser().getSimulation(dataFile, CONFIGURATION_FILE);
	}

	public SimulationModel getSimulationModel() {
		XMLSimulation xml = this.getSimulation();
		try {
			Class<?> model = Class.forName(MODEL_PACKAGE + xml.getName() + "Model");
			return (SimulationModel) model.getDeclaredConstructor(XMLSimulation.class).newInstance(xml);
		} catch (Exception e) {
			Class<?> model;
			try {
				model = Class.forName(MODEL_PACKAGE + xml.getName().substring(0, xml.getName().length() - 3) + "Model");
			} catch (Exception ee) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Get a new SimulationModel based on the input String String
	 * 
	 * @param newSimulation
	 * @return
	 */
	public SimulationModel getSimulationModel(String newSimulation) {
		dataFile = new File(dir + "/data/" + newSimulation + ".xml");
		return this.getSimulationModel();
	}

	// set some sensible defaults when the FileChooser is created
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("Open Data File");
		// pick a reasonable place to start searching for files
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}
}