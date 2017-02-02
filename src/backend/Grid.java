package backend;

import java.util.ArrayList;

public class Grid {
	private ArrayList<Container> containerlist=new ArrayList<Container>();
	private int n=0;
	private int m=0;
	private Grid next;
	private int size;
	
	public static final int[] POSX={-1,0,1,0,-1,1,-1,1};
	public static final int[] POSY={0,-1,0,1,-1,-1,1,1};
	
	public Grid(int n, int m) {
		this.size = n*m;
		for (int i=0;i<size;i++) {
			Container tempContainer = new Container();
			this.containerlist.add(tempContainer);
		}
		for (int i=0;i<size;i++) {
			Container tempContainer = this.containerlist.get(i);
			int x=i / n;
			int y=i % m;
			for (int k=0;k<POSX.length;k++) {
				int xx=x+POSX[k];
				int yy=y+POSY[k];
				if (xx>=n || xx<0 || yy<0 || yy>=m) continue;
				int neighbor=xx*n+yy;
				tempContainer.addNeighbors(this.containerlist.get(neighbor));
			}
		}
	}
	
	public void connectWith(Grid nextRoundGrid) {
		this.next=nextRoundGrid;
		for (int i=0;i<size;i++) {
			this.getContainer(i).setNext(this.next.getContainer(i));
		}
	}
	
	public Container getContainer(int num) {
		return this.containerlist.get(num);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
