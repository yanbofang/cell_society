package backend.SegregationSimulation;

import backend.Cell;

/**
 * This class is an extension of Cell. 
 * This class is used for Segregation Simulation. People's priority is 0.
 * @author chenxingyu
 *
 */
public class People extends Cell {
	public People() {
		this.setPriority(0);
		this.setIdentity("People");
	}
	
	public String toString() {
		return "1";
	}
}
