package backend.ForagingAntsSimulation;

import backend.Cell;
/**
 * This class is an extension of Cell. 
 * This class is used for ForagingAnts Simulation. Ant's priority is 0.
 * @author chenxingyu
 *
 */
public class Ant extends Cell{
	private int headDirection=0;
	private boolean hasFood=false;
	private int direction=0;
	
	public Ant() {
		this.setPriority(0);
		this.setIdentity("Ant");
	}

	public String toString() {
		return "1";
	}

	public boolean isHasFood() {
		return hasFood;
	}

	public void setHasFood() {
		this.hasFood = !hasFood;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
