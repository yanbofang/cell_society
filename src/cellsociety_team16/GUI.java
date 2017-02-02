package cellsociety_team16;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
/**
 * Loads the UI for Cell Society interface
 * @author Elbert
 *
 */
public class GUI extends Application{
	// The size of the visualization window will not change
	public static final int SCREENWIDTH = 500;
	public static final int SCREENHEIGHT = 700;
	public static final String TITLE = "Cellular Simulation";
	public static final Paint BACKGROUND = Color.WHITE;

	private Scene myScene;
	// Sets the padding for the grid
	private int padding = 5;
	private int myGridRows, myGridColumns;
	private String mySimType;
	private Simulation mySimulation;
	/**
	 * Initialize the display and updates
	 */
	public void start(Stage primaryStage){
		Scene scene = setUp(SCREENWIDTH, SCREENHEIGHT, BACKGROUND);
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
	}
	/**
	 * 
	 * @param width sets window width
	 * @param height sets window height
	 * @param color set background color
	 * @return new scene with all of the parts loaded, awaiting input
	 */
	public Scene setUp(int width, int height, Paint color){
		//Select a file 
		//will automatically parse
		Group root = new Group();
		
		myScene = new Scene(root, width, height, color);
		
		Group buttons = new Group();
		//TODO do not hardcode these names, likely don't even hard code the cell colors
		Button play = new Button("Play");
		//TODO toggle to pause
		Button step = new Button("Step Forward");
		Button loadFile = new Button("Load a File");
		Slider speedSlider = new Slider();
		speedSlider.setMin(0);
		speedSlider.setMax(100);
		// Sets default slider location
		speedSlider.setValue(50);
		speedSlider.setBlockIncrement(10);
		Grid myGrid = setUpGrid();
		return myScene;
	}
	/**
	 * Draws and colors and grid of squares
	 * @param rows is the number of rows in the grid
	 * @param columns is the number of columns
	 * @return a new grid object
	 */
	private GridPane setUpGrid(){
		mySimulation = new Simulation();
		GridPane grid = new GridPane();
		myGridRows = mySimulation.rows;
		myGridColumns = mySimulation.columns;
		grid.setPadding(new Insets(padding));
		int sideSize = SCREENWIDTH/myGridRows - padding;
		
		return grid;
	}
	/**
	 * Figures out starting positions
	 */
	private int[] initLocs(){
		
	}
	
	/**
	 * Runs the program
	 */
	public static void main(String[] args){
		launch(args);
	}
}
