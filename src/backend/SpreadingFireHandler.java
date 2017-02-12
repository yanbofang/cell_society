package backend;

import java.util.ArrayList;
import java.util.function.Predicate;
/**
 * A specific class for Fire Spreading Simulation
 * 
 * @author chenxingyu
 *
 */
public class SpreadingFireHandler extends Handler {
	private static final String EMPTY_CELL = "EmptyCell";
	private static final String TREE = "Tree";
	private static final String FIRE = "Fire";
	private double fireProb=0.8;
	private double treeProb=0.2;
	
	public SpreadingFireHandler(double fireProb, double treeProb) {
		this.fireProb=fireProb;
		this.treeProb=treeProb;
	}
	
	/**
	 * The required method to determine the future status of the current grid
	 */
	
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.getMyCell().is(FIRE)) {
			solveForFire(curContainer);
		}
		
		if (curContainer.getMyCell().is(TREE)) {
			solveForTree(curContainer);
		}
		
		if (curContainer.getMyCell().is(EMPTY_CELL)) {
			solveForEmptyCell(curContainer);
		}
	}
	
	private void solveForFire(Container curContainer) {
		curContainer.setNext(new EmptyCell());
	}
	
	private void solveForTree(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		Predicate<String> function = s -> s.compareTo(FIRE) == 0;
		int fireCnt = this.numberLiveNeighbor(myNeighbor, function);
		boolean fired=false;
		if (fireCnt>0) {
			for (Container curNeighbor:myNeighbor) {
				if (curNeighbor.getMyCell().is(FIRE)) {
					boolean fireHappened = (Math.random() * 1.0)<fireProb;
					if (fireHappened) {
						curContainer.setNext(new Fire());
						fired=true;
						break;
					} 
				}
			}
		}
		
		if (!fired) {
			curContainer.setNext(curContainer.getMyCell());
		}
	}
	
	private void solveForEmptyCell(Container curContainer) {
		boolean treeProduced = (Math.random() * 1.0)<treeProb;
		if (treeProduced) {
			curContainer.setNext(new Tree());
		} else {
			curContainer.setNext(new EmptyCell());
		}
	}
}
