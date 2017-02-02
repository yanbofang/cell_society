package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameOfLifeHandler extends Handler {	

	public void solve(Container curContainer) {
		ArrayList<Container> myNeighbor=curContainer.getMyNeighbors();
		if (curContainer.getMyCell() instanceof Life) {
			int cnt = this.numberLiveNeighbor(myNeighbor);
			if (cnt==2 || cnt==3) {
				curContainer.getNext().setCell(new Life());
			} else {
				curContainer.getNext().setCell(new EmptyCell());
			}
		}

		if (curContainer.getMyCell() instanceof EmptyCell) {
			int cnt = this.numberLiveNeighbor(myNeighbor);
			if (cnt==3) {
				curContainer.getNext().setCell(new Life());
			} else { 
				curContainer.getNext().setCell(new EmptyCell());
			}
		}
	}

	@Override
	public boolean check(Cell cell) {
		// TODO Auto-generated method stub
		return cell instanceof Life;
	}
	
}
