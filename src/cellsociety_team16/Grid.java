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
	protected int cellExtents;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
	}

	// TODO put this into mysimulationmodel class
	public Node initialize(int cellExtents, SimulationModel simmod) {
		mySimulationModel = simmod;
		// If the simulationModel contains initial positions, use setGrid which
		// doesn't randomize new positions
		 //cellExtents = mySimulationModel.getCellSize();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();
		if (mySimulationModel.getPositions().isEmpty()) {
			mySimulationModel.setRandomPositions();
			mySimulation.setInitialGrid(mySimulationModel);
			return updateGrid(cellExtents);
		}
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(cellExtents);
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
	public Node resetGrid(int gridExtents) {
		mySimulationModel.setRandomPositions();
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(gridExtents);
	}

	// abstract public List getCellPositions();

}
