package backend;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a n*m Grid containing n*m containers. Its main duty is to set
 * up the neighbors near a container as well as connect the container at time t
 * to the same container at time (t+1)
 * 
 * @author chenxingyu
 *
 */
public abstract class Grid {
	private static final String HOME2 = "Home";
	private static final String FOOD2 = "Food";
	private static final String TOROIDAL = "Toroidal";
	private static final String FINITE = "Finite";
	private ArrayList<Container> containerlist = new ArrayList<Container>();
	private int n = 0;
	private int m = 0;
	private Grid next;
	private int size;
	private String boundary = FINITE;

	public static final int[] POSXSQUARE = { -1, 0, 1, 0, -1, 1, -1, 1 };
	public static final int[] POSYSQUARE = { 0, -1, 0, 1, -1, -1, 1, 1 };

	public static final int[] POSXTRIANGLE = { 0, 0, 1, -1, -1, -1, 0, 0, 1, 1, 1, 1 };
	public static final int[] POSXTRIANGLEREVERSE = { 0, 0, -1, 1, 1, 1, 0, 0, -1, -1, -1, -1 };
	public static final int[] POSYTRIANGLE = { -1, 1, 0, -1, 0, 1, -2, 2, -2, -1, 1, 2 };

	public static final int[] POSXHEXAGON = { -1, -2, -1, 1, 2 };
	public static final int[] POSYHEXAGON = { 0, 0, 1, 1, 0 };

	private int neighborDefn = 4;

	/**
	 * Constructor to construct an n*m Grid and set up the neighbors of each
	 * container.
	 * 
	 * @param n
	 * @param m
	 */
	public Grid(int n, int m) {
		this.size = n * m;
		this.n = n;
		this.m = m;
	}

	/**
	 * Public method to fill the Grid with Container and add corresponding
	 * neighbor to each Container
	 */
	public void fillContainer() {
		for (int i = 0; i < size; i++) {
			Container tempContainer = new Container();
			tempContainer.setMyGrid(this);
			this.containerlist.add(tempContainer);
		}
		for (int i = 0; i < size; i++) {
			Container tempContainer = this.containerlist.get(i);
			int x = i / n;
			int y = i % m;
			addNeighborsFor(tempContainer, x, y);
			tempContainer.setPosX(x);
			tempContainer.setPosY(y);
		}
	}

	/**
	 * A method to add neighbors to current Container at (x,y)
	 * 
	 * @param tempContainer
	 * @param x
	 * @param y
	 */
	private void addNeighborsFor(Container tempContainer, int x, int y) {
		ArrayList<Integer> tempNeighbor = (ArrayList<Integer>) this.getNeighbor(x, y);
		for (int neighbor : tempNeighbor) {
			tempContainer.addNeighbors(this.getContainer(neighbor));
		}
	}

	/**
	 * A method to calculate neighbors of the container located at (x,y) based
	 * on the Positional Array provided by each different kind of grid.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Integer> getNeighbor(int x, int y) {
		List<Integer> result = new ArrayList<Integer>();
		int n = this.getN();
		int m = this.getM();
		LocInfo POSX = this.getNeighborArrayX(x, y);
		LocInfo POSY = this.getNeighborArrayY(x, y);
		for (int k = 0; k < this.getNeighborDefn(); k++) {
			int xx, yy;
			xx = boundXHandle(x + POSX.get(k));
			yy = boundYHandle(y + POSY.get(k));
			if (xx >= n || xx < 0 || yy < 0 || yy >= m)
				continue;
			result.add(xx * n + yy);
		}
		return result;
	}

	/**
	 * Handle the boundary check for different kind of boundary conditions.
	 * Currently it supports "FINITE" and "TOROIDAL"
	 * 
	 * @param x
	 * @return
	 */
	public int boundXHandle(int x) {
		if (this.boundaryIs(FINITE)) {
			return x;
		}
		if (this.boundaryIs(TOROIDAL)) {
			if (x >= n)
				return (x - n);
			else
				return x;
		}
		return x;
	}

	/**
	 * Handle the boundary check for different kind of boundary conditions.
	 * Currently it supports "FINITE" and "TOROIDAL"
	 * 
	 * @param y
	 * @return
	 */
	public int boundYHandle(int y) {
		if (this.boundaryIs(FINITE)) {
			return y;
		}
		if (this.boundaryIs(TOROIDAL)) {
			if (y >= m)
				return (y - m);
			else
				return y;
		}
		return y;
	}

	private boolean boundaryIs(String string) {
		return (this.boundary.compareTo(string) == 0);
	}

	/**
	 * Two abstract methods to get specific positional array for X and Y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract LocInfo getNeighborArrayX(int x, int y);

	public abstract LocInfo getNeighborArrayY(int x, int y);

	/**
	 * Connect the Grid at time t to the Grid at time (t+1)
	 * 
	 * @param nextRoundGrid
	 */
	public void connectWith(Grid nextRoundGrid) {
		this.next = nextRoundGrid;
		for (int i = 0; i < size; i++) {
			this.getContainer(i).setNext(this.next.getContainer(i));
			int Food = this.getContainer(i).getPheromone(FOOD2);
			this.next.getContainer(i).setPheromone(FOOD2, Food);
			int Home = this.getContainer(i).getPheromone(HOME2);
			this.next.getContainer(i).setPheromone(HOME2, Home);
		}
	}

	// Getters and Setters
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

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}
}
