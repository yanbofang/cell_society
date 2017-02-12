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
			for (int i=0;i<4;i++) {	
				List<Container> a = curContainer.getNeighborAtDirection(i);
				for (Container tempContainer: a) {
					if (tempContainer.contains("Patch") && ((Patch) tempContainer.getMyCell()).getSugarAmount()>max) {
						max=((Patch) tempContainer.getMyCell()).getSugarAmount();
					}
				}
			}
		}
	}

}
