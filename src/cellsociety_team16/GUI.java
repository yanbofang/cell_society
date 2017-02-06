package cellsociety_team16;

import java.util.ResourceBundle;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import xml.XMLSimulation;

/**
 * Loads the UI for Cell Society interface
 * 
 * @author Elbert
 *
 */
// TODO do not extend application if unnecessary
public class GUI extends Application {
	// constants
	public static final int SCREENWIDTH = 500;
	public static final int SCREENHEIGHT = 700;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final Paint BACKGROUND = Color.WHITE;

	// scene to report back to Application
	private Scene myScene;
	//

	private int myGridRows, myGridColumns;
	private String mySimType;
	// user input fields
	private ToggleButton myPlayButton;
	private ToggleButton myPauseButton;
	private Button myStepButton;
	private Button myResetButton;
	private Slider mySpeedSlider;
	private ComboBox<String> mySimulationTypes;
	private int gridSideSize;
	//TODO may make a visualizationWindow class
	private Node myGrid;
	private List<Color> myColors;

	// get strings from resource file
	private ResourceBundle myResources;
	// get data on cell
	// TODO go from Simulation to SimulationModel
	private SimulationModel mySimulationModel;

	public GUI(SimulationModel simulation, String language) {
		mySimulationModel = simulation;
		//TODO figure out why it is not seeing resources as accessible
		//for testing:
		//String dislanguage = "English";
		//myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + dislanguage);
		// TODO can move all Stage setting into Main and keep all scene setting
		// here
		Stage newStage = new Stage();
		this.start(newStage);
	}

	/**
	 * Initialize the display and updates
	 */
	// TODO may change to display() to be more descriptive
	public void start(Stage primaryStage) {
		Scene scene = setUp(SCREENWIDTH, SCREENHEIGHT, BACKGROUND);
		primaryStage.setScene(scene);
		//primaryStage.setTitle(myResources.getString("WindowTitle"));
		primaryStage.show();
	}

	/**
	 * Sets up and arranges the window
	 * Calls once
	 * @param width
	 *            sets window width
	 * @param height
	 *            sets window height
	 * @param color
	 *            set background color
	 * @return new scene with all of the parts loaded, awaiting input
	 */
	public Scene setUp(int width, int height, Paint color) {
		// The size of the visualization window will not change
		// Select a file
		// will automatically parse
		BorderPane root = new BorderPane();
		// sets padding in order of top, right, bottom, and left
		// 20 is based on whatever looked nice, arbitrary
		root.setPadding(new Insets(height / 20, width / 20, width / 20, height / 20));

		// set up space for visualization window
		// set grid extents to a of whichever is smaller, width or height
		gridSideSize = (int) Math.min(height * .75, width * .75);
		//TODO figure out why the two lines below are such a problem
		//root.getCenter().prefHeight(gridSideSize);
		//root.getCenter().prefWidth(gridSideSize);
		mySimulationModel.setRandomPositions();
		myGrid = setUpGrid(gridSideSize);
		root.setCenter(myGrid);

		// set up space for user input and buttons
		root.setBottom(setUpUserInput(width));
		// TODO do not hardcode these names, likely don't even hard code the
		// cell colors

		return new Scene(root, width, height, color);
	}

	/**
	 * Draws and colors and grid of squares
	 * 
	 * @return a new grid object
	 */
	private Node setUpGrid(int gridExtents) {
		// mySimulation = new getSimulation(dataFile);
		// GridPane grid = new GridPane();
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();
		myColors = mySimulationModel.getColors();
		//for testing:
		//myGridRows = 5;
		//myGridColumns = 5;
		
		int index = 0;
		
		int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * sideSize;
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				// if not empty
				Rectangle r = new Rectangle(col_iter * sideSize, rowLoc, sideSize, sideSize);
				 r.setFill(myColors.get(index));
				// for testing:
				//r.setFill(Color.rgb(15 * row_iter, 15 * col_iter, 0));
				cells.getChildren().add(r);
				index ++;
			}
		}
		return cells;
	}

	/**
	 * Creates display that the user can interact with
	 */
	private Node setUpUserInput(int width) {
		HBox buttonLine = new HBox();
		buttonLine.setAlignment(Pos.CENTER);
		// mySimulationTypes;
		myResetButton = makeButton("ResetCommand", event -> resetGrid());
		// creates the play/pause toggle button

	//	ToggleGroup playPauseGroup = createToggleButton(myPlayButton, myPauseButton, "PlayCommand", "PauseCommand");

	//	myStepButton = makeButton("StepCommand", event -> step());
		mySpeedSlider = new Slider();
		// slider is based on percentages, hence the 0 to 100 and default value
		// of 50%
		mySpeedSlider.setMin(0);
		mySpeedSlider.setMax(100);
		// Sets default slider location
		mySpeedSlider.setValue(50);
		mySpeedSlider.setBlockIncrement(10);
		buttonLine.getChildren().add(myResetButton);
		buttonLine.getChildren().addAll(mySpeedSlider);

		return buttonLine;
	}
///**
// * Creates a ToggleButton that goes between two states
// * @param stateOn is the first ToggleButton, initially turned on
// * @param statOff is the second ToggleButton
// * @param nameOn is the name in the resource file corresponding to stateOn
// * @param nameOff is the name in the resource file correponding to stateOff
// * @return ToggleGroup of these buttons
// */
//	private ToggleGroup createToggleButton(ToggleButton stateOne, ToggleButton stateTwo, String nameOne,
//			String nameTwo) {
//		// TODO make ToggleButton command compatible with graphics in the
//		// resources folder, do the same for makeButton
//		// TODO redundant code
//		stateOn = new ToggleButton(myResources.getString(nameOn));
//		stateOff = new ToggleButton(myResources.getString(nameOff));
//		ToggleGroup group = new ToggleGroup();
//		stateOn.setToggleGroup(group);
//		// turn stateOn button on, initially
//		stateOn.setSelected(true);
//		stateOff.setToggleGroup(group);
//		return group;
//	}
///**
// * Sets the state of buttons depending on the state of other buttons
// */
//	private void setButtonState() {
//		//myResetButton.setDisable(value);
//		myPlayButton.setSelected(true);
//	}
//
	/**
	 * Creates a button
	 * 
	 * @param name
	 *            corresponds to resource file
	 * @param handler
	 *            corresponds to the action that the button calls
	 * @return a new button
	 */
	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button newButton = new Button();
		//newButton.setText(myResources.getString(name));
		// for testing:
		newButton.setText(name);
		newButton.setOnAction(handler);
		return newButton;
	}

	/**
	 * Resets the simulation Triggered by a button press
	 */
	private void resetGrid() {
		myGrid = setUpGrid(gridSideSize);
	}

//	/**
//	 * Updates the simulation by one advancement Triggered by a button or usual
//	 * run calls
//	 */
//	private void step() {
//
//	}
//
//	/**
//	 * Pauses, plays, or resumes the simulation Triggered by a button
//	 */
//	private void togglePlay() {
//
//	}

	/**
	 * Runs the program, for testing this individual segment
	 */
	// for testing
	public static void main(String[] args) {
		launch(args);
	}
}