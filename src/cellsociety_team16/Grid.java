package cellsociety_team16;

import java.util.ArrayList;
import java.util.List;

import backend.Simulation;
import cellsociety_team16.SimulationModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
	// private Color EMPTY_COLOR = Color.WHITE;
	// private Color ACTIVE_COLOR = Color.GREEN;
	// private Color SPECIAL_COLOR = Color.RED;
	private List<Integer> myInts;
	private boolean gridSizeStatic;
	private boolean gridLines;
	protected Shape myShape;
	protected SimulationModel mySimulationModel;
	protected List<Paint> myColors;
	protected Simulation mySimulation;
	protected int myGridRows, myGridColumns;
	private boolean offsetHalf;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation, boolean bool) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
		myColors = new ArrayList<Paint>();
		myColors.add(0, simulationModel.getEmptyColor());
		myColors.add(1, simulationModel.getInactiveColor());
		myColors.add(2, simulationModel.getActiveColor());
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
		int offset;
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		myInts = mySimulationModel.getPositions();

		int index = 0;
		// int cellSize = Math.min(gridXSize / myGridRows, gridYSize /
		// myGridColumns);
		int cellSize = Math.min(gridExtents / myGridRows, gridExtents / myGridColumns);
		// int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * cellSize;
			if (offsetHalf && rowLoc % 2 == 0) {
				offset = cellSize / 2;
			} else {
				offset = 0;
			}
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				Shape shapely = drawShape(col_iter * sideSize, rowLoc, sideSize, sideSize);
				// Set an id for each sell based on index, so that we can keep
				// track of the changes of individual cell
				shapely.setId(Integer.toString(index));
				shapely.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						changeCellState(shapely);
					}
				});
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

	public void changeCellState(Shape cell) {
		Color currentColor = (Color) cell.getFill();
		int nextColor = myColors.indexOf(currentColor) >= myColors.size() - 1 ? 0 : myColors.indexOf(currentColor) + 1;
		cell.setFill(getColor(nextColor));
		List<Integer> positions = mySimulationModel.getPositions();
		positions.set(Integer.parseInt(cell.getId()), nextColor);
		mySimulationModel.setPositions(positions);
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

	protected void setHalfOffset(boolean bool) {
		offsetHalf = bool;
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
	 * @return a shape determined by the grid subclass called
	 */
	abstract Shape drawShape(double xLoc, double yLoc, double cellSize);

	// abstract public List getCellPositions();

}