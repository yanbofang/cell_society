import javafx.application.Application;
import javafx.stage.Stage;
import xml.*;
import cellsociety_team16.*;
import cellsociety_team16.GUI;
/**
 * Run a simulation here
 * @author Yanbo Fang
 *
 */
public class Main extends Application{
	
	public static void main (String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		XMLManager xml = new XMLManager();
		xml.start(primaryStage);
<<<<<<< HEAD
		SimulationModel mySimulation = new SimulationModel(xml.getSimulation());
=======
		SimulationModel mySimulation = xml.getSimulationModel();
>>>>>>> a6c6d784bafac3efcb6f7bd2022b44889817a5b2
		GUI gui = new GUI(mySimulation, "English");
		gui.start(primaryStage);
	}
}
