package backend.GameOfLifeSimulation;

import java.util.ArrayList;
import java.util.function.Predicate;

import backend.Container;
import backend.EmptyCell;
import backend.Handler;
/**
 * A specific class for Game of Life Simulation
 * 
 * @author chenxingyu
 *
 */
public class GameOfLifeHandler extends Handler {	
	/**
	 * The required method to determine the future status of the current cell
	 */
	public void solve(Container curContainer) {
		ArrayList<Container> myNeighbor=curContainer.getMyNeighbors();
		Predicate<String> function = s-> s.compareTo("Life")==0;
		int cnt = this.numberLiveNeighbor(myNeighbor, function);
		
		if (cnt==3) {
			curContainer.setNext(new Life());
		} else if (cnt==2 && curContainer.getMyCell().is("Life")) {
			curContainer.setNext(new Life());
		} else {
			curContainer.setNext(new EmptyCell());			
		}
	}
}
