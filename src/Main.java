import javafx.application.Application;
import javafx.stage.Stage;
import xml.*;
import cellsociety_team16.*;
public class Main extends Application{
	
	
	public static void main (String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		XMLManager xml = new XMLManager();
		xml.start(primaryStage);
		Simulation simulation = xml.getSimulation();
		GUI gui = new GUI(simulation);
		gui.start(primaryStage);
		
		
	}
}
