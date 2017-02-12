package cellsociety_team16;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

import xml.XMLSimulation;

/**
 * Simulation Model
 * 
 * @author Yanbo Fang
 *
 */
public abstract class SimulationModel {

//	public static final Color ACTIVE_COLOR = Color.RED;
//	public static final Color INACTIVE_COLOR = Color.GREEN;
//	public static final Color EMPTY_COLOR = Color.WHITE;

	private String name;
	private int rows;
	private int cols;
	private double activePercentage;
	private double inactivePercentage;
	private double emptyPercentage;
	private List<Integer> myPositions;
	private List<Integer> myCounts;
	private List<String> availableColorsStrings = new ArrayList<String>();
	private List<Color> myAvailableColors;
	private List<Color> myColors;
	private String cellShape;
	private String activeColor;
	private String inactiveColor;
	private String emptyColor;
	private int cellSize;

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
		availableColorsStrings.add(emptyColor);
		availableColorsStrings.add(inactiveColor);
		availableColorsStrings.add(activeColor);
		myAvailableColors = this.setColorsAvailable();
		cellSize = simulation.getCellSize();
		myColors = this.setColors();
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
	 * Get the number of columns
	 * 
	 * @return
	 */
	public int getCols() {
		return this.cols;
	}

	public String getCellShape() {
		return this.cellShape;
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
			e.printStackTrace();
			return null;
		}
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

	public int getCellSize() {
		return this.cellSize;
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
			if (i == 0) {
				myCounts.set(0, myCounts.get(0) + 1);
			} else if (i == 1) {
				myCounts.set(1, myCounts.get(1) + 1);
			} else if (i == 2) {
				myCounts.set(2, myCounts.get(2) + 1);
			}
		}
		return myCounts;
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
	 * Set a list of random positions of cells
	 * 
	 * @return
	 */
	public void setRandomPositions() {
		Integer[] arry = new Integer[rows * cols];
		Arrays.fill(arry, -1);
		myPositions = Arrays.asList(arry);
		randomize(activePercentage, 2, myPositions);
		randomize(inactivePercentage, 1, myPositions);
		randomize(emptyPercentage, 0, myPositions);
	}

	private void randomize(double percentage, int value, List<Integer> lst) {
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
				lst.set(i, arry[i]);
			}
		}
	}
}
