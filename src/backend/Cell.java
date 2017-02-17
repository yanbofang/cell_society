package backend;

import java.util.ArrayList;
/**
 * This class is an abstract class which contains the basic function of a cell.
 * Including: Finding the container it's currently in, understand its own identity, finding
 * the priority of its identity.
 * 
 * @author chenxingyu
 *
 */
public abstract class Cell {
	private String identity;//Identity is the identity of the cell.
	private Container myContainer;//myContainer is the container associated with each cell
	private int priority = 0;//Priority is the order we process each type of cell

	public int getPriority() {
		return priority;
	}
	public Container getMyContainer() {
		return myContainer;
	}
	public void setMyContainer(Container myContainer) {
		this.myContainer = myContainer;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public boolean is(String a) {
		return this.getIdentity().compareTo(a)==0;
	}
}
