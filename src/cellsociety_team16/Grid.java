package cellsociety_team16;

import java.util.ArrayList;
import java.util.List;

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
	private Color GRIDLINE_COLOR = Color.BLACK;
	private Color EMPTY_COLOR = Color.WHITE;
	private Color ACTIVE_COLOR = Color.GREEN;
	private Color SPECIAL_COLOR = Color.RED;
	private List<Integer> myInts;
	private boolean gridSizeStatic;
	private boolean gridLines;
	private SimulationModel mySimulationModel;
	private List<Paint> myColors;
	private Simulation mySimulation;
	private int myGridRows, myGridColumns;
	private double myOffsetPercentage;
	protected boolean myManipulatable;
	protected int cellSize;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation, double translationPercentage, boolean bool) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
		myColors = new ArrayList<Paint>();
		myColors.add(0, EMPTY_COLOR);
		myColors.add(1, ACTIVE_COLOR);
		myColors.add(2, SPECIAL_COLOR);
		myOffsetPercentage = translationPercentage;
		myManipulatable = bool;
	}

	/**
	 * Draws out a grid
	 * 
	 * @param gridExtents
	 *            determines cell size
	 * @return a grid of the simulationModelType
	 */
	public Node initialize(int gridExtents, SimulationModel simmod) {
		mySimulationModel = simmod;
		// If the simulationModel contains initial positions, use setGrid which
		// doesn't randomize new positions
		// cellExtents = mySimulationModel.getcellSize();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		if (mySimulationModel.getPositions().isEmpty()) {
			mySimulationModel.setRandomPositions();
		}
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(gridExtents);
	}

	/**
	 * Sets color of a certain type of cell: 0 is empty, 1 is active, 2 is
	 * special
	 * 
	 * @param cellType
	 * @param newColor
	 */
	public void setColor(int cellType, Color newColor) {
		myColors.add(cellType, newColor);
	}

	/**
	 * Gets value of color corresponding to which type of cell it is
	 * 
	 * @param cellType:
	 *            0 is empty, 1 is active, 2 is special
	 * @return that cell's color in Paint form
	 */
	private Paint getColor(int cellType) {
		return myColors.get(cellType);
	}

	/**
	 * Sets a finite or infinite grid
	 * 
	 * @param yes
	 *            if true makes the grid constant size
	 */
	public void setStaticGridSize(boolean yes) {
		gridSizeStatic = yes;
	}

	/**
	 * Adds lines around cells, including empty spaces
	 * 
	 * @param boo
	 *            if true adds grid lines
	 */
	public void setGridLines(boolean boo) {
		gridLines = boo;
	}

	/**
	 * Draws and colors a rectangular grid of squares
	 * 
	 * @return a new grid object to add to the scene
	 */
	public Node updateGrid(int gridExtents) {
		int onOffset;
		int rotateAngle;
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		myInts = mySimulationModel.getPositions();

		int index = 0;
		// int cellSize = Math.min(gridXSize / myGridRows, gridYSize /
		// myGridColumns);
		cellSize = Math.min(gridExtents / myGridRows, gridExtents / myGridColumns);
		// int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * cellSize;
			if (rowLoc % 2 == 0) {
				onOffset = 1;
			} else {
				onOffset = 0;
			}
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				if(myManipulatable && col_iter %2 == 0){
					rotateAngle = 180;
				} else {
					rotateAngle = 0;
				}
				Shape shapely = drawShape(cellSize* (col_iter + myOffsetPercentage*onOffset), rowLoc, cellSize, rotateAngle);

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

	protected void setOffset(double value) {
		myOffsetPercentage = value;
	}

	/**
	 * Draws a shape as determined by the subclass of grid
	 * 
	 * @param xLoc
	 *            corresponds to upper left hand side coordinate
	 * @param yLoc
	 *            corresponds to upper left hand side coordinate
	 * @param cellSize
	 *            corresponds to overall width of the cell
	 * @param rotateXAngle
	 * 			  sets rotation angle of shape
	 * @return a shape determined by the grid subclass called
	 */
	abstract Shape drawShape(double xLoc, double yLoc, double cellSize, int rotateXAngle);

	// abstract public List getCellPositions();

}