package backend.Sugarscape;

import java.util.List;

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
			Patch curPatch = (Patch) curContainer.getMyCell();
			curPatch.growBack();
		}
		if (curContainer.contains("Agent")) {
			Agent curAgent = (Agent) curContainer.getMyCell();
			int max=0;
			Container maxContainer = curContainer;
			List<Container> a = curContainer.getNeighborFromCloserToFurther();
			for (Container tempContainer : a) {
				if (tempContainer.contains("Patch") && ((Patch) tempContainer.getMyCell()).getSugarAmount() > max) {
					max = ((Patch) tempContainer.getMyCell()).getSugarAmount();
					maxContainer=tempContainer;
				}
			}
			curAgent.addSugar(((Patch) maxContainer.getMyCell()).getSugarAmount());
			curAgent.subtractMetabolism();
			if (curAgent.getSugarAmount()<0) {
				curContainer.setNext(new EmptyCell());
				return;
			}
			maxContainer.setNext(curContainer.getMyCell());
			curContainer.setNext(new EmptyCell());
		}
	}

}
