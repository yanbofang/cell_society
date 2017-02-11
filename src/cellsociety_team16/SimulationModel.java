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

	public static final Color ACTIVE_COLOR = Color.RED;
	public static final Color INACTIVE_COLOR = Color.GREEN;
	public static final Color EMPTY_COLOR = Color.WHITE;

	private String name;
	private int rows;
	private int cols;
	private double activePercentage;
	private double inactivePercentage;
	private double emptyPercentage;
	private List<Integer> positions;
	private List<Color> colors;

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
		positions = simulation.getPositions();
		colors = this.setColors();
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

	/**
	 * set up the percentage of the "active" cells
	 * 
	 * @param percentage
	 */
	void setActivePercentage(double percentage) {
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
		return this.positions;
	}

	/**
	 * Set the state of each cell in the grid
	 * 
	 * @param lst
	 */
	public void setPositions(List<Integer> lst) {
		this.positions = lst;
		this.setColors();
	}

	/**
	 * Get the color of each cell in the grid
	 * 
	 * @return
	 */
	public List<Color> getColors() {
		colors = this.setColors();
		return this.colors;
	}

	private List<Color> setColors() {
		Color[] arry = new Color[rows * cols];
		for (int i = 0; i < positions.size(); i++) {
			if (positions.get(i) == 0) {
				arry[i] = EMPTY_COLOR;
			} else if (positions.get(i) == 1) {
				arry[i] = INACTIVE_COLOR;
			} else {
				arry[i] = ACTIVE_COLOR;
			}
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
		positions = Arrays.asList(arry);
		randomize(activePercentage, 2, positions);
		randomize(inactivePercentage, 1, positions);
		randomize(emptyPercentage, 0, positions);
	}

	private void randomize(double percentage, int value, List<Integer> lst) {
		int numofCells = rows * cols;
		Integer[] arry = new Integer[numofCells];
		Arrays.fill(arry, -1);
		int count = 0;
		Random rand = new Random();
		while (count < numofCells * percentage) {
			int x = rand.nextInt(numofCells);
			if ((arry[x] == -1) && positions.get(x) == -1) {
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
