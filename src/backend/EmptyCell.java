package backend;

import java.util.ArrayList;

public class EmptyCell extends Cell{
	private Container myContainer;

	@Override
	public String ruleCheck() {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor = this.myContainer.getMyNeighbors();
		int cnt = 0;
		for (int i=0;i<myNeighbor.size();i++) {
			Container curNeighbor = myNeighbor.get(i);
			if (curNeighbor.getMyCell() instanceof Life) cnt++;
		}

		if (cnt==3) {
			return "Live";
		}
		return "Dead";
	}
	
	public String toString() {
		return "0";
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setContainer(Container a) {
		// TODO Auto-generated method stub
		this.myContainer=a;
	}

}
