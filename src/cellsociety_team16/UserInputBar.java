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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

	// buttons
	ComboBox<Shape> myShapeChooser;
	private ArrayList<ColorPicker> myColorPickers;
	private ArrayList<Slider> myValueSliders;
	private CheckBox myGridLines;
	private Slider[] slidersAvailable;
	private double[] changingValues;
	private static int EMPTY_INDEX = 0;
	private static int ACTIVE_INDEX = 1;
	private static int SPECIAL_INDEX = 2;

	public UserInputBar(SimulationModel model, XMLManager manager, Grid grid, ResourceBundle resources) {
		mySimulationModel = model;
		myXMLManager = manager;
		myGrid = grid;
		myResources = resources;
		slidersAvailable = new Slider[3];
		slidersAvailable[EMPTY_INDEX] = makeSlider(0, 100, 10, mySimulationModel.getEmptyPercentage());
		slidersAvailable[EMPTY_INDEX].setDisable(true);
		// slidersAvailable[EMPTY_INDEX].valueProperty().addListener(new
		// ChangeListener<Number>() {
		// @Override
		// public void changed(ObservableValue<? extends Number> observed,
		// Number prevValue, Number newValue) {
		// mySimulationModel.setEmptyPercentage(newValue.doubleValue());
		// }
		// });
		slidersAvailable[ACTIVE_INDEX] = makeSlider(0, 100, 10, mySimulationModel.getInactivePercentage());
		slidersAvailable[ACTIVE_INDEX].valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySimulationModel.setInactivePercentage(newValue.doubleValue());
				updateSliders(slidersAvailable[ACTIVE_INDEX], slidersAvailable[SPECIAL_INDEX],
						slidersAvailable[EMPTY_INDEX], newValue.doubleValue());
			}
		});
		slidersAvailable[SPECIAL_INDEX] = makeSlider(0, 100, 10, mySimulationModel.getActivePercentage());
		slidersAvailable[SPECIAL_INDEX].valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observed, Number prevValue, Number newValue) {
				mySimulationModel.setActivePercentage(newValue.doubleValue());
				updateSliders(slidersAvailable[SPECIAL_INDEX], slidersAvailable[ACTIVE_INDEX],
						slidersAvailable[EMPTY_INDEX], newValue.doubleValue());
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
		Group colorPickerGroup = new Group();
		Group sliderGroup = new Group();
		Random randomGenerator = new Random();

		//
		myShapeChooser = new ComboBox<Shape>();
		myShapeChooser.getItems().addAll(

		);
		// myShapeChooser.setValue(myGrid.getGridType());
		// myShapeChooser.valueProperty().addListener(new
		// ChangeListener<String>() {
		// @Override
		// public void changed(ObservableValue<? extends String> observed,
		// String prevValue, String newValue) {
		// myGrid = new ;
		// }
		// });
		// myShapeChooser.setValue(mySimulationModel.getCellShape());
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

		// userInput.getChildren().addAll(myColorPickers);
		// userInput.getChildren().addAll(sliderGroup);

		myGridLines = new CheckBox(myResources.getString("GridLinesCheckBox"));
		myGridLines.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
				myGrid.setGridLines(new_val);
			}
		});

		userInput.getChildren().add(myGridLines);
		// Slider emptySlider = makeSlider(0,100,10,50,event ->
		// mySimulationModel.setEmptyPercentage(newValue));
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
		for (int i = 1; i < slidersAvailable.length; i++) {
			slidersAvailable[i].setDisable(isPaused);
		}
	}

	private double simulationValue(int i) {
		return 0;
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
		// will snap to integers
		newSlider.setSnapToTicks(true);
		return newSlider;
	}

	private void updateSliders(Slider thisSlider, Slider otherActiveSlider, Slider emptySlider, double newValue) {
		emptySlider.setValue(100 - newValue - otherActiveSlider.getValue());
		if (emptySlider.getValue() <= 0) {
			otherActiveSlider.setValue(100 - newValue);
		}
	}
}
