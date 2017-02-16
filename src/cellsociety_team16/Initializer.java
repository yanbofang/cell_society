package cellsociety_team16;

import javafx.stage.Stage;
import simulation_models.SimulationModel;
import simulation_models.SimulationModelFactory;
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
		SimulationModelFactory factory = new SimulationModelFactory();
		SimulationModel mySimulationModel = factory.createSimulationModel(xml.getSimulation());
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
