package backend;

import java.util.ArrayList;
/**
 * This class is an extension of Cell. 
 * This class is used for Game of Life Simulation. Life's priority is 0.
 * @author chenxingyu
 *
 */

public class Life extends Cell {	
	public Life() {
		this.setPriority(0);
		this.setIdentity("Life");
	}
		
	public String toString() {
		return "1";
	}
	
}
