package cellsociety_team16;

import backend.GridInfo;
import backend.Simulation;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import javafx.util.Duration;
import simulation_models.SimulationModel;
import xml.XMLManager;
import xml.XMLWriter;

/**
 * Loads the GUI for Cell Society interface
 * 
 * @author Yanbo Fang
 * @author Kris Elbert
 *
 */
public class GUI {
	// constants
	public static final int SCREENWIDTH = 700;
	public static final int SCREENHEIGHT = 700;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String GRID_PACKAGE = "cellsociety_team16.";
	public static final Color BACKGROUND = Color.ALICEBLUE;
	public static final double MAX_SPEED = 1000; // in ms
	public static final double MINIMUM_SPEED_MULTIPLIER = .1;
	public static final double MAXIMUM_SPEED_MULTIPLIER = 2;

	// files the user can load
	// TODO lol need to change
	public static final String XML_GAME_OF_LIFE = "GameOfLife";
	public static final String XML_SEGREGATION = "Segregation";
	public static final String XML_SPREADING_FIRE = "SpreadingFire";
	public static final String XML_WATOR_WORLD = "WaTor";
	public static final String XML_SUGAR_SCAPE = "SugarScape";
	public static final String XML_SLIME_MOLDS = "SlimeMolds";

	// list of all of the xml files, user could potentially load new ones
	private ObservableList<String> mySimulationTypes = FXCollections.observableArrayList(XML_GAME_OF_LIFE,
			XML_SEGREGATION, XML_SPREADING_FIRE, XML_WATOR_WORLD, XML_SUGAR_SCAPE, XML_SLIME_MOLDS);
	// current simulation type
	private String mySimulationType;
	// used for initializing and updating grid
	private int gridSideSize;
	private Grid myGrid;
	// get information on cell
	private Simulation mySimulation;
	private XMLManager myXMLManager;
	private SimulationModel mySimulationModel;

	// graph
	private PopulationGraph myGraph;

	// user input fields
	private Button mySaveButton;
	private Button myPlayButton;
	private Button myStepButton;
	private Button myResetButton;
	private Button myNewSimulationButton;
	private Slider mySpeedSlider;
	private ComboBox<String> mySimulationChooser;
	private UserInputBar myLeftUI;

	// get strings from resource file
	protected ResourceBundle myResources;

	private boolean isPaused = true;
	// default speed value is in the middle
	private double mySpeedMultiplier = .5;
	private Timeline timer;
	private BorderPane myRoot;

	public GUI(SimulationModel simulation, String language) throws Exception {
		mySimulationModel = simulation;
		mySimulation = new Simulation();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		timer = new Timeline();
		myXMLManager = new XMLManager();
		
		Class<?> grid = Class.forName(GRID_PACKAGE + mySimulationModel.getCellShape() + "Grid");
		myGrid = (Grid) grid.getDeclaredConstructor(SimulationModel.class, Simulation.class)
				.newInstance(mySimulationModel, mySimulation);

		// set grid extents to a of whichever is smaller, width or height
		// .75 is arbitrary value for aesthetic purposes
		// makes a square grid
		gridSideSize = (int) Math.min(SCREENHEIGHT * .6, SCREENWIDTH * .75);
	}

	/**
	 * Initialize the display and updates only runs once per load of the
	 * simulation
	 */
	// TODO add ability to add more windows
	public void init(Stage primaryStage) {
		myRoot = new BorderPane();

		// sets padding in order of top, right, bottom, and left
		// 20 is based on whatever looked nice, arbitrary
		int padding = 100;
		myRoot.setPadding(new Insets(SCREENHEIGHT / padding, SCREENWIDTH / padding, SCREENWIDTH / padding,
				SCREENHEIGHT / padding));

		myRoot.setCenter(myGrid.initialize(gridSideSize, mySimulationModel));
		myRoot.getCenter().minWidth(gridSideSize);
		myRoot.getCenter().minHeight(gridSideSize);
		myRoot.setBottom(setUpBottom());
		myRoot.setTop(setUpTop());
		// must do before initiate the grid so can get colors
		// TODO see if still true
		myLeftUI = new UserInputBar(mySimulationModel, myXMLManager, myGrid, myResources, SCREENHEIGHT/padding);
		myRoot.setLeft(myLeftUI.draw());
		primaryStage.setScene(new Scene(myRoot, SCREENWIDTH, SCREENHEIGHT, BACKGROUND));
		primaryStage.setTitle(myResources.getString("WindowTitle"));
		primaryStage.show();
	}

