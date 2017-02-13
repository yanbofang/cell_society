package cellsociety_team16;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import xml.XMLManager;

public class UserInputBar {
	SimulationModel mySimulationModel;
	// Simulation mySimulation;
	XMLManager myXMLManager;
	Grid myGrid;

	// buttons
	ListCell<String> myShapeChooser;
	private ArrayList<ColorPicker> myColorPickers;
	private ArrayList<Slider> myValueSliders;
	private CheckBox myGridLines;

	public UserInputBar(SimulationModel model, XMLManager manager, Grid grid) {
		mySimulationModel = model;
		myXMLManager = manager;
		myGrid = grid;
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
//		myShapeChooser = new ListCell<Object>(
//				FXCollections.observableArrayList("SquareIcon", "TriangleIcon", "HexagonIcon"));
//myShapeChooser.setValue(myGrid.getGridType());
//		myShapeChooser.valueProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observed, String prevValue, String newValue) {
//				myGrid = new ;
//			}
//		});
		myColorPickers = new ArrayList<ColorPicker>();
		 for (int i = 0; i < mySimulationModel.numberOfStates(); i++) {
			 //may use default value from sim
		 ColorPicker newPicker = makeColorPicker(i);
		 newPicker.setValue(myGrid.getColor(i));
		 myColorPickers.add(i, newPicker);
		 colorPickerGroup.getChildren().add(newPicker);
		
		 // TODO figure out arraylist of varying values
		 // myValueSliders.add(i, makeSlider(0, 100, ))
		 }

		 userInput.getChildren().addAll(myColorPickers);

		//myGridLines = new CheckBox(myResources.getString("GridLinesCheckBox"));
//		myGridLines.selectedProperty().addListener(new ChangeListener<Boolean>() {
//			public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
//				myGrid.setGridLines(new_val);
//			}
//		});
//
		//userInput.getChildren().add(myGridLines);
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
}
