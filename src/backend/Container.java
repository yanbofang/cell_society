package backend;

import java.util.ArrayList;
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
	private ArrayList<Cell> myCell=null;
	private boolean locked=false;
	private int posX;
	private int posY;
	
	private ArrayList<Container> myNeighbors=new ArrayList<Container>();
	//Constructors below
	public Container() {
	}
	
	public Container(Cell a) {
		this.setMyCell(a);
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
		for (Cell a:this.myCell) {
			if (a.is(s)) return true;
		}
		return false;
	}
	private int foodPheromone=0;
	private int homePheromone=0;
	public int getFoodPheromone() {
		return foodPheromone;
	}
	public void setFoodPheromone(int foodPheromone) {
		this.foodPheromone = foodPheromone;
	}
	public int getHomePheromone() {
		return homePheromone;
	}
	public void setHomePheromone(int homePheromone) {
		this.homePheromone = homePheromone;
	}


}
