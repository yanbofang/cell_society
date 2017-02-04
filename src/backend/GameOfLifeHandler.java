package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

public class GameOfLifeHandler extends Handler {	

	public void solve(Container curContainer) {
		ArrayList<Container> myNeighbor=curContainer.getMyNeighbors();
		if (curContainer.getMyCell().is("Life")) {
			Predicate<String> function = s-> s.compareTo("Life")==0;
			int cnt = this.numberLiveNeighbor(myNeighbor, function);
			if (cnt==2 || cnt==3) {
				curContainer.getNext().setCell(new Life());
			} else {
				curContainer.getNext().setCell(new EmptyCell());
			}
		}

		if (curContainer.getMyCell().is("EmptyCell")) {
			Predicate<String> function = s-> s.compareTo("Life")==0;
			int cnt = this.numberLiveNeighbor(myNeighbor, function);
			if (cnt==3) {
				curContainer.getNext().setCell(new Life());
			} else { 
				curContainer.getNext().setCell(new EmptyCell());
			}
		}
	}
}
