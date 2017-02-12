package backend.WaTorSimulation;

import java.util.ArrayList;
import java.util.function.Predicate;

import backend.Container;
import backend.EmptyCell;
import backend.Handler;

/**
 * A specific class for WaTor Simulation
 * 
 * @author chenxingyu
 *
 */
public class WaTorHandler extends Handler {
	private static final String SHARK = "Shark";
	private static final String EMPTY_CELL = "EmptyCell";
	private static final String FISH = "Fish";
	private int fishCnt=0;	
	private int emptyCnt=0;
	private int sharkCnt=0;
	private int breedTime=4;
	
	public WaTorHandler(int breedTime) {
		this.breedTime=breedTime;
	}
	
	/**
	 * The required method to determine the future status of the current grid.
	 * Have three helper method solveForFish, solveForShark, solveForEmptyCell to do the 
	 * real processing. 
	 * 
	 * @param curContainer-the Container we are going to look at
	 */
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		Predicate<String> function;
		function = s -> s.compareTo(FISH) == 0;
		fishCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo(EMPTY_CELL) == 0;
		emptyCnt = this.numberLiveNeighbor(myNeighbor, function);

		function = s -> s.compareTo(SHARK) == 0;
		sharkCnt = this.numberLiveNeighbor(myNeighbor, function);

		if (curContainer.getMyCell().is(SHARK)) {
			solveForShark(curContainer);
		}

		if (curContainer.getMyCell().is(FISH)) {
			solveForFish(curContainer);
		}

		if (curContainer.getMyCell().is(EMPTY_CELL)) {			
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
			curContainer.setNext(curContainer.getMyCell());
		}
	}
	
	private void solveForFish(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		curContainer.getMyCell().increaseLifeSpan();
		if (emptyCnt > 0) {
			int emptyNum = (int) (Math.random() * ((double) emptyCnt) );
			this.moveToNearbyPlace(curContainer, myNeighbor, emptyNum);
		} else {
			curContainer.setNext(curContainer.getMyCell());
		}
	}
	
	private void solveForEmptyCell(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getNext().getMyNeighbors();
		int breedCnt = 0;
		for (Container curNeighbor:myNeighbor) {
			if (readyForBreed(curNeighbor)) {
					breedCnt++;
			}
		}

		int breedNum = randNum(breedCnt);
		
		if (breedCnt > 0) {
			Predicate<Container> function = s -> readyForBreed(s);
			
			Container breedContainer=this.searchForNth(breedNum, myNeighbor, function);
			breedContainer.getMyCell().setLifeSpan(0);
			if (breedContainer.getMyCell().is(FISH)) {
				curContainer.setNext(new Fish());
			} else {
				curContainer.setNext(new Shark());
			}
		} else {
			curContainer.setNext(new EmptyCell());
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
		this.moveToNeighborOfType(curContainer, myNeighbor, FISH, num);
	}

	private void moveToNearbyPlace(Container curContainer, ArrayList<Container> myNeighbor, int num) {
		this.moveToNeighborOfType(curContainer, myNeighbor, EMPTY_CELL, num);
	}

	private Container moveToNeighborOfType(Container curContainer, ArrayList<Container> myNeighbor,
			String neighborIdentity, int num) {

		curContainer.setNext(new EmptyCell());
		int temp = 0;
		for (Container curNeighbor:myNeighbor) {
			if (curNeighbor.getMyCell().is(neighborIdentity) && !curNeighbor.getNext().isLocked()) {
				if (temp == num) {
					//System.out.println(curContainer.getPosX()+" "+curContainer.getPosY()+" "+myNeighbor.get(i).getPosX()+" "+myNeighbor.get(i).getPosY());
					//System.out.println(curContainer.getMyCell());
					curNeighbor.setNext(curContainer.getMyCell());
					return curNeighbor;
				}
				temp++;
			}
		}
		return null;
	}

	private boolean readyForBreed(Container a) {
		return (a.isLocked() && (a.getMyCell().is(FISH) || a.getMyCell().is(SHARK)) && a.getMyCell().getLifeSpan() > breedTime);
	}

}


