package backend;

import java.util.ArrayList;
import java.util.List;
/**
 * This class serves as the container for the cell. 
 * These containers will maintain the spatial relationship between containers in each round.
 * You can find the container nearby you easily using getMyNeighbors.
 * 
 * @author chenxingyu
 *
 */
public class Container {
	
	private Container next=null;
	private ArrayList<Cell> myCell=new ArrayList<Cell>();
	private boolean locked=false;
	private int posX;
	private int posY;
	private Grid myGrid;
	
	private ArrayList<Container> myNeighbors=new ArrayList<Container>();
	//Constructors below
	public Container() {
	}
	
	public Container(Cell a) {
		this.setMyCell(a);
	}
	
	public Container(Grid myGrid) {
		super();
		this.myGrid=myGrid;
	}
	
	public void setNext(Cell a) {
		this.getNext().addMyCell(a);;
		this.getNext().setLocked(true);
	}
	//Getters and Setters below
	public void addNeighbors(Container a) {
		this.myNeighbors.add(a);
	}
			
	public Container getNext() {
		return this.next;
	}

	public void setNext(Container a) {
		this.next = a;
	}

	public ArrayList<Container> getMyNeighbors() {
		return myNeighbors;
	}

	public void setMyNeighbors(ArrayList<Container> myNeighbors) {
		this.myNeighbors = myNeighbors;
	}

	public Cell getMyCell() {
		if (myCell.size()==0 || myCell==null) return null;
		return myCell.get(0);
	}
	
	public Cell getIthCell(int i) {
		return myCell.get(i);
	}
	
	public int numCellContain() {
		return myCell.size();
	}

	public void setMyCell(Cell myCell) {
		this.myCell.add(myCell);
		myCell.setMyContainer(this);
	}
	
	public void addMyCell(Cell myCell) {
		this.myCell.add(myCell);
		myCell.setMyContainer(this);
	}
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	public boolean contains(String s) {
		if (myCell.size()==0) return false;
		for (Cell a:this.myCell) {
			if (a.is(s)) return true;
		}
		return false;
	}
	private int foodPheromone=0;
	private int homePheromone=0;
	public int getPheromone(String a) {
		if (a.compareTo("Food")==0) return foodPheromone;
		if (a.compareTo("Home")==0) return homePheromone;
		return 0;
	}
	public void setPheromone(String a, int Pheromone) {
		if (a.compareTo("Food")==0) this.foodPheromone = Pheromone;
		if (a.compareTo("Home")==0) this.homePheromone = Pheromone;
	}

	public List<Container> getNeighborAtDirection(int i) {
		List<Container> temp=new ArrayList<Container>();
		int[][] directionX=new int[][]{{-1,-1,-1},{0,-1,1},{1,1,1},{0,-1,1}};
		int[][] directionY=new int[][]{{0,-1,1},{-1,-1,-1},{0,-1,1},{1,1,1}};
		Grid myGird=this.myGrid;
		int n=myGrid.getN();
		int m=myGrid.getM();
		for (int j=0;j<3;i++) {
			int xx=myGrid.boundXHandle(this.getPosX()+directionX[i][j]);
			int yy=myGrid.boundYHandle(this.getPosY()+directionY[i][j]);
			if (xx>=n || xx<0 || yy<0 || yy>=m) continue;
			temp.add(myGrid.getContainer(xx*n+yy));
		}
		return temp;
	}

	public List<Container> getNeighborFromCloserToFurther() {
		List<Container> temp=new ArrayList<Container>();
		int[] directionX=new int[]{-1,0,1,0,-2,0,2,0,-2,0,2,0};
		int[] directionY=new int[]{0,1,0,-1,0,2,0,-2,0,2,0,-2};
		int n=myGrid.getN();
		int m=myGrid.getM();
		for (int i=0;i<directionX.length;i++) {
			int xx=myGrid.boundXHandle(this.getPosX()+directionX[i]);
			int yy=myGrid.boundYHandle(this.getPosY()+directionY[i]);
			if (xx>=n || xx<0 || yy<0 || yy>=m) continue;
			temp.add(myGrid.getContainer(xx*n+yy));
		}
		return temp;
	}

	public Grid getMyGrid() {
		return myGrid;
	}

	public void setMyGrid(Grid myGrid) {
		this.myGrid = myGrid;
	}

}
