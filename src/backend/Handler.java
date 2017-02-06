package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class Handler {
	
	
	private Grid thisRoundGrid;
	private Grid nextRoundGrid;
	
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
	
	public ArrayList<Container> emptyNeighbor() {
		ArrayList<Container> emptyNeighborList = new ArrayList<>();
		for (int i=0;i<thisRoundGrid.getSize();i++) {
			Container curContainer = thisRoundGrid.getContainer(i);
			if (curContainer.getMyCell().is("EmptyCell") && !curContainer.getNext().isLocked()) {
				emptyNeighborList.add(curContainer);
			}
		}
		return emptyNeighborList;
	}
	
	public int numberLiveNeighbor(ArrayList<Container> myNeighbor, Predicate<String> predicate) {
		int cnt=0;
		for (int i=0;i<myNeighbor.size();i++) {
			Container curNeighbor = myNeighbor.get(i);
			if (this.check(predicate, curNeighbor.getMyCell() )) cnt++;
		}
		return cnt;
	}

	public abstract void solve(Container curContainer);
	
	public boolean check(Predicate<String> predicate, Cell cell) {
		return predicate.test(cell.getIdentity());
	}
}
