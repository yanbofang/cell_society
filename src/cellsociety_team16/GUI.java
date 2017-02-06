package cellsociety_team16;

import java.util.ResourceBundle;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import xml.XMLManager;

/**
 * Loads the UI for Cell Society interface
 * 
 * @author Kris Elbert
 *
 */
// TODO do not extend application if unnecessary
public class GUI {
	// constants
	public static final int SCREENWIDTH = 500;
	public static final int SCREENHEIGHT = 700;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final Paint BACKGROUND = Color.WHITE;
	public static final String XML_GAME_OF_LIFE = "GameofLife";
	public static final String XML_SEGREGATION = "Segregation";
	public static final String XML_SPREADING_FIRE = "SpreadingFire";
	public static final String XML_WATOR_WORLD = "WaTor";

	// scene to report back to Application
	private Scene myScene;
	//

	private int myGridRows, myGridColumns;
	private String mySimType;
	// user input fields
	private Button myPlayButton;
	private Button myPauseButton;
	private Button myStepButton;
	private Button myResetButton;
	private Slider mySpeedSlider;
	private ComboBox<String> chooseSimulation;
	private ObservableList<String> mySimulationTypes = FXCollections.observableArrayList(XML_GAME_OF_LIFE,
			XML_SEGREGATION, XML_SPREADING_FIRE, XML_WATOR_WORLD);
	private int gridSideSize;
	// TODO may make a visualizationWindow class
	private Node myGrid;
	private List<Color> myColors;

	// get strings from resource file
	private ResourceBundle myResources;
	// get data on cell
	// TODO go from Simulation to SimulationModel
	private SimulationModel mySimulationModel;
	private boolean isPaused;
	// default speed value is in the middle
	private double mySpeedMultiplier = .5;
	//TODO may not need depending on how SimulationModel is handling things
	private XMLManager myXMLManager;

	public GUI(SimulationModel simulation, String language) {
		// TODO call based on simulation chosen
		mySimulationModel = simulation;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		Stage newStage = new Stage();
		this.display(newStage);
	}

	/**
	 * Initialize the display and updates
	 */
	public void display(Stage primaryStage) {
		Scene scene = setUp(SCREENWIDTH, SCREENHEIGHT, BACKGROUND);
		primaryStage.setScene(scene);
		primaryStage.setTitle(myResources.getString("WindowTitle"));
		primaryStage.show();
	}

