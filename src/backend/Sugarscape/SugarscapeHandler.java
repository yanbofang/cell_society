package backend.Sugarscape;

import java.util.List;

import backend.Cell;
import backend.Container;
import backend.EmptyCell;
import backend.Handler;

public class SugarscapeHandler extends Handler {

	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		if (curContainer.contains("EmptyCell")) {
			curContainer.setNext(new EmptyCell());
		}
		if (curContainer.contains("Patch")) {
			for (int i=0;i<curContainer.numCellContain();i++) {
				if (!curContainer.getIthCell(i).is("Patch")) continue;
				Patch curPatch = (Patch) curContainer.getIthCell(i);
				curPatch.growBack();
				curContainer.setNext(curPatch);
			}
		}
		if (curContainer.contains("Agent")) {
			for (int i=0;i<curContainer.numCellContain();i++) {
				if (!curContainer.getIthCell(i).is("Agent")) continue;
				Agent curAgent = (Agent) curContainer.getIthCell(i);
				int max=0;
				Container maxContainer = curContainer;
				Patch maxPatch=null;
				List<Container> a = curContainer.getNeighborFromCloserToFurther();
				for (Container tempContainer : a) {
					//System.out.println(((Patch) tempContainer.getMyCell()).getSugarAmount());
					if (tempContainer.contains("Patch")){
						for (int k=0;k<tempContainer.numCellContain();k++){
							if (tempContainer.getIthCell(k).is("Patch") && ((Patch) tempContainer.getIthCell(k)).getSugarAmount() > max) {
								max = ((Patch) tempContainer.getIthCell(k)).getSugarAmount();
								maxContainer=tempContainer;
								maxPatch=((Patch) tempContainer.getIthCell(k));
							}
						}
					}
				}
				//System.out.println(maxContainer.getPosX()+" "+maxContainer.getPosY());
				curAgent.addSugar(max);
				if (maxPatch!=null) maxPatch.beingEaten();
				curAgent.subtractMetabolism();
				System.out.println(curAgent.getSugarAmount());
				if (curAgent.getSugarAmount()<0) {
					curContainer.getNext().addMyCell(new EmptyCell());
					return;
				}
				maxContainer.getNext().addMyCell(curAgent);
			}
		}
	}

}
