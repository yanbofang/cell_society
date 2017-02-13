package backend.ForagingAntsSimulation;

import java.util.ArrayList;

import backend.Container;
import backend.EmptyCell;
import backend.Grid;
import backend.Handler;

/**
 * A specific class for ForagingAnts Simulation. It will handle all movement and
 * simulation for this simulation.
 * 
 * @author chenxingyu
 *
 */

public class ForagingAntsHandler extends Handler {
	private int max = 20;
	private Grid thisRoundGrid;
	private Grid nextRoundGrid;

	/**
	 * Override a method of super class because of the specific need of this
	 * simulation.
	 */
	@Override
	public void startNewRoundSimulation(Grid thisRoundGrid, Grid nextRoundGrid, int numPriority) {
		this.thisRoundGrid = thisRoundGrid;
		this.nextRoundGrid = nextRoundGrid;

		for (int priority = 0; priority < numPriority; priority++) {
			for (int i = 0; i < thisRoundGrid.getSize(); i++) {
				Container curContainer = thisRoundGrid.getContainer(i);
				for (int k = 0; k < curContainer.numCellContain(); k++) {
					if (curContainer.getMyCell().getPriority() == priority) {
						this.solve(curContainer);
					}
				}
			}
		}
		for (int i = 0; i < this.nextRoundGrid.getSize(); i++) {
			if (this.nextRoundGrid.getContainer(i).getMyCell() == null) {
				this.nextRoundGrid.getContainer(i).setMyCell(new EmptyCell());
			}
		}
	}

	/**
	 * The required method to determine the future status of the current grid.
	 * Have one helper method solveForAnt to help the real processing.
	 * 
	 * @param curContainer-the
	 *            Container we are going to look at
	 */
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

		for (int i = 0; i < curContainer.numCellContain(); i++) {
			if (!curContainer.getIthCell(i).is("Ant"))
				continue;
			Ant curAnt = (Ant) curContainer.getIthCell(i);
			if (curAnt.isHasFood()) {
				solveForAnt(curContainer, curAnt, "Home", "Food");
			} else {
				solveForAnt(curContainer, curAnt, "Food", "Home");
			}
		}
	}

	private void solveForAnt(Container curContainer, Ant curAnt, String whatToFind, String whatToLeave) {
		if (curContainer.contains(whatToLeave)) {
			ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
			int max = -1;
			Container maxContainer = null;
			for (Container curNeighbor : myNeighbor) {
				if (curNeighbor.getPheromone(whatToFind) > max) {
					max = curNeighbor.getPheromone(whatToFind);
					maxContainer = curNeighbor;
				}
			}
			this.increaseToMax(curContainer, whatToLeave);
			maxContainer.getNext().addMyCell(curAnt);
			return;
		}

		ArrayList<Container> myNeighborInVision = (ArrayList<Container>) curContainer
				.getNeighborAtDirection(curAnt.getDirection());
		ArrayList<Container> myNeighborNotInVision = new ArrayList<Container>();
		for (int k = 0; k < 4; k++) {
			if (k != curAnt.getDirection())
				myNeighborNotInVision.addAll(curContainer.getNeighborAtDirection(k));
		}
		int pheromoneCnt = NearbyDirection(curContainer, curAnt.getDirection(), whatToFind);
		double pheromoneRand = Math.random() * ((double) pheromoneCnt) * 1.5;
		int pheromone = 0;
		for (Container curNeighbor : myNeighborInVision) {
			if (curNeighbor.numCellContain() <= 10) {
				pheromone += curNeighbor.getPheromone(whatToFind);
				if (pheromone > pheromoneRand) {
					this.increaseToDesired(curContainer, whatToLeave);
					curNeighbor.getNext().addMyCell(curAnt);
					if (curNeighbor.contains(whatToFind)) {
						curAnt.setHasFood();
						increaseToMax(curNeighbor, whatToFind);
					}
					return;
				}
			}
		}
		pheromoneCnt = -pheromoneCnt;
		for (int k = 0; k < 4; k++) {
			pheromoneCnt = NearbyDirection(curContainer, curAnt.getDirection(), whatToFind);
		}
		pheromoneRand = Math.random() * ((double) pheromoneCnt);
		pheromone = 0;
		for (Container curNeighbor : myNeighborInVision) {
			if (curNeighbor.numCellContain() <= 10) {
				pheromone += curNeighbor.getPheromone(whatToFind);
				if (pheromone > pheromoneRand) {
					this.increaseToDesired(curContainer, whatToLeave);
					curNeighbor.getNext().addMyCell(curAnt);
					if (curNeighbor.contains(whatToFind)) {
						curAnt.setHasFood();
						increaseToMax(curNeighbor, whatToFind);
					}
					return;
				}
			}
		}

	}

	private void increaseToDesired(Container curContainer, String a) {
		ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
		int max = 0;
		for (Container curNeighbor : myNeighbor) {
			if (curNeighbor.getPheromone(a) > max)
				max = curNeighbor.getPheromone(a);
		}
		if (curContainer.getPheromone(a) < max - 2)
			curContainer.setPheromone(a, max - 2);
	}

	private void increaseToMax(Container curContainer, String a) {
		if (a.compareTo("Food") == 0)
			curContainer.setPheromone(a, max);
		if (a.compareTo("Home") == 0)
			curContainer.setPheromone(a, max);
	}

	private int NearbyDirection(Container curContainer, int i, String a) {
		ArrayList<Container> myNeighbor = (ArrayList<Container>) curContainer.getNeighborAtDirection(i);
		int Pheromone = 0;
		for (Container curNeighbor : myNeighbor) {
			if (curNeighbor.numCellContain() <= 10) {
				Pheromone += curNeighbor.getPheromone(a);
			}
		}
		return Pheromone;
	}

}
