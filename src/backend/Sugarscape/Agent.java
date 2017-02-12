package backend.Sugarscape;

import backend.Cell;

public class Agent extends Cell {
	private static final int defaulSugarMetabolism = 2;
	
	private int sugarAmount = 0;
	private int sugarMetabolism=0;

	public Agent() {
		this.sugarAmount=0;
		this.sugarMetabolism=defaulSugarMetabolism;
		this.setIdentity("Patch");
		this.setPriority(1);
	}
	
	
	public Agent(int sugarAmount, int sugarMetabolism) {
		super();
		this.sugarAmount = sugarAmount;
		this.sugarMetabolism = sugarMetabolism;
	}


	private void addSugar(int amount) {
		this.sugarAmount+=amount;
	}
	
	private void subtractMetabolism() {
		this.sugarAmount-=this.sugarMetabolism;
	}
}
