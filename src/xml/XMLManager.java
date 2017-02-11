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

import cellsociety_team16.*;

public class XMLManager extends Application {
	// kind of data files to look for
	public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final String dir = System.getProperty("user.dir");
	public static final File CONFIGURATION_FILE = new File(dir + "/data/Configuration.xml");



	// it is generally accepted behavior that the chooser remembers where user
	// left it last
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private File dataFile;
	

	@Override
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
		try{
			Class<?> model = Class.forName("cellsociety_team16." + xml.getName() + "Model");
			return (SimulationModel) model.getDeclaredConstructor(XMLSimulation.class).newInstance(xml);

		}catch(Exception e){
			return null;
		}
	}

	/**
	 * Get a new SimulationModel based on the input String
	 * String
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