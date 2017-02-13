package backend.SlimeSimulation;

import backend.Cell;

/**
 * This class is an extension of Cell. This class is used for SlimeMold
 * Simulation. Slime's priority is 0.
 * 
 * @author chenxingyu
 *
 */
public class Slime extends Cell {
	public Slime() {
		this.setPriority(0);
		this.setIdentity("Slime");
	}

	public String toString() {
		return "1";
	}
}
