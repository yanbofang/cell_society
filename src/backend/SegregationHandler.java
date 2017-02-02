package backend;

import java.util.ArrayList;

public class SegregationHandler extends Handler {
	private double percent=0.3;
	@Override
	public void solve(Container curContainer) {
		// TODO Auto-generated method stub
		ArrayList<Container> myNeighbor=curContainer.getMyNeighbors();
		if (curContainer.getMyCell() instanceof People) {
			int cnt = this.numberLiveNeighbor(myNeighbor);
			if (((double)cnt)/8>=percent) {
				System.out.println(curContainer.getPosX()+" "+curContainer.getPosY()+" "+cnt);
				curContainer.getNext().setCell(curContainer.getMyCell());
				curContainer.getNext().setLocked(true);
			} else {
				curContainer.getNext().setCell(new EmptyCell());
				curContainer.getNext().setLocked(true);
				
				ArrayList<Container> emptyBlock = this.emptyNeighbor();
				int rand = (int) (Math.random()*emptyBlock.size());
				//System.out.println(emptyBlock.size()+" "+rand);

				emptyBlock.get(rand).getNext().setCell(curContainer.getMyCell());
				emptyBlock.get(rand).getNext().setLocked(true);
			}
		}

		if (curContainer.getMyCell() instanceof EmptyCell) {
			curContainer.getNext().setCell(new EmptyCell());
			curContainer.getNext().setLocked(true);
		}
	}

	@Override
	public boolean check(Cell cell) {
		// TODO Auto-generated method stub
		return (cell instanceof People);
	}

}
