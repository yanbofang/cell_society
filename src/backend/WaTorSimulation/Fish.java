package backend.WaTorSimulation;

import backend.Cell;

/**
 * This class is an extension of Cell. 
 * This class is used for WaTor Simulation. Fish's priority is 1.
 * @author chenxingyu
 *
 */

public class Fish extends Cell {
	private int lifeSpan=0;

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
	
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	public void increaseLifeSpan() {
		this.lifeSpan++;
	}
	public int getLifeSpan() {
		return lifeSpan;
	}
}
