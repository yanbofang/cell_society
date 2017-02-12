package backend.ForagingAntsSimulation;

import backend.Cell;

public class Ant extends Cell{
	private int headDirection=0;
	private boolean hasFood=false;
	public Ant() {
		this.setPriority(1);
		this.setIdentity("Ant");
	}

	public String toString() {
		return "1";
	}

	public boolean isHasFood() {
		return hasFood;
	}

	public void setHasFood(boolean hasFood) {
		this.hasFood = hasFood;
	}
}
