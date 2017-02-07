package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

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
				System.out.println(curContainer.getPosX()+" "+curContainer.getPosY()+" "+cnt);
				curContainer.getNext().setCell(curContainer.getMyCell());
				curContainer.getNext().setLocked(true);
			} else {
				curContainer.getNext().setCell(new EmptyCell());
				curContainer.getNext().setLocked(true);
				
				ArrayList<Container> emptyBlock = (ArrayList<Container>) this.emptyNeighbor();
				int rand = (int) (Math.random()*emptyBlock.size());
				//System.out.println(emptyBlock.size()+" "+rand);

				emptyBlock.get(rand).getNext().setCell(curContainer.getMyCell());
				emptyBlock.get(rand).getNext().setLocked(true);
			}
		}

		if (curContainer.getMyCell().is("EmptyCell") ) {
			curContainer.getNext().setCell(new EmptyCell());
			curContainer.getNext().setLocked(true);
		}
	}

}
