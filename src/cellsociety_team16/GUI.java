package cellsociety_team16;

import backend.Simulation;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.util.Duration;
import xml.XMLManager;

/**
 * Loads the GUI for Cell Society interface
 * 
 * @author Kris Elbert
 *
 */
public class GUI {
	// constants
	public static final int SCREENWIDTH = 500;
	public static final int SCREENHEIGHT = 700;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final Paint BACKGROUND = Color.ALICEBLUE;
	public static final double MAX_SPEED = 1000; // in ms

	// files the user can load
	public static final String XML_GAME_OF_LIFE = "GameOfLife";
	public static final String XML_SEGREGATION = "Segregation";
	public static final String XML_SPREADING_FIRE = "SpreadingFire";
	public static final String XML_WATOR_WORLD = "WaTor";

	// list of all of the xml files, user could potentially load new ones
	private ObservableList<String> mySimulationTypes = FXCollections.observableArrayList(XML_GAME_OF_LIFE,
			XML_SEGREGATION, XML_SPREADING_FIRE, XML_WATOR_WORLD);
	// current simulation type
	private String mySimulationType;
	// used for initializing and updating grid
	private int gridSideSize;
	private Node myGrid;
	// get information on cell
	private Simulation mySimulation;
	private XMLManager myXMLManager;
	private SimulationModel mySimulationModel;
	private int myGridRows, myGridColumns;
	private List<Color> myColors;

	// user input fields
	private Button myPlayButton;
	private Button myStepButton;
	private Button myResetButton;
	private Slider mySpeedSlider;
	private ComboBox<String> mySimulationChooser;
	private ColorPicker[] myColorPickers;

	// get strings from resource file
	private ResourceBundle myResources;

	private boolean isPaused = true;
	// default speed value is in the middle
	private double mySpeedMultiplier = .5;
	private Timeline timer;
	private BorderPane myRoot;

	public GUI(SimulationModel simulation, String language) {
		mySimulationModel = simulation;
		mySimulation = new Simulation();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		timer = new Timeline();
		myXMLManager = new XMLManager();
	}

	/**
	 * Initialize the display and updates only runs once per load of the game
	 */
	public void init(Stage primaryStage) {
		myRoot = new BorderPane();

		// sets padding in order of top, right, bottom, and left
		// 20 is based on whatever looked nice, arbitrary
		int padding = 10;
		myRoot.setPadding(new Insets(SCREENHEIGHT / padding, SCREENWIDTH / padding, SCREENWIDTH / padding,
				SCREENHEIGHT / padding));

		// set grid extents to a of whichever is smaller, width or height
		// .75 is arbitrary value for aesthetic purposes
		gridSideSize = (int) Math.min(SCREENHEIGHT * .75, SCREENWIDTH * .75);

		myRoot.setCenter(resetGrid());

		// must do before initiate the grid so mySimulationChooser combBox is
		// initiated
		myRoot.setBottom(setUpBottom());

		// initializes grid
		mySimulation.setInitialGrid(mySimulationModel);
		myGrid = updateGrid(gridSideSize);

		primaryStage.setScene(new Scene(myRoot, SCREENWIDTH, SCREENHEIGHT, BACKGROUND));
		primaryStage.setTitle(myResources.getString("WindowTitle"));
		primaryStage.show();
	}

