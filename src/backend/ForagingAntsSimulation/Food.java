package backend.ForagingAntsSimulation;

import backend.Cell;

public class Food extends Cell{
	public Food() {
		this.setPriority(0);
		this.setIdentity("Food");
	}

	public String toString() {
		return "F";
	}
}
