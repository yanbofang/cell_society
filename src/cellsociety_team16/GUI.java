package cellsociety_team16;

import java.util.ResourceBundle;

import backend.Simulation;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.util.Duration;
import xml.XMLManager;
import xml.XMLSimulation;

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
	public static final Paint BACKGROUND = Color.ALICEBLUE;
	public static final String XML_GAME_OF_LIFE = "GameOfLife";
	public static final String XML_SEGREGATION = "Segregation";
	public static final String XML_SPREADING_FIRE = "SpreadingFire";
	public static final String XML_WATOR_WORLD = "WaTor";
	public static final double MAX_SPEED = 1000; // in ms
	// scene to report back to Application
	private Scene myScene;
	//

	private int myGridRows, myGridColumns;
	private String mySimulationType;
	// user input fields
	private Button myPlayButton;
	private Button myStepButton;
	private Button myResetButton;
	private Slider mySpeedSlider;
	private ComboBox<String> mySimulationChooser;
	private ObservableList<String> mySimulationTypes = FXCollections.observableArrayList(XML_GAME_OF_LIFE,
			XML_SEGREGATION, XML_SPREADING_FIRE, XML_WATOR_WORLD);
	private int gridSideSize;
	// TODO may make a visualizationWindow class
	private Node myGrid;
	private List<Color> myColors;
	private Simulation mySimulation;

	// get strings from resource file
	private ResourceBundle myResources;
	// get data on cell
	// TODO go from Simulation to SimulationModel
	private SimulationModel mySimulationModel;
	private boolean isPaused;
	// default speed value is in the middle
	private double mySpeedMultiplier = .5;
	// TODO may not need depending on how SimulationModel is handling things
	private XMLManager myXMLManager;
	private Timeline timer;
	private BorderPane myRoot;

	public GUI(SimulationModel simulation, String language) {
		mySimulationModel = simulation;
		mySimulation = new Simulation();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		Stage newStage = new Stage();
		this.init(newStage);
		timer = new Timeline();
		myXMLManager = new XMLManager();
	}

	/**
	 * Initialize the display and updates
	 */
	public void init(Stage primaryStage) {
		Scene scene = setUp(SCREENWIDTH, SCREENHEIGHT, BACKGROUND);
		primaryStage.setScene(scene);
		mySimulation.setInitialGrid(mySimulationModel);
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
		myRoot = new BorderPane();
		// sets padding in order of top, right, bottom, and left
		// 20 is based on whatever looked nice, arbitrary
		int padding = 10;
		myRoot.setPadding(new Insets(height / padding, width / padding, width / padding, height / padding));

		// set up space for visualization window
		// set grid extents to a of whichever is smaller, width or height
		// .75 is arbitrary value for aesthetic purposes
		gridSideSize = (int) Math.min(height * .75, width * .75);
		// TODO replace
		mySimulationModel.setRandomPositions();
		myGrid = setUpGrid(gridSideSize);
		myRoot.setCenter(myGrid);

		// set up space for user input and buttons
		// must do before initiate the grid so mySimulationChooser combBox is
		// initiated
		myRoot.setBottom(setUpUserInput(width));
		// resets grid - TODO may be redundant
		myGrid = setUpGrid(gridSideSize);
		myRoot.setCenter(myGrid);

		return new Scene(myRoot, width, height, color);
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
		mySimulationChooser = new ComboBox<String>(mySimulationTypes);
		// 4 is an arbitrary value for aesthetic purposes
		mySimulationChooser.setVisibleRowCount(4);
		// sets to display which simulation the user originally chose
		mySimulationChooser.setValue(mySimulationType);
		mySimulationChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
				// resets the simulation type that will be displayed
				// TODO see if can take a string or sml file
				mySimulationModel = myXMLManager.getSimulationModel(newValue);
				mySimulationModel.setRandomPositions();
				mySimulation.setInitialGrid(mySimulationModel);
				//System.out.println(mySimulationModel.getName());
				resetGrid();
				play();
			}
		});

		myResetButton = makeButton("ResetCommand", event -> resetGrid());
		// creates the play/pause toggle button
		myPlayButton = makeButton("PlayCommand", event -> play());
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
				timer.setRate(mySpeedMultiplier);
			}
		});
		mySpeedSlider.setBlockIncrement(.1);
		//mySpeedSlider.setSnapToTicks(true);

		buttonLine.getChildren().add(mySimulationChooser);
		buttonLine.getChildren().add(myResetButton);
		buttonLine.getChildren().add(myPlayButton);
		buttonLine.getChildren().add(myStepButton);
		buttonLine.getChildren().addAll(mySpeedSlider);

		return buttonLine;
	}

	/**
	 * Creates a button
	 * 
	 * @param name
	 *            corresponds to resource file
	 * @param handler
	 *            corresponds to the action that the button calls
	 * @return a new button
	 * 
	 *         Based on makeButton in BrowserView by
	 * @author Owen Astrachan
	 * @author Marcin Dobosz
	 * @author Yuzhang Han
	 * @author Edwin Ward
	 * @author Robert C. Duvall
	 */
	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		// final String IMAGEFILE_SUFFIXES =
		// String.format(".*\\.(%s)", String.join("|",
		// ImageIO.getReaderFileSuffixes()));
		Button newButton = new Button();
		String label = myResources.getString(name);
		// if(label.matches(IMAGEFILE_SUFFIXES))

		newButton.setWrapText(true);
		newButton.setText(myResources.getString(name));

		newButton.setShape(new Circle(SCREENWIDTH / 10));
		newButton.setPrefWidth(SCREENWIDTH / 5);
		newButton.setPrefHeight(SCREENWIDTH / 10);

		newButton.setOnAction(handler);
		return newButton;
	}

	/**
	 * Sets the speed the animation runs at
	 */
	// private void resetSpeed(Slider slider){
	// final Timeline timer = new Timeline();
	// timer.setCycleCount(Timeline.INDEFINITE);
	//
	// slider.valueChangingProperty().addListener(new ChangeListener<Double>(){
	// @Override
	// public void changed(ObservableValue<? extends double> observed, Double
	// oldValue, Double newValue){
	// resetDuration(timer, newValue);
	// }
	// });
	// }
	/**
	 * Resets duration of keyframes
	 */

	// private void resetDuration(Timeline timer, double interval){
	// KeyFrame keyframe = new Keyframe(
	// Duration.seconds(MAX_SPEED * interval);
	// );
	// }
	/**
	 * Reramdonizes the simulation Triggered by a button press
	 */
	private void resetGrid() {
		mySimulationModel.setRandomPositions();
		//mySimulationModel.setPositions(mySimulation.startNewRoundSimulation());
		myGrid = setUpGrid(gridSideSize);
		myRoot.setCenter(myGrid);
	}

	/**
	 * Updates the simulation by one advancement Triggered by a button or usual
	 * run calls
	 */
	private void step() {
		// TODO using new colors as determined by simulation backend
		mySimulationModel.setPositions(mySimulation.startNewRoundSimulation());
		myGrid = setUpGrid(gridSideSize);
		myRoot.setCenter(myGrid);
	}

	/**
	 * Pauses, plays, or resumes the simulation Triggered by a button
	 */
	private void play() {
		isPaused = !isPaused;

		if (isPaused) {
			myPlayButton.setText(myResources.getString("PlayCommand"));
			timer.pause();
			System.out.println(isPaused);
		} else {
			myPlayButton.setText(myResources.getString("PauseCommand"));
			KeyFrame frame = new KeyFrame(Duration.millis(MAX_SPEED * mySpeedMultiplier), e -> step());
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.getKeyFrames().add(frame);
			timer.play();
		}
	}

	/**
	 * May or may not use to tell SimulationModel which simulation to run
	 */
	public String setSimulationType() {
		return mySimulationChooser.getValue();
	}
}