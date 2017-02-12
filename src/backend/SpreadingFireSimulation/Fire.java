package backend.SpreadingFireSimulation;

import backend.Cell;

/**
 * This class is an extension of Cell. 
 * This class is used for Fire Spreading Simulation. Fire's priority is 0.
 * @author chenxingyu
 *
 */
public class Fire extends Cell {
	public Fire() {
		this.setPriority(0);
		this.setIdentity("Fire");
	}

	public String toString() {
		return "2";
	}
}
