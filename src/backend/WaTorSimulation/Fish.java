package backend.WaTorSimulation;

import backend.Cell;

/**
 * This class is an extension of Cell. 
 * This class is used for WaTor Simulation. Fish's priority is 1.
 * @author chenxingyu
 *
 */

public class Fish extends Cell {
	
	public Fish() {
		this.setPriority(1);
		this.setIdentity("Fish");
	}

	public String toString() {
		return "1";
	}

	public Fish breed() {
		return new Fish();
	}
}
