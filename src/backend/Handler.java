package backend;

import java.util.ArrayList;

public abstract class Handler {
	public void startNewRoundSimulation(Grid thisRoundGrid, Grid nextRoundGrid, int numPriority) {
		for (int priority=0;priority<numPriority;priority++) {
			for (int i=0;i<thisRoundGrid.getSize();i++) {
				Container curContainer = thisRoundGrid.getContainer(i);
				if (!curContainer.getNext().isLocked() && curContainer.getMyCell().getPriority()==priority) {
					ArrayList<Cell> newCell = createNewCell(curContainer.getMyCell().ruleCheck());
					for (int k=0;k<newCell.size();k++) {
						curContainer.getNext().setCell(newCell.get(k));
						curContainer.getNext().setLocked(true);
					}
				}
			}
		}
	}
	
	public abstract Cell createNewCell(ArrayList<String> cellFuture); 
}