	/**
	 * Sets up and arranges the window Calls once
	 * 
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
		int padding = 20;
		root.setPadding(new Insets(height / padding, width / padding, width / padding, height / padding));

		// set up space for visualization window
		// set grid extents to a of whichever is smaller, width or height
		// .75 is arbitrary value for aesthetic purposes
		gridSideSize = (int) Math.min(height * .75, width * .75);
		// TODO figure out why the two lines below are such a problem
		// root.getCenter().prefHeight(gridSideSize);
		// root.getCenter().prefWidth(gridSideSize);

		// set up space for user input and buttons
		// must do before initiate the grid so chooseSimulation combBox is
		// initiated
		root.setBottom(setUpUserInput(width));
		// resets grid - TODO may be redundant
		myGrid = setUpGrid(gridSideSize);
		root.setCenter(myGrid);

		return new Scene(root, width, height, color);
	}

	/**
	 * Draws and colors and grid of squares
	 * 
	 * @return a new grid object
	 */
	private Node setUpGrid(int gridExtents) {
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		mySimulationModel.setRandomPositions();
		// make sure getting original starting colors
		myColors = mySimulationModel.getColors();

		int index = 0;

		int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * sideSize;
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				Rectangle r = new Rectangle(col_iter * sideSize, rowLoc, sideSize, sideSize);
				r.setFill(myColors.get(index));
				// for testing:
				// r.setFill(Color.rgb(15 * row_iter, 15 * col_iter, 0));
				cells.getChildren().add(r);
				index++;
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
		chooseSimulation = new ComboBox<String>(mySimulationTypes);
		// 4 is an arbitrary value for aesthetic purposes
		chooseSimulation.setVisibleRowCount(4);
		chooseSimulation.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
				// resets the simulation type that will be displayed
				mySimulationModel.getSimulationModel(newValue);
			}
		});
		// default simulation is game of life
		chooseSimulation.setValue(XML_GAME_OF_LIFE);
		myResetButton = makeButton("ResetCommand", event -> resetGrid());
		// creates the play/pause toggle button
		// myPlayButton = makeButton("PlayCommand", event -> play());
		// ToggleGroup playPauseGroup = createToggleButton(myPlayButton,
		// myPauseButton, "PlayCommand", "PauseCommand");

		myStepButton = makeButton("StepCommand", event -> step());
		mySpeedSlider = new Slider();
		// slider is based on percentages, hence the 0 to 10 and default value
		// which then multiplies by duration
		mySpeedSlider.setMin(0);
		mySpeedSlider.setMax(1);
		// Sets default slider location
		mySpeedSlider.setValue(mySpeedMultiplier);
		mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySpeedMultiplier = newValue.doubleValue();
			}

		});
		mySpeedSlider.setBlockIncrement(.1);
		mySpeedSlider.setSnapToTicks(true);

		buttonLine.getChildren().add(chooseSimulation);
		buttonLine.getChildren().add(myResetButton);
		buttonLine.getChildren().add(myPlayButton);
		buttonLine.getChildren().add(myStepButton);
		buttonLine.getChildren().addAll(mySpeedSlider);

		return buttonLine;
	}

	/// **
	// * Creates a ToggleButton that goes between two states
	// * @param stateOn is the first ToggleButton, initially turned on
	// * @param statOff is the second ToggleButton
	// * @param nameOn is the name in the resource file corresponding to stateOn
	// * @param nameOff is the name in the resource file correponding to
	/// stateOff
	// * @return ToggleGroup of these buttons
	// */
	// private ToggleGroup createToggleButton(ToggleButton stateOne,
	/// ToggleButton stateTwo, String nameOne,
	// String nameTwo) {
	// // TODO make ToggleButton command compatible with graphics in the
	// // resources folder, do the same for makeButton
	// // TODO redundant code
	// stateOn = new ToggleButton(myResources.getString(nameOn));
	// stateOff = new ToggleButton(myResources.getString(nameOff));
	// ToggleGroup group = new ToggleGroup();
	// stateOn.setToggleGroup(group);
	// // turn stateOn button on, initially
	// stateOn.setSelected(true);
	// stateOff.setToggleGroup(group);
	// return group;
	// }
	/// **
	// * Sets the state of buttons depending on the state of other buttons
	// */
	// private void setButtonState() {
	// //myResetButton.setDisable(value);
	// myPlayButton.setSelected(true);
	// }

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
		newButton.setText(myResources.getString(name));
		newButton.setOnAction(handler);
		return newButton;
	}

	/**
	 * Reramdonizes the simulation Triggered by a button press
	 */
	private void resetGrid() {
		mySimulationModel.setRandomPositions();
		myGrid = setUpGrid(gridSideSize);
	}

	// /**
	// * Updates the simulation by one advancement Triggered by a button or
	// usual
	// * run calls
	// */
	private void step() {
		// TODO using new colors as determined by simulation backend
		myGrid = setUpGrid(gridSideSize);
	}

	//
	/**
	 * Pauses, plays, or resumes the simulation Triggered by a button
	 */
	private void play() {
		isPaused = !isPaused;
		if (isPaused) {
			myPlayButton.setText(myResources.getString("PlayCommand"));
			// showAndWait();
		} else {
			myPlayButton.setText(myResources.getString("PauseCommand"));

			// for(duration % mySpeed == 0){
			step();
			// }
		}
	}

	private void pause() {
		isPaused = true;
		// myPauseButton.getContentDisplay();

	}

	/**
	 * Inner class deals with clicks based on
	 * http://blogs.kiyut.com/tonny/2013/07/30/javafx-webview-addhyperlinklistener/
	 * and BrowserView by
	 * 
	 * @author Owen Astrachan
	 * @author Marcin Dobosz
	 * @author Yuzhang Han
	 * @author Edwin Ward
	 * @author Robert C. Duvall
	 */
	private class PlayListener implements ChangeListener<State> {
		public static final String EVENT_CLICK = "click";

		@Override
		public void changed(ObservableValue<? extends State> observed, State oldState, State newState) {
			// TODO Auto-generated method stub
			isPaused = !isPaused;
		}
	}

	/**
	 * May or may not use to tell SimulationModel which simulation to run
	 */
	public String setSimulationType() {
		return chooseSimulation.getValue();
	}
}