package cellsociety_team16;

import javafx.stage.Stage;
import xml.XMLManager;
import xml.XMLWriter;

/**
 * Initialize a simulation
 * 
 * @author Yanbo Fang
 *
 */
public class Initializer {

	public void initSimulation(Stage stage) throws Exception {
		XMLManager xml = new XMLManager();
		xml.start(stage);

		SimulationModel mySimulationModel = xml.getSimulationModel();
		XMLWriter writer = new XMLWriter();
		writer.writeToXML(mySimulationModel);

		GUI gui = new GUI(mySimulationModel, "English");

		gui.init(stage);
	}

	/**
	 * Create another simulation
	 * 
	 * @throws Exception
	 */
	public void newSimulation() throws Exception {
		Stage newStage = new Stage();
		this.initSimulation(newStage);
	}

}
