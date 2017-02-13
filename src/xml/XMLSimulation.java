package xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Simple immutable value object representing Simulation data.
 * 
 * @author Yanbo Fang
 * @author Robert C. Duvall
 */
public class XMLSimulation {
	// name in data file that will indicate it represents data for this type of
	// object
	public static final String DATA_TYPE = "Simulation";

	// DEFAULT VALUES IN CASE OF VALUES NOT GIVEN
	public static final double DEFAULT_FIRE_PROBABILITY = 0.3;
	public static final double DEFAULT_TREE_PROBABILITY = 0;
	public static final double DEFAULT_SATISFACTION_RATE = 0.5;
	public static final int DEFAULT_SUGAR_METABOLISM = 1;
	public static final int DEFAULT_SUGAR_GROW_BACK_RATE = 1;
	public static final int DEFAULT_FISH_BREED = 3;
	public static final int DEFAULT_SHARK_BREED = 3;

	// field names expected to appear in data file holding values for this
	// object
	// simple way to create an immutable list
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "name", "author", "rows", "cols",
			"activePercentage", "inactivePercentage", "emptyPercentage", "fireProbability", "treeProbability",
			"satisfactionRate", "fishBreed", "sharkBreed", "sugarMetabolism", "sugarGrowBackRate", "positions",
			"amounts", "concentrationGate", "concentrationAmount" });

	public static final List<String> CONFIGURATION_FIELDS = Arrays.asList(new String[] { "cellShape", "activeColor",
			"inactiveColor", "emptyColor", "cellSize", "numOfNeighbors", "gridLines" });

	// specific data values for this instance
	private Map<String, String> myDataValues;

	public XMLSimulation(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	/**
	 * Get the name of the simulation
	 * 
	 * @return
	 */
	public String getName() {
		return myDataValues.get(DATA_FIELDS.get(0));
	}

	/**
	 * Get the authors
	 * 
	 * @return
	 */
	public String getAuthor() {
		return myDataValues.get(DATA_FIELDS.get(1));
	}

	/**
	 * Get the rows of the simulation
	 * 
	 * @return
	 */
	public int getRows() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(2)));
	}

	/**
	 * Get the columns of the simulation
	 * 
	 * @return
	 */
	public int getCols() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(3)));
	}

	/**
	 * Get the active cell percentage
	 * 
	 * @return
	 */
	public double getActivePercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(4)));
	}

	/**
	 * Get the inactive cell percentage
	 * 
	 * @return
	 */
	public double getInactivePercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(5)));
	}

	/**
	 * Get the empty cell percentage
	 * 
	 * @return
	 */
	public double getEmptyPercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(6)));
	}

	/**
	 * Get the fire probability
	 * 
	 * @return
	 */
	public double getFireProbability() {
		return myDataValues.get(DATA_FIELDS.get(7)).equals("") ? DEFAULT_FIRE_PROBABILITY
				: Double.parseDouble(myDataValues.get(DATA_FIELDS.get(7)));
	}

	/**
	 * Get the tree probability
	 * 
	 * @return
	 */
	public double getTreeProbability() {
		return myDataValues.get(DATA_FIELDS.get(8)).equals("") ? DEFAULT_TREE_PROBABILITY
				: Double.parseDouble(myDataValues.get(DATA_FIELDS.get(8)));
	}

	/**
	 * Get the satisfactionRate
	 * 
	 * @return
	 */
	public double getSatisfactionRate() {
		return myDataValues.get(DATA_FIELDS.get(9)).equals("") ? DEFAULT_SATISFACTION_RATE
				: Double.parseDouble(myDataValues.get(DATA_FIELDS.get(9)));
	}

	/**
	 * Get the fish breed
	 * 
	 * @return
	 */
	public int getFishBreed() {
		return myDataValues.get(DATA_FIELDS.get(10)).equals("") ? DEFAULT_FISH_BREED
				: Integer.parseInt(myDataValues.get(DATA_FIELDS.get(10)));
	}

	/**
	 * Get the shark breed
	 * 
	 * @return
	 */
	public int getSharkBreed() {
		return myDataValues.get(DATA_FIELDS.get(11)).equals("") ? DEFAULT_SHARK_BREED
				: Integer.parseInt(myDataValues.get(DATA_FIELDS.get(11)));
	}

	/**
	 * Get the sugar metabolism
	 * 
	 * @return
	 */
	public int getSugarMetabolism() {
		return myDataValues.get(DATA_FIELDS.get(12)).equals("") ? DEFAULT_SUGAR_METABOLISM
				: Integer.parseInt(myDataValues.get(DATA_FIELDS.get(12)));
	}

	/**
	 * Get the sugar grow back rate
	 * 
	 * @return
	 */
	public int getSugarGrowBackRate() {
		return myDataValues.get(DATA_FIELDS.get(13)).equals("") ? DEFAULT_SUGAR_GROW_BACK_RATE
				: Integer.parseInt(myDataValues.get(DATA_FIELDS.get(13)));
	}

	/**
	 * Get the positions list
	 * 
	 * @return
	 */
	public List<Integer> getPositions() {
		String ints = myDataValues.get(DATA_FIELDS.get(14));
		ArrayList<Integer> pos = new ArrayList<Integer>();
		for (int i = 0; i < ints.length(); i++) {
			Integer cellState = Integer.parseInt(ints.substring(i, i + 1));
			if (cellState < 0 || cellState > 2) {
				throw new XMLException("Invalid cell state value at index %d", i / 2);
			}
			pos.add(cellState);
			// Skip the space
			i++;
		}
		if (pos.size() > this.getCols() * this.getRows()) {
			throw new XMLException("Cell locaations are outside the bounds of the grid's size");
		}
		return pos;
	}

	/**
	 * Get the amounts list
	 * 
	 * @return
	 */
	public List<Integer> getAmounts() {
		String ints = myDataValues.get(DATA_FIELDS.get(15));
		if (ints == null || ints.isEmpty())
			return new ArrayList<Integer>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		for (int i = 0; i < ints.length() - 1; i++) {
			if (ints.substring(i, i + 1).equals(" "))
				continue;
			StringBuilder sb = new StringBuilder();
			while (!ints.substring(i, i + 1).equals(" ")) {
				sb.append(ints.substring(i, i + 1));
				i++;
			}
			amounts.add(Integer.parseInt(sb.toString()));
		}
		amounts.add(Integer.parseInt(ints.substring(ints.length() - 1)));

		if (amounts.size() > this.getCols() * this.getRows()) {
			throw new XMLException("Cell locaations are outside the bounds of the grid's size");
		}
		return amounts;
	}

	/**
	 * Get the concentration gate for slime molds
	 * 
	 * @return
	 */
	public int getConcentrationGate() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(16)));
	}

	/**
	 * Get the concentration amount for slime molds
	 * 
	 * @return
	 */
	public int getConcentrationAmount() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(17)));
	}

	/**
	 * Get the cell shape for the simulation
	 * 
	 * @return
	 */
	public String getCellShape() {
		return myDataValues.get(CONFIGURATION_FIELDS.get(0));
	}

	/**
	 * Get the color for the active cells
	 * 
	 * @return
	 */
	public String getActiveColor() {
		return myDataValues.get(CONFIGURATION_FIELDS.get(1));
	}

	/**
	 * Get the color for the inactive cells
	 * 
	 * @return
	 */
	public String getInactiveColor() {
		return myDataValues.get(CONFIGURATION_FIELDS.get(2));
	}

	/**
	 * Get the color for the empty cells
	 * 
	 * @return
	 */
	public String getEmptyColor() {
		return myDataValues.get(CONFIGURATION_FIELDS.get(3));
	}

	/**
	 * Get the size of the cell
	 * 
	 * @return
	 */
	public int getCellSize() {
		return Integer.parseInt(myDataValues.get(CONFIGURATION_FIELDS.get(4)));
	}

	/**
	 * Get the number of neighbors
	 * 
	 * @return
	 */
	public int getNumOfNeighbors() {
		return Integer.parseInt(myDataValues.get(CONFIGURATION_FIELDS.get(5)));
	}

	/**
	 * Get the boolean for whether show or not show the grid lines
	 * 
	 * @return
	 */
	public boolean getGridLines() {
		return Boolean.parseBoolean(myDataValues.get(CONFIGURATION_FIELDS.get(6)));

	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Simulation {\n");
		for (Map.Entry<String, String> e : myDataValues.entrySet()) {
			result.append("  " + e.getKey() + "='" + e.getValue() + "',\n");
		}
		result.append("}\n");
		return result.toString();
	}
}
