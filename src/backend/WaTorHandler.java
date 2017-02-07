package backend;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * A specific class for WaTor Simulation
 * 
 * @author chenxingyu
 *
 */
public class WaTorHandler extends Handler {
	private int fishCnt=0;	
	private int emptyCnt=0;
	private int sharkCnt=0;
	private int breedTime=4;
	
	public WaTorHandler(int breedTime) {
		this.breedTime=breedTime;
	}
	
	/**
	 * The required method to determine the future status of the current grid.
	 * Let three helper method solveForFish, solveForShark, solveForEmptyCell to do the processing 
	 * 
	 * @param curContainer-the Container we are going to look at
	 */
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		Predicate<String> function;
		function = s -> s.compareTo("Fish") == 0;
		fishCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo("EmptyCell") == 0;
		emptyCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo("Shark") == 0;
		sharkCnt = this.numberLiveNeighbor(myNeighbor, function);

		if (curContainer.getMyCell().is("Shark")) {
			solveForShark(curContainer);
		}

		if (curContainer.getMyCell().is("Fish")) {
			solveForFish(curContainer);
		}

		if (curContainer.getMyCell().is("EmptyCell")) {			
			solveForEmptyCell(curContainer);
		}
	}

	
	private void solveForShark(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		curContainer.getMyCell().increaseLifeSpan();
		if (fishCnt > 0) {
			int fishNum = randNum(fishCnt);
			this.eatNearbyFish(curContainer, myNeighbor, fishNum);
		} else if (emptyCnt > 0) {
			int emptyNum = randNum(emptyCnt);
			this.moveToNearbyPlace(curContainer, myNeighbor, emptyNum);
		} else {
			curContainer.getNext().setCell(curContainer.getMyCell());
			curContainer.getNext().setLocked(true);
		}
	}
	
	private void solveForFish(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		curContainer.getMyCell().increaseLifeSpan();
		if (emptyCnt > 0) {
			int emptyNum = (int) (Math.random() * ((double) emptyCnt) );
			this.moveToNearbyPlace(curContainer, myNeighbor, emptyNum);
		} else {
			curContainer.getNext().setCell(curContainer.getMyCell());
			curContainer.getNext().setLocked(true);
		}
	}
	
	private void solveForEmptyCell(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getNext().getMyNeighbors();
		int breedCnt = 0;
		for (int i = 0; i < myNeighbor.size(); i++) {
			Container curNeighbor=myNeighbor.get(i);
			if (readyForBreed(curNeighbor)) {
					breedCnt++;
			}
		}

		int breedNum = randNum(breedCnt);
		
		if (breedCnt > 0) {
			Predicate<Container> function = s -> readyForBreed(s);
			
			Container breedContainer=this.searchForNth(breedNum, myNeighbor, function);
			breedContainer.getMyCell().setLifeSpan(0);
			if (breedContainer.getMyCell().is("Fish")) {
				curContainer.getNext().setCell(new Fish());
			} else {
				curContainer.getNext().setCell(new Shark());
			}
			curContainer.getNext().setLocked(true);
		} else {
			curContainer.getNext().setCell(new EmptyCell());
			curContainer.getNext().setLocked(true);
		}
	}

	
	private Container searchForNth(int nth, ArrayList<Container> myNeighbor, Predicate<Container> function) {
		int temp=0;
		for (int i = 0; i < myNeighbor.size(); i++) {
			Container curNeighbor=myNeighbor.get(i);
			if (function.test(curNeighbor)) {
				if (temp == nth) {
					return curNeighbor;
				}
				temp++;
			}
		}
		return null;
	}
	
	private int randNum(int num) {
		return (int) (Math.random() * ((double) num));
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

	private boolean readyForBreed(Container a) {
		return (a.isLocked() && (a.getMyCell().is("Fish") || a.getMyCell().is("Shark")) && a.getMyCell().getLifeSpan() > breedTime);
	}

}


