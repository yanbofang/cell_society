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
	// field names expected to appear in data file holding values for this
	// object
	// simple way to create an immutable list
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "name", "author", "rows", "cols",
			"activePercentage", "inactivePercentage", "emptyPercentage", "fireProbability", "treeProbability",
			"satisfactionRate", "fishBreed", "sharkBreed", "sugarMetabolism","sugarGrowBackRate", "positions", "amounts" });

	public static final List<String> CONFIGURATION_FIELDS = Arrays.asList(new String[]{"cellShape", "activeColor", "inactiveColor", "emptyColor", "cellSize", "numOfNeighbors"});
	
	// specific data values for this instance
	private Map<String, String> myDataValues;

	public XMLSimulation(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	// provide alternate ways to access data values if needed
	public String getName() {
		return myDataValues.get(DATA_FIELDS.get(0));
	}

	public String getAuthor() {
		return myDataValues.get(DATA_FIELDS.get(1));
	}

	public int getRows() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(2)));
	}

	public int getCols() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(3)));
	}

	public double getActivePercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(4)));
	}

	public double getInactivePercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(5)));
	}

	public double getEmptyPercentage() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(6)));
	}

	public double getFireProbability() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(7)));
	}

	public double getTreeProbability() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(8)));
	}

	public double getSatisfactionRate() {
		return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(9)));
	}

	public int getFishBreed() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(10)));
	}

	public int getSharkBreed() {
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(11)));
	}

	public int getSugarMetabolism(){
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(12)));
	}
	
	public int getSugarGrowBackRate(){
		return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(13)));
	}
	
	public List<Integer> getPositions() {
		String ints = myDataValues.get(DATA_FIELDS.get(14));
		ArrayList<Integer> pos = new ArrayList<Integer>();
		for (int i = 0; i < ints.length(); i++) {
			Integer cellState = Integer.parseInt(ints.substring(i, i + 1));
			if (cellState < 0 || cellState > 2) {
				throw new XMLException("Invalid cell state value at index %d", i / 2);
			}
			pos.add(cellState);
			//Skip the space
			i++;
		}
		if (pos.size() > this.getCols() * this.getRows()) {
			throw new XMLException("Cell locaations are outside the bounds of the grid's size");
		}
		return pos;
	}
	
	public List<Integer> getAmounts(){
		String ints = myDataValues.get(DATA_FIELDS.get(14));
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		for (int i = 0; i < ints.length(); i++) {
			Integer amount = Integer.parseInt(ints.substring(i, i + 1));
			amounts.add(amount);
			i++;
		}
		if (amounts.size() > this.getCols() * this.getRows()) {
			throw new XMLException("Cell locaations are outside the bounds of the grid's size");
		}
		return amounts;
	}
	

	public String getCellShape(){
		return myDataValues.get(CONFIGURATION_FIELDS.get(0));
	}
	
	public String getActiveColor(){
		return myDataValues.get(CONFIGURATION_FIELDS.get(1));
	}
	
	public String getInactiveColor(){
		return myDataValues.get(CONFIGURATION_FIELDS.get(2));
	}
	
	public String getEmptyColor(){
		return myDataValues.get(CONFIGURATION_FIELDS.get(3));
	}
	
	public int getCellSize(){
		return Integer.parseInt(myDataValues.get(CONFIGURATION_FIELDS.get(4)));
	}
	
	public int getNumOfNeighbors(){
		return Integer.parseInt(myDataValues.get(CONFIGURATION_FIELDS.get(5)));
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
