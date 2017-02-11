package cellsociety_team16;

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
	private Map<Integer, Paint> myColorMap;
	private Paint DEFAULT_COLOR_EMPTY = Color.WHITE;
	private Paint DEFAULT_COLOR_ACTIVE = Color.GREEN;
	private Paint DEFAULT_COLOR_SPECIAL = Color.RED;
	private Paint GRIDLINE_COLOR = Color.BLACK;
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
		myColorMap = new HashMap<Integer, Paint>();
		myColorMap.put(0, DEFAULT_COLOR_EMPTY);
		myColorMap.put(1, DEFAULT_COLOR_ACTIVE);
		myColorMap.put(2, DEFAULT_COLOR_SPECIAL);
	}

	// TODO put this into mysimulationmodel class
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
	public Node updateGrid(int gridExtents){
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

				shapely.setFill((Paint) myColorMap.get(myInts.get(index)));
				if(gridLines){
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
	abstract protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize);

	// abstract public List getCellPositions();

}
