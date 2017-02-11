package cellsociety_team16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backend.Simulation;
import cellsociety_team16.SimulationModel;
import javafx.scene.Group;
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
	private Paint DEFAULT_COLOR_EMPTY = Color.WHITE;
	private Paint DEFAULT_COLOR_ACTIVE = Color.GREEN;
	private Paint DEFAULT_COLOR_SPECIAL = Color.RED;
	private Paint GRIDLINE_COLOR = Color.BLACK;
	private boolean gridSizeStatic;
	private boolean gridLines;
	protected Shape myShape;
	protected SimulationModel mySimulationModel;
	private List<Paint> myColors;
	protected Simulation mySimulation;
	protected int myGridRows, myGridColumns;

	/**
	 * Initalizes a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
		myColors = new ArrayList<Paint>();
		myColors.add(0, DEFAULT_COLOR_EMPTY);
		myColors.add(1, DEFAULT_COLOR_ACTIVE);
		myColors.add(2, DEFAULT_COLOR_SPECIAL);
	}

	/**
	 * Draws out a grid
	 * 
	 * @param gridExtents
	 *            determines cell size
	 * @return a grid of the simulationModelType
	 */
	public Node initialize(int gridExtents) {
		// If the simulationModel contains initial positions, use setGrid which
		// doesn't randomize new positions
		if (mySimulationModel.getPositions().isEmpty()) {
			mySimulationModel.setRandomPositions();
			mySimulation.setInitialGrid(mySimulationModel);
			return updateGrid(gridExtents);
		}
		mySimulation.setInitialGrid(mySimulationModel);

		return updateGrid(gridExtents);
	}

	/**
	 * Resets color of a certain type of cell
	 * 
	 * @param cellType
	 *            where 0 corresponds to empty, 1 to active, and 2 to special
	 * @param newColor
	 *            sets the color the cellType will now be
	 */
	public void setColor(int cellType, Color newColor) {
		myColors.add(cellType, newColor);
	}

	/**
	 * @param cellType
	 *            where 0 corresponds to empty, 1 to active, and 2 to pecial
	 * @return the color of that cellType
	 */
	private Paint getColor(int cellType) {
		return myColors.get(cellType);
	}

	/**
	 * Sets the grid size as constant if true, infinite if false
	 * 
	 * @param yes
	 *            is true or false
	 */
	public void setStaticGridSize(boolean yes) {
		gridSizeStatic = yes;
	}

	/**
	 * Draws grid lines outlining the cells
	 * 
	 * @param boo
	 *            is true if lines are to be drawn
	 */
	public void setGridLines(boolean boo) {
		gridLines = boo;
	}

	/**
	 * Draws and colors a rectangular grid of squares
	 * 
	 * @return a new grid object to add to the scene
	 */
	//refactored code
	public Node updateGrid(int gridExtents) {
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		myInts = mySimulationModel.getPositions();

		int index = 0;

		int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * sideSize;

			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				Shape shapely = drawShape(col_iter * sideSize, rowLoc, sideSize, sideSize);

				shapely.setFill(getColor(myInts.get(index)));
				if (gridLines) {
					shapely.setStroke(GRIDLINE_COLOR);
				}
				cells.getChildren().add(shapely);
				index++;
			}
		}
		return cells;
	}

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

	/**
	 * Draws a shape of the specified size
	 * 
	 * @param xLoc
	 * @param yLoc
	 * @param xSize
	 * @param ySize
	 * @return that shape, dependent upon which subclass of Grid is made
	 */
	abstract protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize);

	// abstract public List getCellPositions();

}
