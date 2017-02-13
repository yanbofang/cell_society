package backend.ForagingAntsSimulation;

import backend.Cell;
/**
 * This class is an extension of Cell. 
 * This class is used for ForagingAnts Simulation. Food's priority is 1.
 * @author chenxingyu
 *
 */

public class Food extends Cell{
	public Food() {
		this.setPriority(1);
		this.setIdentity("Food");
	}

	public String toString() {
		return "F";
	}
}
