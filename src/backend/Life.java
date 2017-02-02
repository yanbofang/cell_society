package backend;

import java.util.ArrayList;

public class Life extends Cell {
	private Container myContainer;
	private int priority = 0;
	
	public Life() {
	}
	
	@Override
	public String ruleCheck() {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor = this.myContainer.getMyNeighbors();
		int cnt = 0;
		for (int i=0;i<myNeighbor.size();i++) {
			Container curNeighbor = myNeighbor.get(i);
			if (curNeighbor.getMyCell() instanceof Life) cnt++;
		}
		if (cnt==2 || cnt==3) {
			return "Live";
		} else {
			return "Dead";
		}
	}
	
	public String toString() {
		return "1";
	}

	public Container getMyContainer() {
		return myContainer;
	}

	public void setMyContainer(Container myContainer) {
		this.myContainer = myContainer;
	}
	

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return this.priority;
	}

	@Override
	public void setContainer(Container a) {
		// TODO Auto-generated method stub
		this.myContainer=a;
	}
}
