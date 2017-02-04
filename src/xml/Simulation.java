package xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Simple immutable value object representing Simulation data.
 * @author Yanbo Fang
 * @author Robert C. Duvall
 */
public class Simulation {
	// name in data file that will indicate it represents data for this type of
	// object
	public static final String DATA_TYPE = "Simulation";
	// field names expected to appear in data file holding values for this
	// object
	// simple way to create an immutable list
	public static final List<String> DATA_FIELDS = Arrays
			.asList(new String[] { "name", "author", "rows", "cols", "activeX", "activeY" });

	// specific data values for this instance
	private Map<String, String> myDataValues;

	public Simulation(Map<String, String> dataValues) {
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
	
	public ArrayList<Integer> getActiveX(){
		String ints = myDataValues.get(DATA_FIELDS.get(4));
		ArrayList<Integer> xPos = new ArrayList<Integer>();
		for(int i = 0; i < ints.length(); i++){
			xPos.add(Integer.parseInt(ints.substring(i, i+1)));
		}
		return xPos;
	}

	public ArrayList<Integer> getActiveY(){
		String ints = myDataValues.get(DATA_FIELDS.get(5));
		ArrayList<Integer> yPos = new ArrayList<Integer>();
		for(int i = 0; i < ints.length(); i++){
			yPos.add(Integer.parseInt(ints.substring(i, i+1)));
		}
		return yPos;
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
