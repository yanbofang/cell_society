package backend.SegregationSimulation;

import java.util.ArrayList;
import java.util.function.Predicate;

import backend.Container;
import backend.EmptyCell;
import backend.Handler;

/**
 * A specific class for Game of Segregation Simulation
 * @author chenxingyu
 *
 */
public class SegregationHandler extends Handler {
	private double percent=0.3;
	/**
	 * The constructor to construct a handler for the segregation model with [percent] satisfactory rate
	 * 
	 * @param percent
	 */
	public SegregationHandler(double percent) {
		this.percent=percent;
	}
	
	/**
	 * The required method to determine the future status of the current cell
	 */
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor=curContainer.getMyNeighbors();
		if (curContainer.getMyCell().is("People")) {
			Predicate<String> function = s-> s.compareTo("People")==0;
			int cnt = this.numberLiveNeighbor(myNeighbor, function);

			if (((double)cnt)/8>=percent) {
				curContainer.setNext(curContainer.getMyCell());
			} else {
				curContainer.setNext(new EmptyCell());
				
				ArrayList<Container> emptyBlock = (ArrayList<Container>) this.emptyNeighbor();
				int rand = (int) (Math.random()*emptyBlock.size());

				emptyBlock.get(rand).setNext(curContainer.getMyCell());
			}
		}

		if (curContainer.getMyCell().is("EmptyCell") ) {
			curContainer.setNext(new EmptyCell());
		}
	}

}
