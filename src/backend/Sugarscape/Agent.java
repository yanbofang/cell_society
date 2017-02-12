package backend.Sugarscape;

import backend.Cell;

public class Agent extends Cell {
	private static final int defaulSugarMetabolism = 2;
	
	private int sugarAmount = 0;
	private int sugarMetabolism=0;

	public Agent() {
		this.setSugarAmount(0);
		this.sugarMetabolism=defaulSugarMetabolism;
		this.setIdentity("Patch");
		this.setPriority(1);
	}
	
	
	public Agent(int sugarAmount, int sugarMetabolism) {
		super();
		this.setSugarAmount(sugarAmount);
		this.sugarMetabolism = sugarMetabolism;
	}


	public void addSugar(int amount) {
		this.setSugarAmount(this.getSugarAmount() + amount);
	}
	
	public void subtractMetabolism() {
		this.setSugarAmount(this.getSugarAmount() - this.sugarMetabolism);
	}

	public int getSugarAmount() {
		return sugarAmount;
	}


	public void setSugarAmount(int sugarAmount) {
		this.sugarAmount = sugarAmount;
	}
}
