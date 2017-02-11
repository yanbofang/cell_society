package cellsociety_team16;

import java.util.List;
import java.util.Map;

import backend.Simulation;
import cellsociety_team16.SimulationModel;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Abstract class that will create a new grid in the visualization window
 * 
 * @author Elbert
 *
 */
public abstract class Grid {
	// TODO may not need
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	// Integers correspond to cell types and colors
	private List<Integer> myInts;
	private Map<Integer, Paint> myColorMap;
	private boolean gridSizeStatic;
	private boolean gridLines;
	protected Shape myShape;
	protected SimulationModel mySimulationModel;
	protected List myColors;
	protected Simulation mySimulation;
	protected int myGridRows, myGridColumns;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
	}	
	//TODO put this into mysimulationmodel class
	public Node initialize(){
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(8);
	}

	public void setColor(int cellType, Color newColor) {
		myColorMap.put(cellType, newColor);
	}

	private Paint getColor(int cellType) {
		return myColorMap.get(cellType);
	}

	public void setStaticGridSize(boolean yes) {
		gridSizeStatic = yes;
	}

	public void setGridLines(boolean boo) {
		gridLines = boo;
	}
	/**
	 * Draws and colors a rectangular grid of squares
	 * 
	 * @return a new grid object to add to the scene
	 */
	abstract public Node updateGrid(int gridExtents);
	/**
	 * Reramdonizes the simulation Triggered by a button press
	 * 
	 * @return a new grid of the mySimulationType
	 */
	abstract public Node resetGrid(int gridExtents);

	// abstract public List getCellPositions();

}
