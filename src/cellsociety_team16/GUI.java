package cellsociety_team16;

import backend.Simulation;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import java.util.ArrayList;
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
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.util.Duration;
import xml.XMLException;
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
	// TODO mess with min and max speed values
	public static final double MAX_SPEED = 1000; // in ms

	// files the user can load
	// TODO lol need to change
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
	private Grid myGrid;
	// get information on cell
	private Simulation mySimulation;
	private XMLManager myXMLManager;
	private SimulationModel mySimulationModel;
	private int myGridRows, myGridColumns;
	private List<Color> myColors;
	
	//graph
	private PopulationGraph myGraph;

	// user input fields
	private Button myLoadButton;
	private Button mySaveButton;
	private Button myPlayButton;
	private Button myStepButton;
	private Button myResetButton;
	private Slider mySpeedSlider;
	private ComboBox<String> mySimulationChooser;
	private ArrayList<ColorPicker> myColorPickers;
	private ArrayList<Slider> myValueSliders;
	private ComboBox<Object> myShapeChooser;
	private CheckBox addGridLines;

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
		myGrid = new SquareGrid(mySimulationModel, mySimulation);
		// set grid extents to a of whichever is smaller, width or height
		// .75 is arbitrary value for aesthetic purposes
		gridSideSize = (int) Math.min(SCREENHEIGHT * .75, SCREENWIDTH * .75);
	}

	/**
	 * Initialize the display and updates only runs once per load of the
	 * simulation
	 * 
	 * @throws Exception
	 */
	// TODO add more windows
	public void init(Stage primaryStage) {
		myStage = primaryStage;
		myRoot = new BorderPane();

		// sets padding in order of top, right, bottom, and left
		// 20 is based on whatever looked nice, arbitrary
		int padding = 10;
		myRoot.setPadding(new Insets(SCREENHEIGHT / padding, SCREENWIDTH / padding, SCREENWIDTH / padding,
				SCREENHEIGHT / padding));

		myRoot.setCenter(myGrid.initialize(gridSideSize, mySimulationModel));

		// must do before initiate the grid so mySimulationChooser combBox is
		// initiated
		// TODO see if still true
		myRoot.setBottom(setUpBottom());
		myRoot.setTop(setUpTop());

		primaryStage.setScene(new Scene(myRoot, SCREENWIDTH, SCREENHEIGHT, BACKGROUND));
		primaryStage.setTitle(myResources.getString("WindowTitle"));
		primaryStage.show();
	}

	private Node setUpTop(){
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);
		top.setMaxHeight(SCREENHEIGHT/5);
		myGraph = new PopulationGraph(mySimulationModel);
		top.getChildren().add(myGraph.createPopulationGraph());
		return top;
	}
	
	
	
	/**
	 * Creates display that the user can interact with that goes along the
	 * bottom throws an exception if the file loaded is not suitable
	 */
	private Node setUpBottom() {
		HBox buttonLine = new HBox();
		buttonLine.setAlignment(Pos.CENTER);
		// TODO just click to load a new file
		// Click to save current settings
		mySimulationChooser = new ComboBox<String>(mySimulationTypes);
		// 4 is an arbitrary value for aethstetic purposes
		mySimulationChooser.setVisibleRowCount(4);
		// sets to display which simulation the user originally chose
		mySimulationChooser.setValue(mySimulationType);
		mySimulationChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
				// resets the simulation type that will be displayed
				// TODO see if can take a string or xml file
				mySimulationModel = myXMLManager.getSimulationModel(newValue);
				// If the simulationModel contains initial positions, use
				// setGrid which doesn't randomize new positions
				myGrid.initialize(gridSideSize, mySimulationModel);
				// mySimulationModel.setRandomPositions();
				// mySimulation.setInitialGrid(mySimulationModel);
				// System.out.println(mySimulationModel.getName());
				myRoot.setCenter(myGrid.resetGrid(gridSideSize));
				play();
			}
		});
		// mySaveButton = makeButton("SaveFileCommand", event ->
		// myXMLManager.saveFile();
		// }
		// myLoadButton = makeButton("LoadFileCommand", event -> {
		// try {
		// myXMLManager.start(myStage);
		// } catch (Exception e) {
		// throw new XMLException(e);
		// }
		// mySimulationModel = myXMLManager.getSimulationModel();
		// myGrid.initialize(gridSideSize);
		// });
		myResetButton = makeButton("ResetCommand", event -> myRoot.setCenter(myGrid.resetGrid(gridSideSize)));
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
// buttonLine.getChildren().add(mySaveButton);
		// buttonLine.getChildren().add(myLoadButton);
		buttonLine.getChildren().addAll(myResetButton, myPlayButton, myStepButton, mySpeedSlider);

		return buttonLine;
	}

	/**
	 * 
	 * @param min
	 *            value of the slider
	 * @param max
	 *            value of the slider
	 * @param increment
	 * @param changingValue
	 * @return
	 */
	private Slider makeSlider(double min, double max, double increment, double changingValue) {
		Slider newSlider = new Slider();
		// slider is based on percentages, hence the 0 to 10 and default value
		// which then multiplies by duration
		newSlider.setMin(min);
		newSlider.setMax(max);
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
	// TODO throws NoGridException
	private Node setUpLeft(int numberOfTypes) {
		VBox userInput = new VBox();
		Group colorPickerGroup = new Group();
		Group sliderGroup = new Group();
		Random randomGenerator = new Random();

		for (int i = 0; i < numberOfTypes; i++) {
			myColorPickers.add(i, new ColorPicker());
			Color newColor = randomLightColor();
			myColorPickers.get(i).setValue(newColor);
			colorPickerGroup.getChildren().add(myColorPickers.get(i));

			myColorPickers.get(i).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					myGrid.setColor(myColorPickers.indexOf(this), newColor);
				}
			});
			// myValueSliders.add(i, makeSlider(0, 100, ))
		}

		return userInput;
	}

	/**
	 * @return a new Random pastel Color
	 */
	private Color randomLightColor() {
		// creates a bright, light color
		Random randomGenerator = new Random();
		double hue = randomGenerator.nextDouble();
		double saturation = 1.0;
		double brightness = 1.0;
		return Color.hsb(hue, saturation, brightness);
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
	 * Updates the simulation by one advancement Triggered by a button or usual
	 * run calls
	 */
	private void step() {
		mySimulationModel.setPositions(mySimulation.startNewRoundSimulation());
		myRoot.setCenter(myGrid.updateGrid(gridSideSize));
		myGraph.updateGraph(mySimulationModel, myGraph.getCurrentX() + 0.1);
		
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