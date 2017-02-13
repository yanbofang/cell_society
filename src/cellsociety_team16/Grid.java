package cellsociety_team16;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import backend.Simulation;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import simulation_models.SimulationModel;

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
private static int EMPTY_INDEX = 0;
private static int ACTIVE_INDEX = 1;
private static int SPECIAL_INDEX = 2;

	private List<Integer> myInts;
	private boolean gridSizeStatic;
	private boolean gridLines;
	private SimulationModel mySimulationModel;
	private List<Color> myColors;
	private Simulation mySimulation;
	private int myGridRows, myGridColumns;
	private double myOffsetPercentage;
	protected boolean myManipulatable;
	protected int cellSize;
	protected String myShape;

	/**
	 * Declares a grid object
	 */
	public Grid(SimulationModel simulationModel, Simulation simulation, double translationPercentage, boolean bool) {
		mySimulationModel = simulationModel;
		mySimulation = simulation;
		myColors = new ArrayList<Color>();
		myOffsetPercentage = translationPercentage;
		myManipulatable = bool;
		
		System.out.println(simulationModel.getName());
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

		gridLines = mySimulationModel.getGridLines();
		
		setBaseColor(EMPTY_INDEX, mySimulationModel.getEmptyColor());
		setBaseColor(ACTIVE_INDEX, mySimulationModel.getInactiveColor());
		setBaseColor(SPECIAL_INDEX, mySimulationModel.getActiveColor());
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
	 * special Will reset color for all of those cell types
	 * 
	 * @param cellType
	 * @param newColor
	 * 
	 */
	public void setBaseColor(int cellType, Color newColor) {
		// default weight is no weight, 0
		try {
			myColors.set(cellType, newColor);
		} catch (IndexOutOfBoundsException e) {
			myColors.add(cellType, newColor);
		} catch (NullPointerException e) {
			setBaseColor(cellType, randomLightColor());
		}
	}

	/**
	 * Gets value of color corresponding to which type of cell it is Does not
	 * tell how light or dark the color is, only the base
	 * 
	 * @param cellType:
	 *            0 is empty, 1 is active, 2 is special
	 * @return that cell's color in Color form
	 */
	public Color getColor(int cellType) {
		try {
			return myColors.get(cellType);
		} catch (IndexOutOfBoundsException e) {
			setBaseColor(cellType, randomLightColor());
			return getColor(cellType);
		}
	}

	/**
	 * Gets value of color corresponding to which type of cell it is and weight
	 * of darkness or lightness
	 * 
	 * @param cellType:
	 *            0 is empty, 1 is active, 2 is special
	 * @param weight
	 *            determines how dark it should be, negative values make lighter
	 *            weight of 12 darkens WHITE to BLACK
	 * @return that cell's color in Paint form
	 */
	public Color getColor(int cellType, int weight) {
		// gets base color
		Color newColor = getColor(cellType);
		if (weight < 0) {
			for (int i = 0; i > weight; i--) {
				newColor = newColor.desaturate();
			}
		}
		for (int i = 0; i < weight; i++) {
			newColor = newColor.darker();
		}
		return newColor;
	}

	/**
	 * @return a new Random pastel Color
	 */
	private Color randomLightColor() {
		// creates a bright, light color
		Random randomGenerator = new Random();
		double hue = randomGenerator.nextDouble();
		double saturation = 1.0;
		double brightness = 1.0;
		return Color.hsb(hue, saturation, brightness);
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
		cellSize = Math.max(mySimulationModel.getCellSize(),
				Math.min(gridExtents / myGridRows, gridExtents / myGridColumns));
		// int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * cellSize;
			if (row_iter % 2 == 0) {
				onOffset = 1;
			} else {
				onOffset = 0;
			}
			if (row_iter % 2 == 0) {
				rotateAngle = 180;
			} else {
				rotateAngle = 0;
			}
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				if (col_iter % 2 == 1) {
					rotateAngle += 180;
				}
				Shape shapely = drawShape(cellSize * (col_iter + myOffsetPercentage * onOffset), rowLoc, cellSize,
						rotateAngle);

				// Set an id for each sell based on index, so that we can keep
				// track of the changes of individual cell
				shapely.setId(Integer.toString(index));
				shapely.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						changeCellState(shapely);
					}
				});
				// gets darkness or lightness of the square
				int capacity = 0;
				shapely.setFill(getColor(myInts.get(index), capacity));
				if (gridLines) {
					shapely.setStroke(GRIDLINE_COLOR);
				}
				if (col_iter % 2 == 1) {
					rotateAngle -= 180;
				}
				cells.getChildren().add(shapely);
				index++;
			}
		}
		return cells;
	}

	/**
	 * When clicked, will change color and communicate with simulation Model
	 * 
	 * @param cell
	 */
	public void changeCellState(Shape cell) {
		Color currentColor = (Color) cell.getFill();
		int nextColor = myColors.indexOf(currentColor) >= mySimulationModel.numberOfStates() - 1 ? 0
				: myColors.indexOf(currentColor) + 1;
		cell.setFill(getColor(nextColor));
		List<Integer> positions = mySimulationModel.getPositions();
		positions.set(Integer.parseInt(cell.getId()), nextColor);
		mySimulationModel.setPositions(positions);
		mySimulation.setInitialGrid(mySimulationModel);
	}

	/**
	 * Reramdonizes the simulation Triggered by a button press
	 * 
	 * @return a new grid of the mySimulationType
	 */
	public Node resetGrid(int gridExtents, SimulationModel simmod) {
		mySimulationModel = simmod;
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
	 *            sets rotation angle of shape
	 * @return a shape determined by the grid subclass called
	 */
	abstract Shape drawShape(double xLoc, double yLoc, double cellSize, int rotateXAngle);

	public String getGridType() {
		return myShape;
	}
	// abstract public List getCellPositions();
	// public Grid setGridType(){
	// model.getDeclaredConstructor(XMLSimulation.class).newInstance(xml)
	// }

}