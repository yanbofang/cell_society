package simulation_models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

import xml.XMLSimulation;

/**
 * // This entire file is part of my masterpiece. This is an abstract class of
 * Simulation Model, it initialize with the data in the XML and then is used to
 * communicate between the frontend and the backend. I chose this part as my
 * masterpiece be cause this signifies my improved understanding of inheritance
 * hierarchy. Unlike the first assignment, where I also used inheritance
 * hierarchy for the Brick component. As my TA pointed out, "There seems to be
 * no functional difference between any of the Brick types." This time, however,
 * each specific subclass has fields and methods that are unique to their own
 * simulation. For instance, the SegregationModel has the satisfactionRate
 * variable, while the SlimeMoldsModel has concentrationGate and
 * concentrationAmount. In addition, they all need to implement the
 * numberOfStates method, which return the number of states for the current
 * simulation. Meanwhile, the subclass also share lots of common fields and
 * methods such as number of rows and cols, which are implemented in the
 * abstract class.
 * 
 * Simulation Model
 * 
 * @author Yanbo Fang
 *
 */
public abstract class SimulationModel {

	private String name;
	private int rows;
	private int cols;
	private double activePercentage;
	private double inactivePercentage;
	private double emptyPercentage;
	private List<Integer> myPositions;
	private List<Integer> myCounts;
	private List<String> availableColorsStrings;
	private List<Color> myAvailableColors;
	private List<Color> myColors;
	private List<Integer> myAmounts;
	private String cellShape;
	private String activeColor;
	private String inactiveColor;
	private String emptyColor;
	private String edgeType;
	private int cellSize;
	private int numOfNeighbors;
	private boolean myGridLines;

	/**
	 * Constructor for SimulationModel
	 * 
	 * @param simulation
	 */
	public SimulationModel(XMLSimulation simulation) {
		name = simulation.getName();
		rows = simulation.getRows();
		cols = simulation.getCols();
		activePercentage = simulation.getActivePercentage();
		inactivePercentage = simulation.getInactivePercentage();
		emptyPercentage = simulation.getEmptyPercentage();
		myPositions = simulation.getPositions();
		cellShape = simulation.getCellShape();
		activeColor = simulation.getActiveColor();
		inactiveColor = simulation.getInactiveColor();
		emptyColor = simulation.getEmptyColor();
		availableColorsStrings = new ArrayList<String>();
		availableColorsStrings.add(emptyColor);
		availableColorsStrings.add(inactiveColor);
		availableColorsStrings.add(activeColor);
		myAvailableColors = this.setColorsAvailable();
		cellSize = simulation.getCellSize();
		numOfNeighbors = simulation.getNumOfNeighbors();
		myAmounts = this.setAmounts(simulation.getAmounts());
		myColors = this.setColors();
		myGridLines = simulation.getGridLines();
		edgeType = simulation.getEdgeType();
	}

	/**
	 * Get the name of the Simulation
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the number of rows
	 * 
	 * @return
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Set the number of rows
	 * 
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Get the number of columns
	 * 
	 * @return
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Set the number of columns
	 * 
	 * @param cols
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Get the shape of the cell
	 * 
	 * @return
	 */
	public String getCellShape() {
		return this.cellShape;
	}

	/**
	 * Set the shape of the cell
	 * 
	 * @param shape
	 */
	public void setCellShape(String shape) {
		this.cellShape = shape;
	}

	/**
	 * Return a Color object for empty cells
	 * 
	 * @return
	 */
	public Color getEmptyColor() {
		return this.myAvailableColors.get(0);
	}

	/**
	 * Return a Color object for inactive cells
	 * 
	 * @return
	 */
	public Color getInactiveColor() {
		return this.myAvailableColors.get(1);
	}

	/**
	 * Return a Color object for active cells
	 * 
	 * @return
	 */
	public Color getActiveColor() {
		return this.myAvailableColors.get(2);
	}

	/**
	 * Return the size of the cell
	 * 
	 * @return
	 */
	public int getCellSize() {
		return this.cellSize;
	}

	/**
	 * Set the size of the cell
	 * 
	 * @param size
	 */
	public void setCellSize(int size) {
		this.cellSize = size;
	}

	/**
	 * Return the number of neighbors for a cell
	 * 
	 * @return
	 */
	public int getNumOfNeighbors() {
		return this.numOfNeighbors;
	}

	/**
	 * Set how many neighbors a cell has
	 * 
	 * @param neighbors
	 */
	public void setNumOfNeighbors(int neighbors) {
		this.numOfNeighbors = neighbors;
	}

	/**
	 * Return an arraylist of mymyCounts of 3 types of cells, index 0 is the
	 * count of empty ones, index 1 is the inactive, 2 is the active
	 * 
	 * @return
	 */
	public List<Integer> getCounts() {
		myCounts = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
		for (Integer i : myPositions) {
			myCounts.set(i, myCounts.get(i) + 1);
		}
		// // Update the percentage
		// this.setEmptyPercentage((double) myCounts.get(0) / (rows * cols));
		// this.setInactivePercentage((double) myCounts.get(1) / (rows * cols));
		// this.setActivePercentage((double) myCounts.get(2) / (rows * cols));
		return myCounts;
	}

