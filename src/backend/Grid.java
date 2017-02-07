package backend;

import java.util.ArrayList;
/**
 * This class is a n*m Grid containing n*m containers. Its main duty is to 
 * set up the neighbors near a container as well as connect the container at
 * time t to the same container at time (t+1)
 * 
 * @author chenxingyu
 *
 */
public class Grid {
	private ArrayList<Container> containerlist=new ArrayList<Container>();
	private int n=0;
	private int m=0;
	private Grid next;
	private int size;
	
	public static final int[] POSX={-1,0,1,0,-1,1,-1,1};
	public static final int[] POSY={0,-1,0,1,-1,-1,1,1};
	
	private int neighborDefn=4;
	/**
	 * Constructor to construct an n*m Grid and set up the neighbors of each container.
	 * @param n
	 * @param m
	 */
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
			tempContainer.setPosX(x);
			tempContainer.setPosY(y);
			for (int k=0;k<this.neighborDefn;k++) {
				int xx=x+POSX[k];
				int yy=y+POSY[k];
				if (xx>=n || xx<0 || yy<0 || yy>=m) continue;
				int neighbor=xx*n+yy;
				tempContainer.addNeighbors(this.containerlist.get(neighbor));
			}
		}
	}
	/**
	 * Connect the Grid at time t to the Grid at time (t+1)
	 * @param nextRoundGrid
	 */
	public void connectWith(Grid nextRoundGrid) {
		this.next=nextRoundGrid;
		for (int i=0;i<size;i++) {
			this.getContainer(i).setNext(this.next.getContainer(i));
		}
	}
	
	//Getters and Setters
	public Container getContainer(int num) {
		return this.containerlist.get(num);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Container> getContainerlist() {
		return containerlist;
	}

	public void setContainerlist(ArrayList<Container> containerlist) {
		this.containerlist = containerlist;
	}

	public int getNeighborDefn() {
		return neighborDefn;
	}

	public void setNeighborDefn(int neighborDefn) {
		this.neighborDefn = neighborDefn;
	}
}