	/**
	 * Draws and colors a rectangular grid of squares
	 * 
	 * @return a new grid object to add to the scene
	 */
	private Node updateGrid(int gridExtents) {
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

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
	 * Creates display that the user can interact with that goes along the bottom
	 */
	private Node setUpBottom() {
		HBox buttonLine = new HBox();
		buttonLine.setAlignment(Pos.CENTER);
		mySimulationChooser = new ComboBox<String>(mySimulationTypes);
		// 4 is an arbitrary value for aethstetic purposes
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
				// System.out.println(mySimulationModel.getName());
				myRoot.setCenter(resetGrid());
				play();
			}
		});
		myResetButton = makeButton("ResetCommand", event -> myRoot.setCenter(resetGrid()));
		// creates the play/pause toggle button
		myPlayButton = makeButton("PlayCommand", event -> play());
		myStepButton = makeButton("StepCommand", event -> step());
		mySpeedSlider = makeSlider(.1, 2, mySpeedMultiplier, .1);
		mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySpeedMultiplier = newValue.doubleValue();
				timer.setRate(mySpeedMultiplier);
			}
		});

		buttonLine.getChildren().add(mySimulationChooser);
		buttonLine.getChildren().add(myResetButton);
		buttonLine.getChildren().add(myPlayButton);
		buttonLine.getChildren().add(myStepButton);
		buttonLine.getChildren().addAll(mySpeedSlider);

		return buttonLine;
	}
/**
 * 
 */
	private Slider makeSlider(double min, double max, double increment, double changingValue){
		Slider newSlider = new Slider();
		// slider is based on percentages, hence the 0 to 10 and default value
		// which then multiplies by duration
		newSlider.setMin(min);
		newSlider.setMax(2);
		// Sets default slider location
		newSlider.setValue(changingValue);
		
		newSlider.setBlockIncrement(increment);
		// will snap to integers
		// mySpeedSlider.setSnapToTicks(true);
		return newSlider;
	}
	/**
	 * Sets up User input that modifies cells that appears on the left side
	 */
	private Node setUpLeft(int numberOfTypes) {
		
		Group colorPickerGroup = new Group();
		
		Random randomGenerator = new Random();
for (int i = 0; i < numberOfTypes; i++){
	myColorPickers[i] = new ColorPicker();
	Color newColor = randomLightColor();
	myColorPickers[i].setValue(newColor);
	colorPickerGroup.getChildren().add(myColorPickers[i]);
	myColorPickers[i].setOnAction(new EventHandler() {
		@Override
		public void handle(Event e) {
			myGrid.setColor(i, newColor);
		}
	});
}
	/**
	 * @return a new Random pastel Color
	 */
	private Color randomLightColor(){
		// creates a bright, light color
		Random randomGenerator = new Random();
		double hue  = randomGenerator.nextDouble();
		double saturation = 1.0 ;
		double brightness = 1.0;
		return Color.hsb(hue, saturation,brightness);
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
		final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));

		Button newButton = new Button();
		String label = myResources.getString(name);
		if (label.matches(IMAGEFILE_SUFFIXES)) {
			newButton.setGraphic(
					new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PACKAGE + label))));
		} else {
			newButton.setWrapText(true);
			newButton.setText(label);
		}
		newButton.setShape(new Circle(SCREENWIDTH / 10));
		newButton.setPrefWidth(SCREENWIDTH / 5);
		newButton.setPrefHeight(SCREENWIDTH / 10);
		newButton.setOnAction(handler);

		return newButton;
	}

	/**
	 * Reramdonizes the simulation Triggered by a button press
	 * 
	 * @return a new grid of the mySimulationType
	 */
	private Node resetGrid() {
		mySimulationModel.setRandomPositions();
		mySimulation.setInitialGrid(mySimulationModel);
		myGrid = updateGrid(gridSideSize);
		return myGrid;
	}

	/**
	 * Updates the simulation by one advancement Triggered by a button or usual
	 * run calls
	 */
	private void step() {
		mySimulationModel.setPositions(mySimulation.startNewRoundSimulation());
		myGrid = updateGrid(gridSideSize);
		myRoot.setCenter(myGrid);
	}

	/**
	 * Pauses, plays, or resumes the simulation Triggered by a button
	 */
	private void play() {
		myStepButton.setDisable(isPaused);
		myResetButton.setDisable(isPaused);

		isPaused = !isPaused;
		if (isPaused) {
			myPlayButton.setText(myResources.getString("PlayCommand"));
			timer.pause();
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