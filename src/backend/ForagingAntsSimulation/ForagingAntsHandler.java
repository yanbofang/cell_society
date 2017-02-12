package backend.ForagingAntsSimulation;

import java.util.ArrayList;

import backend.Container;
import backend.EmptyCell;
import backend.Grid;
import backend.Handler;
import backend.Home;

public class ForagingAntsHandler extends Handler{
	private int max=20;
	private Grid thisRoundGrid;
	private Grid nextRoundGrid;

	@Override
	public void startNewRoundSimulation(Grid thisRoundGrid, Grid nextRoundGrid, int numPriority) {
		this.thisRoundGrid=thisRoundGrid;
		this.nextRoundGrid=nextRoundGrid;
		
		for (int priority=0;priority<numPriority;priority++) {
			for (int i=0;i<thisRoundGrid.getSize();i++) {
				Container curContainer = thisRoundGrid.getContainer(i);
				for (int k=0;k<curContainer.numCellContain();k++) {
					if (curContainer.getMyCell().getPriority()==priority) {
						this.solve(curContainer);
					}
				}
			}
		}
		for (int i=0;i<this.nextRoundGrid.getSize();i++) {
			if (this.nextRoundGrid.getContainer(i).getMyCell()==null) {
				this.nextRoundGrid.getContainer(i).setMyCell(new EmptyCell());
			}
		}
	}
	
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.contains("EmptyCell")) {
			curContainer.setNext(new EmptyCell());
		}
		if (curContainer.contains("Home")) {
			curContainer.addMyCell(new Home());
		}
		if (curContainer.contains("Food")) {
			curContainer.addMyCell(new Food());
		}
		
		for (int i=0;i<curContainer.numCellContain();i++) {
			if (!curContainer.getIthCell(i).is("Ant")) continue;
			Ant curAnt=(Ant) curContainer.getIthCell(i);
			int foodCnt=foodNearby(curContainer);
			double foodRand=Math.random()*((double)foodCnt);
			int homeCnt=homeNearby(curContainer);
			double homeRand=Math.random()*((double)homeCnt);
			ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
			if (curAnt.isHasFood()) {
				if (curContainer.contains("Home")) {
					increaseHomeToMax(curContainer);
					curAnt.setHasFood(false);
					return;
				}
				int food=0;
				for (Container curNeighbor:myNeighbor) {
					if (curNeighbor.numCellContain()<=10) {
						food+=curNeighbor.getFoodPheromone();
						if (food>foodRand) {
							this.increaseFoodToDesired(curContainer);
							curContainer.getNext().addMyCell(curAnt);
							break;
						}
					}
				}
			}else{
				if (curContainer.contains("Food")) {
					curAnt.setHasFood(true);
					increaseHomeToMax(curContainer);
					return;
				}
				int home=0;
				for (Container curNeighbor:myNeighbor) {
					if (curNeighbor.numCellContain()<=10) {
						home+=curNeighbor.getHomePheromone();
						if (home>homeRand) {
							this.increaseHomeToDesired(curContainer);
							curContainer.getNext().addMyCell(curAnt);
							break;
						}
					}
				}
			}
		}
	}
	private void increaseFoodToDesired(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		int max=0;
		for (Container curNeighbor:myNeighbor) {
			if (curNeighbor.getFoodPheromone()>max) max=curNeighbor.getFoodPheromone();
		}
		if (curContainer.getHomePheromone()<max-2) curContainer.setHomePheromone(max-2);
	}

	private void increaseHomeToDesired(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		int max=0;
		for (Container curNeighbor:myNeighbor) {
			if (curNeighbor.getHomePheromone()>max) max=curNeighbor.getHomePheromone();
		}
		if (curContainer.getFoodPheromone()<max-2) curContainer.setHomePheromone(max-2);
	}
	private void increaseFoodToMax(Container curContainer) {
		curContainer.setFoodPheromone(max);
	}
	private void increaseHomeToMax(Container curContainer) {
		curContainer.setHomePheromone(max);
	}
	private int foodNearby(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		int foodPheromone=0;
		for (Container curNeighbor:myNeighbor) {
			if (curNeighbor.numCellContain()<=10) {
				foodPheromone+=curNeighbor.getFoodPheromone();
			}
		}
		return foodPheromone;
	}

	private int homeNearby(Container curContainer) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		int homePheromone=0;
		for (Container curNeighbor:myNeighbor) {
			if (curNeighbor.numCellContain()<=10) {
				homePheromone+=curNeighbor.getHomePheromone();
			}
		}
		return homePheromone;
	}

}
