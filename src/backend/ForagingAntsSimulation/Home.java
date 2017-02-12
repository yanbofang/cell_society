package backend.ForagingAntsSimulation;

import backend.Cell;

public class Home extends Cell{
	public Home() {
		this.setPriority(0);
		this.setIdentity("Home");
	}

	public String toString() {
		return "H";
	}
}
