package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameOfLifeHandler extends Handler {
	private static final Map<String, Integer> myMap = new HashMap<String, Integer>();
	static {
		myMap.put("Live",0);
		myMap.put("Dead",1);
	}
	
	@Override
	public Cell createNewCell(String cellFuture) {
		if (myMap.get(cellFuture)==0) {
			return new Life();
		} else {
			return new EmptyCell();
		}
	}
}
