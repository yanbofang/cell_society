package backend;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class to encapsulate the required grid information. This class will
 * be passed back to front end as the display
 * 
 * @author chenxingyu
 *
 */
public class GridInfo {
	private ArrayList<Integer> type;
	private ArrayList<Integer> amount;

	public GridInfo(List<Integer> type, List<Integer> amount) {
		super();
		this.type = (ArrayList<Integer>) type;
		this.amount = (ArrayList<Integer>) amount;
	}

	public List<Integer> getType() {
		return type;
	}

	public List<Integer> getAmount() {
		return amount;
	}
}
