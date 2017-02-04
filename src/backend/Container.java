package backend;

import java.util.ArrayList;

public class Container {
	private Container next=null;
	private Cell myCell=null;
	private boolean locked=false;
	private int posX;
	private int posY;
	
	private ArrayList<Container> myNeighbors=new ArrayList<Container>();
	
	public Container() {
	}
	
	public Container(Cell a) {
		this.setMyCell(a);
	}
	
	public void addNeighbors(Container a) {
		this.myNeighbors.add(a);
	}
		
	public void setCell(Cell a) {
		this.setMyCell(a);
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
		return myCell;
	}

	public void setMyCell(Cell myCell) {
		this.myCell = myCell;
		myCell.setContainer(this);
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

}
