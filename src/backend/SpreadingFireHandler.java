package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

public class SpreadingFireHandler extends Handler {
	private double fireProb=0.8;
	private double treeProb=0.2;
	
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.getMyCell().is("Fire")) {
			curContainer.getNext().setCell(new EmptyCell());
			curContainer.getNext().setLocked(true);			
		}
		if (curContainer.getMyCell().is("Tree")) {
			ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
			Predicate<String> function = s -> s.compareTo("Fire") == 0;
			int fireCnt = this.numberLiveNeighbor(myNeighbor, function);
			//System.out.println(curContainer.getPosX()+" "+curContainer.getPosY()+" "+fireCnt);
			boolean fired=false;
			if (fireCnt>0) {
				for (int i = 0; i < myNeighbor.size(); i++) {
					Container curNeighbor=myNeighbor.get(i);
					//System.out.println(curNeighbor.getPosX()+" "+curNeighbor.getPosY()+" "+curNeighbor.getMyCell().getIdentity());
					if (curNeighbor.getMyCell().is("Fire")) {
						//System.out.println((Math.random() * 1.0)>fireProb);
						boolean fireHappened = (Math.random() * 1.0)<fireProb;
						if (fireHappened) {
							curContainer.getNext().setCell(new Fire());
							curContainer.getNext().setLocked(true);
							fired=true;
							break;
						} 
					}
				}
			}
			
			if (!fired) {
				curContainer.getNext().setCell(curContainer.getMyCell());
				curContainer.getNext().setLocked(true);
			}
 		}
		
		if (curContainer.getMyCell().is("EmptyCell")) {
			boolean treeProduced = (Math.random() * 1.0)<treeProb;
			if (treeProduced) {
				curContainer.getNext().setCell(new Tree());
				curContainer.getNext().setLocked(true);
			} else {
				curContainer.getNext().setCell(new EmptyCell());
				curContainer.getNext().setLocked(true);
			}
		}
	}

}
