package backend;

import java.util.ArrayList;
import java.util.List;
/**
 * This class is an abstract class which serves as the handler for the simulation.
 * The handler takes in a current state and its associated future grid, then returns
 * a nextRoundGrid which contains the future state of the simulation.
 */
import java.util.function.Predicate;

public abstract class Handler {
	
	
	private Grid thisRoundGrid;
	private Grid nextRoundGrid;
	
	/**
	 * This method takes in the grid for current state, the grid for future state and the 
	 * total number of different cells. By applying specific rules to each cell, the handler
	 * will update the future status for nextRoundGrid in an order determined by each cell's
	 * priority.
	 * @param thisRoundGrid
	 * @param nextRoundGrid
	 * @param numPriority
	 */
	public void startNewRoundSimulation(Grid thisRoundGrid, Grid nextRoundGrid, int numPriority) {
		//System.out.println("I'm Here");
		this.thisRoundGrid=thisRoundGrid;
		this.nextRoundGrid=nextRoundGrid;
		
		for (int priority=0;priority<numPriority;priority++) {
			for (int i=0;i<thisRoundGrid.getSize();i++) {
				Container curContainer = thisRoundGrid.getContainer(i);
				if (!curContainer.getNext().isLocked() && curContainer.getMyCell().getPriority()==priority) {
					this.solve(curContainer);
				}
			}
		}
	}
	
	/**
	 * This method is a helper method. It will return a list of containers which is currently empty.
	 * 
	 * @return a List of Containers, which is all the empty grids currently
	 */
	public List<Container> emptyNeighbor() {
		ArrayList<Container> emptyNeighborList = new ArrayList<>();
		for (int i=0;i<thisRoundGrid.getSize();i++) {
			Container curContainer = thisRoundGrid.getContainer(i);
			if (curContainer.getMyCell().is("EmptyCell") && !curContainer.getNext().isLocked()) {
				emptyNeighborList.add(curContainer);
			}
		}
		return emptyNeighborList;
	}
	
	/**
	 * This method is a helper method. It will return the number of neighbors it wants to find.
	 * It takes in a lambda function which will tell if the neighbor is the kind we are searching
	 * and all the neighbors. Then it will return the number of specific type of neighbors it wants
	 * to find. 
	 * @param myNeighbor
	 * @param predicate
	 * @return the number of specific kind of neighbor you are searching for. 
	 */
	public int numberLiveNeighbor(ArrayList<Container> myNeighbor, Predicate<String> predicate) {
		int cnt=0;
		for (int i=0;i<myNeighbor.size();i++) {
			Container curNeighbor = myNeighbor.get(i);
			if (this.check(predicate, curNeighbor.getMyCell() )) cnt++;
		}
		return cnt;
	}
	
	/**
	 * This is an abstract method that needs to be implemented by each specific simulation.
	 * Based on the curContainer's status and its neighbors' status, this method needs to 
	 * calculate the future status of the current location as well as the future status of any
	 * related location. E.g. A->B (A moves to B) not only A's future status has been set but
	 * also B's future status.
	 * @param curContainer
	 */
	public abstract void solve(Container curContainer);
	
	private boolean check(Predicate<String> predicate, Cell cell) {
		return predicate.test(cell.getIdentity());
	}
}
