package cellsociety_team16;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xml.Simulation;

public class SimulationModel {

	public static final String ACTIVE_COLOR = "Red";
	public static final String INACTIVE_COLOR = "Yellow";
	public static final String EMPTY_COLOR = "White";

	private String name;
	private int rows;
	private int cols;
	private double activePercentage;
	private double inactivePercentage;
	private double emptyPercentage;
	private List<Integer> positions;
	private List<String> colors;

	public SimulationModel(Simulation simulation) {
		name = simulation.getName();
		rows = simulation.getRows();
		cols = simulation.getCols();
		activePercentage = simulation.getActivePercentage();
		inactivePercentage = simulation.getInactivePercentage();
		emptyPercentage = simulation.getEmptyPercentage();
	}

	public String getName() {
		return this.name;
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
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
	 * Get the state of each cell in the grid
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
	}

	/**
	 * Get the color of each cell in the grid
	 * 
	 * @return
	 */
	public List<String> getColors() {
		this.setColors();
		return this.colors;
	}

	private void setColors() {
		for (int i = 0; i < positions.size(); i++) {
			if (positions.get(i) == 0) {
				colors.set(i, EMPTY_COLOR);
			} else if (positions.get(i) == 1) {
				colors.set(i, INACTIVE_COLOR);
			} else {
				colors.set(i, ACTIVE_COLOR);
			}
		}
	}

	/**
	 * Get a list of random positions of cells
	 * 
	 * @return
	 */
	public List<Integer> getRandomPositions() {
		List<Integer> lst = new ArrayList<Integer>();
		randomize(lst, activePercentage, 2);
		randomize(lst, inactivePercentage, 1);
		for (int i = 0; i < rows * cols; i++) {
			if (lst.get(i) == null) {
				lst.set(i, 0);
			}
		}
		return lst;
	}

	private List<Integer> randomize(List<Integer> lst, double percentage, int value) {
		int count = 0;
		int numofCells = rows * cols;
		Random rand = new Random();
		while (count < numofCells * percentage) {
			int x = rand.nextInt(numofCells);
			if (!(lst.get(x) == null)) {
				lst.set(x, value);
				count++;
			}
		}
		return lst;
	}

}
