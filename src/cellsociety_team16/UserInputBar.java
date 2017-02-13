package cellsociety_team16;

import cellsociety_team16.SquareGrid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import simulation_models.SimulationModel;
import xml.XMLManager;

public class UserInputBar {
	SimulationModel mySimulationModel;
	// Simulation mySimulation;
	XMLManager myXMLManager;
	Grid myGrid;
	ImageView mySquareIcon, myTriangleIcon, myHexagonIcon;
	private ResourceBundle myResources;
	// not directly linking to GUI.myResources in case it gets updated

	ComboBox<String> myShapeChooser;
	private static double SLIDER_MAX = 1;
	private static double SLIDER_MIN = 0;
	private static double SLIDER_INCREMENT = .1;
	public static final String HEXAGON = "Hexagon";
	public static final String SQUARE = "Square";
	public static final String TRIANGLE = "Triangle";
	private ObservableList<String> myCellTypes = FXCollections.observableArrayList(HEXAGON, SQUARE, TRIANGLE);
	private ArrayList<ColorPicker> myColorPickers;

	private CheckBox myGridLines;
	private Slider[] slidersAvailable;
	private static int EMPTY_INDEX = 0;
	private static int ACTIVE_INDEX = 1;
	private static int SPECIAL_INDEX = 2;
	private int myPadding;

	public UserInputBar(SimulationModel model, XMLManager manager, Grid grid, ResourceBundle resources, int pads) {
		mySimulationModel = model;
		myXMLManager = manager;
		myGrid = grid;
		myResources = resources;
		myPadding = pads;

		slidersAvailable = new Slider[3];
		slidersAvailable[EMPTY_INDEX] = makeSlider(SLIDER_MIN, SLIDER_MAX, SLIDER_INCREMENT,
				mySimulationModel.getEmptyPercentage());
		slidersAvailable[EMPTY_INDEX].valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				updateSliders(slidersAvailable[EMPTY_INDEX], slidersAvailable[ACTIVE_INDEX],
						slidersAvailable[SPECIAL_INDEX]);
			}
		});
		slidersAvailable[ACTIVE_INDEX] = makeSlider(SLIDER_MIN, SLIDER_MAX, SLIDER_INCREMENT,
				mySimulationModel.getInactivePercentage());
		slidersAvailable[ACTIVE_INDEX].valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySimulationModel.setInactivePercentage(newValue.doubleValue());
				updateSliders(slidersAvailable[ACTIVE_INDEX], slidersAvailable[EMPTY_INDEX],
						slidersAvailable[SPECIAL_INDEX]);
			}
		});
		slidersAvailable[SPECIAL_INDEX] = makeSlider(SLIDER_MIN, SLIDER_MAX, SLIDER_INCREMENT,
				mySimulationModel.getActivePercentage());
		slidersAvailable[SPECIAL_INDEX].valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySimulationModel.setActivePercentage(newValue.doubleValue());
				updateSliders(slidersAvailable[SPECIAL_INDEX], slidersAvailable[EMPTY_INDEX],
						slidersAvailable[ACTIVE_INDEX]);
			}
		});
	}

	/**
	 * Sets up User input that modifies cells that appears on the left side Will
	 * need to update each time the simulationModel changes
	 * 
	 * @return left node
	 */
	// TODO throws NoGridException
	protected Node draw() {
		VBox userInput = new VBox();

		myShapeChooser = new ComboBox<String>(myCellTypes);

		myShapeChooser.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
				myGrid.setGridType(newValue);
			}
		});
		myShapeChooser.setValue(mySimulationModel.getCellShape());
		myColorPickers = new ArrayList<ColorPicker>();
		for (int i = 0; i < mySimulationModel.numberOfStates(); i++) {
			// may use default value from sim
			ColorPicker newPicker = makeColorPicker(i);
			newPicker.setValue(myGrid.getColor(i));
			myColorPickers.add(i, newPicker);
			userInput.getChildren().add(newPicker);
			userInput.getChildren().add(slidersAvailable[i]);
			// TODO figure out arraylist of varying values
			// myValueSliders.add(i, makeSlider(0, 100, 10, changingValue[i]))
		}

		myGridLines = new CheckBox(myResources.getString("GridLinesCheckBox"));
		myGridLines.setSelected(mySimulationModel.getGridLines());
		myGridLines.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
				myGrid.setGridLines(new_val);
			}
		});

		userInput.getChildren().add(myGridLines);
		userInput.getChildren().add(myShapeChooser);
		return userInput;
	}

	/**
	 * Creates a colorpicker with index of celltype that determine its color
	 * 
	 * @param index
	 * @return ColorPicker
	 */
	private ColorPicker makeColorPicker(int index) {
		ColorPicker newPicker = new ColorPicker();
		newPicker.setValue(myGrid.getColor(index));
		newPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// 0 gets base value, not lighter or darker
				myGrid.setBaseColor(index, newPicker.getValue());
				System.out.println(newPicker.getValue());
			}
		});
		return newPicker;
	}

	/**
	 * For anything do not want active and able to change when the simulation is
	 * running
	 * 
	 * @param isPaused
	 *            is false if the simulation is playing
	 */
	public void pause(boolean isPaused) {
		for (int i = 0; i < slidersAvailable.length; i++) {
			slidersAvailable[i].setDisable(isPaused);
		}
	}

	/**
	 * Makes a slider
	 * 
	 * @param min
	 *            minimum value of the slider
	 * @param max
	 *            maximum value of the slider
	 * @param increment
	 *            is what each tick mark would be set up to divide the slider
	 * @param defaultValue
	 *            is the value that it defaults to initially
	 * @return
	 */
	private Slider makeSlider(double min, double max, double increment, double defaultValue) {
		Slider newSlider = new Slider();
		// slider is based on percentages, hence the 0 to 10 and default value
		// which then multiplies by duration
		newSlider.setMin(min);
		newSlider.setMax(max);
		// Sets default slider location
		newSlider.setValue(defaultValue);

		newSlider.setBlockIncrement(increment);

		return newSlider;
	}

	/**
	 * Updates the values of the other sliders so the percentages are never over
	 * 100% the maximum slider value
	 * 
	 * @param controller
	 *            is the slider currently being moved, the others will update
	 *            over it
	 * @param moveFirst
	 *            is the slider who will be modified first in response to the
	 *            controller
	 * @param moveSecond
	 *            is the slider that will be modified second in order to account
	 *            for the controller's change
	 */
	private void updateSliders(Slider controller, Slider moveFirst, Slider moveSecond) {
		moveFirst.setValue(SLIDER_MAX - controller.getValue() - moveSecond.getValue());
		if (moveFirst.getValue() <= SLIDER_MIN) {
			moveSecond.setValue(SLIDER_MAX - controller.getValue());
		}
	}
}
