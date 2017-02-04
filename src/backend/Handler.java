package backend;

import java.util.ArrayList;

public abstract class Handler {
	
	private Grid thisRoundGrid;
	private Grid nextRoundGrid;
	
	public void startNewRoundSimulation(Grid thisRoundGrid, Grid nextRoundGrid, int numPriority) {
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
			if (curContainer.getMyCell() instanceof EmptyCell && !curContainer.getNext().isLocked()) {
				emptyNeighborList.add(curContainer);
			}
		}
		return emptyNeighborList;
	}
	
	public int numberLiveNeighbor(ArrayList<Container> myNeighbor) {
		int cnt=0;
		for (int i=0;i<myNeighbor.size();i++) {
			Container curNeighbor = myNeighbor.get(i);
			if (this.check(curNeighbor.getMyCell())) cnt++;
		}
		return cnt;
	}

	public abstract void solve(Container curContainer);
	public abstract boolean check(Cell cell);
}
