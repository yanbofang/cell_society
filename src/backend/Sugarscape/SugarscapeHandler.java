package backend.Sugarscape;

import java.util.List;

import backend.Cell;
import backend.Container;
import backend.EmptyCell;
import backend.Handler;

/**
 * A specific class for Sugarscape Simulation. It will handle all movement and
 * simulation for this simulation.
 * 
 * @author chenxingyu
 *
 */
public class SugarscapeHandler extends Handler {
	private static final String PATCH = "Patch";
	private static final String AGENT = "Agent";

	/**
	 * The required method to determine the future status of the current grid.
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
		if (curContainer.contains(PATCH)) {
			for (int i = 0; i < curContainer.numCellContain(); i++) {
				if (!curContainer.getIthCell(i).is(PATCH))
					continue;
				Patch curPatch = (Patch) curContainer.getIthCell(i);
				curPatch.growBack();
				curContainer.setNext(curPatch);
			}
		}
		if (curContainer.contains(AGENT)) {
			for (int i = 0; i < curContainer.numCellContain(); i++) {
				if (!curContainer.getIthCell(i).is(AGENT))
					continue;
				Agent curAgent = (Agent) curContainer.getIthCell(i);
				int max = 0;
				Container maxContainer = curContainer;
				Patch maxPatch = null;
				List<Container> a = curContainer.getNeighborFromCloserToFurther();
				for (Container tempContainer : a) {
					// System.out.println(((Patch)
					// tempContainer.getMyCell()).getSugarAmount());
					if (tempContainer.contains(PATCH)) {
						for (int k = 0; k < tempContainer.numCellContain(); k++) {
							if (tempContainer.getIthCell(k).is(PATCH)
									&& ((Patch) tempContainer.getIthCell(k)).getSugarAmount() > max) {
								max = ((Patch) tempContainer.getIthCell(k)).getSugarAmount();
								maxContainer = tempContainer;
								maxPatch = ((Patch) tempContainer.getIthCell(k));
							}
						}
					}
				}
				curAgent.addSugar(max);
				if (maxPatch != null)
					maxPatch.beingEaten();
				curAgent.subtractMetabolism();
//				System.out.println(curAgent.getSugarAmount());
				if (curAgent.getSugarAmount() < 0) {
					curContainer.getNext().addMyCell(new EmptyCell());
					return;
				}
				maxContainer.getNext().addMyCell(curAgent);
			}
		}
	}

}
