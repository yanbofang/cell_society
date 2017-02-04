package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

public class WaTorHandler extends Handler {

	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		Predicate<String> function;
		function = s -> s.compareTo("Fish") == 0;
		int fishCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo("EmptyCell") == 0;
		int emptyCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo("Shark") == 0;
		int sharkCnt = this.numberLiveNeighbor(myNeighbor, function);

		if (curContainer.getMyCell().is("Shark")) {
			curContainer.getMyCell().increaseLifeSpan();
			if (fishCnt > 0) {
				int fishNum = (int) (Math.random() * ((double) fishCnt));
				this.eatNearbyFish(curContainer, myNeighbor, fishNum);
			} else if (emptyCnt > 0) {
				int emptyNum = (int) (Math.random() * ((double) emptyCnt));
				this.moveToNearbyPlace(curContainer, myNeighbor, emptyNum);
			} else {
				curContainer.getNext().setCell(curContainer.getMyCell());
				curContainer.getNext().setLocked(true);
			}
		}

		if (curContainer.getMyCell().is("Fish")) {
			curContainer.getMyCell().increaseLifeSpan();
			if (emptyCnt > 0) {
				int emptyNum = (int) (Math.random() * ((double) emptyCnt) );
				this.moveToNearbyPlace(curContainer, myNeighbor, emptyNum);
			} else {
				curContainer.getNext().setCell(curContainer.getMyCell());
				curContainer.getNext().setLocked(true);
			}
		}

		if (curContainer.getMyCell().is("EmptyCell")) {
			//System.out.println("EmptyCell:"+curContainer.getPosX()+" "+curContainer.getPosY());
			
			myNeighbor = curContainer.getNext().getMyNeighbors();
			int breedCnt = 0;
			for (int i = 0; i < myNeighbor.size(); i++) {
				if (myNeighbor.get(i).isLocked() && (myNeighbor.get(i).getMyCell().is("Fish") || myNeighbor.get(i).getMyCell().is("Shark"))) {
					if (myNeighbor.get(i).getMyCell().getLifeSpan() > 4)
						breedCnt++;
				}
			}

			int breedNum = (int) (Math.random() * ((double) breedCnt));
			
			//System.out.println(breedCnt);
			if (breedCnt > 0) {
				int temp = 0;
				for (int i = 0; i < myNeighbor.size(); i++) {
					if (myNeighbor.get(i).isLocked() && (myNeighbor.get(i).getMyCell().is("Fish") || myNeighbor.get(i).getMyCell().is("Shark"))) {
						if (temp == breedNum) {
							myNeighbor.get(i).getMyCell().setLifeSpan(0);
							System.out.println("BreedCell:"+curContainer.getPosX()+" "+curContainer.getPosY());
							if (myNeighbor.get(i).getMyCell().is("Fish")) {
								curContainer.getNext().setCell(new Fish());
							} else {
								curContainer.getNext().setCell(new Shark());
							}
							curContainer.getNext().setLocked(true);
						}
						temp++;
					}
				}
			} else {
				curContainer.getNext().setCell(new EmptyCell());
				curContainer.getNext().setLocked(true);
			}
		}
	}

	private void eatNearbyFish(Container curContainer, ArrayList<Container> myNeighbor, int num) {
		this.moveToNeighborOfType(curContainer, myNeighbor, "Fish", num);
	}

	private void moveToNearbyPlace(Container curContainer, ArrayList<Container> myNeighbor, int num) {
		this.moveToNeighborOfType(curContainer, myNeighbor, "EmptyCell", num);
	}

	private Container moveToNeighborOfType(Container curContainer, ArrayList<Container> myNeighbor,
			String neighborIdentity, int num) {

		curContainer.getNext().setCell(new EmptyCell());
		curContainer.getNext().isLocked();
		int temp = 0;
		for (int i = 0; i < myNeighbor.size(); i++) {
			if (myNeighbor.get(i).getMyCell().is(neighborIdentity) && !myNeighbor.get(i).getNext().isLocked()) {
				if (temp == num) {
					//System.out.println(curContainer.getPosX()+" "+curContainer.getPosY()+" "+myNeighbor.get(i).getPosX()+" "+myNeighbor.get(i).getPosY());
					//System.out.println(curContainer.getMyCell());
					myNeighbor.get(i).getNext().setCell(curContainer.getMyCell());
					myNeighbor.get(i).getNext().setLocked(true);
					return myNeighbor.get(i);
				}
				temp++;
			}
		}
		return null;
	}
}