	/**
	 * Return the percentage of active cells in the current model
	 * 
	 * @return
	 */
	public double getActivePercentage() {
		return this.activePercentage;
	}

	/**
	 * Return the percentage of inactive cells in the current model
	 * 
	 * @return
	 */
	public double getInactivePercentage() {
		return this.inactivePercentage;
	}

	/**
	 * Return the percentage of empty cells in the current model
	 * 
	 * @return
	 */
	public double getEmptyPercentage() {
		return this.emptyPercentage;
	}

	/**
	 * set up the percentage of the "active" cells
	 * 
	 * @param percentage
	 */
	public void setActivePercentage(double percentage) {
		this.activePercentage = percentage;
	}

	/**
	 * set up the percentage of the "inactive" cells
	 * 
	 * @param percentage
	 */
	public void setInactivePercentage(double percentage) {
		this.inactivePercentage = percentage;
	}

	/**
	 * set up the percentage of the "active" cells
	 * 
	 * @param percentage
	 */
	public void setEmptyPercentage(double percentage) {
		this.emptyPercentage = percentage;
	}

	/**
	 * Return a boolean value of whether to show the grid lines
	 * 
	 * @return
	 */
	public boolean getGridLines() {
		return this.myGridLines;
	}

	/**
	 * Set whether to show the grid lines
	 * 
	 * @param gridLines
	 */
	public void setGridLines(boolean gridLines) {
		this.myGridLines = gridLines;
	}

	public String getEdgeType() {
		return this.edgeType;
	}

	/**
	 * Get the state of each cell in the grid, the value at a specific index
	 * signifies the state of that cell: 0 is empty, 1 is inactive, and 2 is
	 * active
	 * 
	 * @return
	 */
	public List<Integer> getPositions() {
		return this.myPositions;
	}

	/**
	 * Set the state of each cell in the grid
	 * 
	 * @param lst
	 */
	public void setPositions(List<Integer> lst) {
		this.myPositions = lst;
		this.setColors();
	}

	/**
	 * Return a list of the amounts of items at corresponding indices.
	 * 
	 * @return
	 */
	public List<Integer> getAmounts() {
		return this.myAmounts;
	}

	/**
	 * Set the amounts and return it
	 * 
	 * @param amounts
	 * @return
	 */
	public List<Integer> setAmounts(List<Integer> amounts) {
		// if amounts is empty, initialize the list to 1
		if (amounts == null || amounts.isEmpty()) {
			myAmounts = new ArrayList<Integer>();
			for (int i = 0; i < rows * cols; i++)
				myAmounts.add(1);
		} else {
			this.myAmounts = amounts;
		}
		return myAmounts;
	}

	/**
	 * Get the color of each cell in the grid
	 * 
	 * @return
	 */
	public List<Color> getColors() {
		myColors = this.setColors();
		return this.myColors;
	}

	private List<Color> setColors() {
		Color[] arry = new Color[rows * cols];
		for (int i = 0; i < myPositions.size(); i++) {
			myAvailableColors.get(myPositions.get(i));
		}
		return Arrays.asList(arry);
	}

	/**
	 * Abstract method, get the number of states for specific simulation. e.g. 2
	 * meaning there are 2 type of cells,
	 * 
	 * @return
	 */
	public abstract int numberOfStates();

	/**
	 * Set a random list with the value at a specific index being the state of
	 * the cell - 0 is empty, 1 is inactive and 2 is active
	 * 
	 * @return
	 */
	public void setRandomPositions() {
		Integer[] arry = new Integer[rows * cols];
		Arrays.fill(arry, -1);
		myPositions = Arrays.asList(arry);
		randomize(activePercentage, 2);
		randomize(inactivePercentage, 1);
		randomize(emptyPercentage, 0);
	}

	private void randomize(double percentage, int value) {
		int numofCells = rows * cols;
		Integer[] arry = new Integer[numofCells];
		Arrays.fill(arry, -1);
		int count = 0;
		Random rand = new Random();
		while (count < numofCells * percentage) {
			int x = rand.nextInt(numofCells);
			if ((arry[x] == -1) && myPositions.get(x) == -1) {
				arry[x] = value;
				count++;
			}
		}
		for (int i = 0; i < numofCells; i++) {
			if (arry[i] != -1) {
				myPositions.set(i, arry[i]);
			}
		}
	}

	private List<Color> setColorsAvailable() {
		myAvailableColors = new ArrayList<Color>();
		try {
			for (String s : availableColorsStrings) {
				Color c = (Color) Class.forName("javafx.scene.paint.Color").getField(s.toUpperCase()).get(null);
				myAvailableColors.add(c);
			}
			return myAvailableColors;
		} catch (Exception e) {
			return null;
		}
	}

}