	/**
	 * Sets up top node with a graph to keep track of the population change over
	 * time
	 * 
	 * @return top node
	 */
	private Node setUpTop() {
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);
		// 5 is what looked fine
		top.setMaxHeight(SCREENHEIGHT / 5);
		myGraph = new PopulationGraph(mySimulationModel);
		top.getChildren().add(myGraph.createPopulationGraph());
		return top;
	}

	/**
	 * Creates display that the user can interact with that goes along the
	 * bottom
	 * 
	 * @return bottom node
	 */
	private Node setUpBottom() {
		HBox buttonLine = new HBox();
		buttonLine.setAlignment(Pos.CENTER);
		VBox simulationModifiers = new VBox();
		simulationModifiers.setAlignment(Pos.CENTER);
		// TODO just click to load a new file
		// Click to save current settings
		mySimulationChooser = new ComboBox<String>(mySimulationTypes);
		// 4 is an arbitrary value for aethstetic purposes
		mySimulationChooser.setVisibleRowCount(4);
		// sets to display which simulation the user originally chose

		mySimulationChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
				// resets the simulation type that will be displayed
				mySimulationModel = myXMLManager.getSimulationModel(newValue);
				// If the simulationModel contains initial positions, use
				// setGrid which doesn't randomize new positions
				// TODO make a reset fn to simplify
				System.out.println("from setUpBottom:" + mySimulationModel.numberOfStates());

				myGrid.initialize(gridSideSize, mySimulationModel);
				myRoot.setCenter(myGrid.resetGrid(gridSideSize, mySimulationModel));
				// TODO see if need to pass in mySimulationModel
				myRoot.setLeft(myLeftUI.draw());
				play();
			}
		});
		mySimulationChooser.setValue(mySimulationType);

		mySaveButton = makeButton("SaveFileCommand", new Rectangle(SCREENWIDTH/10, SCREENHEIGHT/200), event -> saveFile());

		simulationModifiers.getChildren().add(mySimulationChooser);
		simulationModifiers.getChildren().add(mySaveButton);

		Shape circleButton = new Circle(SCREENWIDTH / 10);
		myResetButton = makeButton("ResetCommand", circleButton,
				event -> myRoot.setCenter(myGrid.resetGrid(gridSideSize, mySimulationModel)));
		// creates the play/pause toggle button
		myPlayButton = makeButton("PlayCommand", circleButton,event -> play());
		myStepButton = makeButton("StepCommand", circleButton,event -> step());

		Initializer init = new Initializer();
		myNewSimulationButton = makeButton("NewSimulationCommand", circleButton,event -> {
			try {
				init.newSimulation();
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(String.format("Couldn't start a new simulation"));
				a.showAndWait();
			}
		});

		mySpeedSlider = new Slider();

		mySpeedSlider.setMin(MINIMUM_SPEED_MULTIPLIER);
		mySpeedSlider.setMax(MAXIMUM_SPEED_MULTIPLIER);
		mySpeedSlider.setValue(mySpeedMultiplier);
		mySpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySpeedMultiplier = newValue.doubleValue();
				timer.setRate(mySpeedMultiplier);
			}
		});

		buttonLine.getChildren().add(simulationModifiers);
		buttonLine.getChildren().add(myResetButton);
		buttonLine.getChildren().add(myPlayButton);
		buttonLine.getChildren().add(myStepButton);
		buttonLine.getChildren().addAll(mySpeedSlider);
		buttonLine.getChildren().add(myNewSimulationButton);

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
	private Button makeButton(String name, Shape shape, EventHandler<ActionEvent> handler) {
		final String IMAGEFILE_SUFFIXES = String.format("(.*)\\.(%s)",
				String.join("|", ImageIO.getReaderFileSuffixes()));

		Button newButton = new Button();
		String label = myResources.getString(name);
		if (label.matches(IMAGEFILE_SUFFIXES)) {
			try {
				newButton.setGraphic(
						// TODO figure out why this does not work
						new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PACKAGE + label))));

			} catch (NullPointerException e) {
				newButton.setWrapText(true);
				newButton.setText(label);
			}
		} else {
			newButton.setWrapText(true);
			newButton.setText(label);
		}
		newButton.setShape(shape);
		newButton.setPrefWidth(SCREENWIDTH / 5);
		newButton.setPrefHeight(SCREENWIDTH / 10);
		newButton.setOnAction(handler);
		whenMouseOver(newButton);
		return newButton;
	}

	/**
	 * Adds a nice drop shadow when mouse hovers over the active button
	 * 
	 * @param button
	 */
	protected void whenMouseOver(Control button) {
		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(shadow);
			}
		});
		// Removing the shadow when the mouse cursor is off
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(null);
			}
		});
	}

	/**
	 * Updates the simulation by one advancement Triggered by a button or usual
	 * run calls
	 */
	private void step() {
		GridInfo gridinfo=mySimulation.startNewRoundSimulation();
		mySimulationModel.setPositions(gridinfo.getType());
		mySimulationModel.setAmounts(gridinfo.getAmount());
		myRoot.setCenter(myGrid.updateGrid(gridSideSize, mySimulationModel));
		myGraph.updateGraph(mySimulationModel, myGraph.getCurrentX() + 0.1);
	}

	/**
	 * Pauses, plays, or resumes the simulation Triggered by a button
	 */
	private void play() {
		myStepButton.setDisable(isPaused);
		myResetButton.setDisable(isPaused);
		myLeftUI.pause(isPaused);
		isPaused = !isPaused;
		if (isPaused) {
			myPlayButton.setText(myResources.getString("PlayCommand"));
			timer.pause();
		} else {
			myPlayButton.setText(myResources.getString("PauseCommand"));
			KeyFrame frame = new KeyFrame(Duration.millis(MAX_SPEED / 2), e -> step());
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.getKeyFrames().add(frame);
			timer.play();
		}
	}

	private void saveFile() {
		XMLWriter writer = new XMLWriter();
		writer.writeToXML(mySimulationModel);
	}

	/**
	 * May or may not use to tell SimulationModel which simulation to run
	 */
	public String getSimulationType() {
		return mySimulationChooser.getValue();
	}
}