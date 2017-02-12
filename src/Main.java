import javafx.application.Application;
import javafx.stage.Stage;
import cellsociety_team16.*;

/**
 * Beginning of the program
 * 
 * @author Yanbo Fang
 *
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Initializer init = new Initializer();
		init.initSimulation(primaryStage);
	}
}