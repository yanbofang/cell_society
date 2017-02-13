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
	private int PheromoneDiffusionGate = 4;
	private int PheromoneDiffusionAmount = 2;

	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.contains("Slime")) {
			ArrayList<Container> myNeighbor = curContainer.getMyNeighbors();
			int max = curContainer.getPheromone("Food");
			Container maxContainer = curContainer;
			for (Container curNeighbor : myNeighbor) {
				if (curNeighbor.getPheromone("Food") > max) {
					max = curNeighbor.getPheromone("Food");
					maxContainer = curNeighbor;
				}
			}
			curContainer.setNext(new EmptyCell());
			maxContainer.setNext(curContainer.getMyCell());
			if (curContainer.getPheromone("Food") > this.PheromoneDiffusionGate) {
				ArrayList<Container> neighborAdd = (ArrayList<Container>) curContainer.getNeighborFromCloserToFurther();
				for (int i = 0; i < neighborAdd.size(); i++) {
					Container curNeighbor = neighborAdd.get(i);
					curNeighbor.setPheromone("Food", curNeighbor.getPheromone("Food") + this.PheromoneDiffusionAmount);
				}
			}
		}
	}

	public int getPheromoneDiffusionGate() {
		return PheromoneDiffusionGate;
	}

	public void setPheromoneDiffusionGate(int pheromoneDiffusionGate) {
		PheromoneDiffusionGate = pheromoneDiffusionGate;
	}

}
