package backend.ForagingAntsSimulation;

import backend.Cell;
/**
 * This class is an extension of Cell. 
 * This class is used for ForagingAnts Simulation. Home's priority is 1.
 * @author chenxingyu
 *
 */

public class Home extends Cell{
	public Home() {
		this.setPriority(1);
		this.setIdentity("Home");
	}

	public String toString() {
		return "H";
	}
}
