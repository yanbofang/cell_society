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
	protected Shape myShape;
	protected SimulationModel mySimulationModel;
	protected List<Paint> myColors;
	protected Simulation mySimulation;
	protected int myGridRows, myGridColumns;
	protected int gridExtents;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
		myColors = new ArrayList<Paint>();
		myColors.add(0, EMPTY_COLOR);
		myColors.add(1, ACTIVE_COLOR);
		myColors.add(2, SPECIAL_COLOR);
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
		// cellExtents = mySimulationModel.getCellSize();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		if (mySimulationModel.getPositions().isEmpty()) {
			mySimulationModel.setRandomPositions();
			mySimulation.setInitialGrid(mySimulationModel);
			return updateGrid(gridExtents);
		}
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(gridExtents);
	}

	public void setColor(int cellType, Color newColor) {
		myColors.add(cellType, newColor);
	}

	private Paint getColor(int cellType) {
		return myColors.get(cellType);
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
	//Oh my gosh I used to have a comment here why is it not here anymore
	abstract Shape drawShape(int xLoc, int yLoc, int xSize, int ySize);

	// abstract public List getCellPositions();

}