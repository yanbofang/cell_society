package backend.SlimeSimulation;

import java.util.ArrayList;

import backend.Container;
import backend.EmptyCell;
import backend.Handler;

/**
 * A specific class for SlimeMold Simulation. It will handle all movement and
 * simulation for this simulation.
 * 
 * @author chenxingyu
 *
 */

public class SlimeHandler extends Handler {
	private static final String FOOD = "Food";
	private static final String EMPTY_CELL = "EmptyCell";
	private int PheromoneDiffusionGate = 4;
	private int PheromoneDiffusionAmount = 2;

	/**
	 * The required method to determine the future status of the current grid.
	 * 
	 * @param curContainer-the
	 *            Container we are going to look at
	 */
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.getMyCell().is("Slime")) {
			ArrayList<Container> myNeighbor = (ArrayList<Container>) curContainer.getNeighborFromCloserToFurther();
			int max = -1;
			Container maxContainer = null;
			if (!curContainer.getNext().isLocked()) {
				max = curContainer.getPheromone(FOOD);
				maxContainer = curContainer;
			}
			for (Container curNeighbor : myNeighbor) {
				if (!curNeighbor.getNext().isLocked() && curNeighbor.getPheromone(FOOD) > max) {
					max = curNeighbor.getPheromone(FOOD);
					maxContainer = curNeighbor;
				}
			}

			if (curContainer != maxContainer) {
				curContainer.setNext(new EmptyCell());
				maxContainer.setNext(curContainer.getMyCell());
				System.out.println(maxContainer.getPosX() + " " + maxContainer.getPosY() + " "
						+ maxContainer.getNext().numCellContain());
			} else {
				curContainer.setNext(curContainer.getMyCell());
				System.out.println(maxContainer.getPosX() + " " + maxContainer.getPosY() + " "
						+ maxContainer.getNext().numCellContain());
			}
			if (curContainer.getPheromone(FOOD) > this.PheromoneDiffusionGate) {
				ArrayList<Container> neighborAdd = (ArrayList<Container>) curContainer.getNeighborFromCloserToFurther();
				for (int i = 0; i < neighborAdd.size(); i++) {
					Container curNeighbor = neighborAdd.get(i);
					curNeighbor.getNext().setPheromone(FOOD,
							curNeighbor.getPheromone(FOOD) + this.PheromoneDiffusionAmount);
				}
			}
		}
		if (curContainer.getMyCell().is(EMPTY_CELL)) {
			curContainer.setNext(new EmptyCell());
		}
	}

	public int getPheromoneDiffusionGate() {
		return PheromoneDiffusionGate;
	}

	public void setPheromoneDiffusionGate(int pheromoneDiffusionGate) {
		PheromoneDiffusionGate = pheromoneDiffusionGate;
	}

}
