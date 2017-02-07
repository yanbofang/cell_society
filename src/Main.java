import javafx.application.Application;
import javafx.stage.Stage;
import xml.*;
import cellsociety_team16.*;
import cellsociety_team16.GUI;

import backend.Simulation;
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
		Integer a=Integer.parseInt("1");
		System.out.println(a);
		XMLManager xml = new XMLManager();
		xml.start(primaryStage);
		
		SimulationModel mySimulation = xml.getSimulationModel();
		mySimulation.setRandomPositions();
		Simulation simTest = new Simulation();
		simTest.setInitialGrid(mySimulation);
		for (int i=0;i<100;i++) {
			simTest.startNewRoundSimulation();
		}
		
		GUI gui = new GUI(mySimulation, "English");

		gui.start(primaryStage);
		
	}
}
