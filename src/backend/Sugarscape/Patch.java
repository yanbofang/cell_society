package backend.Sugarscape;

import backend.Cell;

public class Patch extends Cell{
	private static final int defaultRate = 4;
	
	
	private int sugarAmount=0;
	private int sugarGrowBackRate=0;
	
	public Patch() {
		this.setSugarAmount(0);
		this.sugarGrowBackRate=defaultRate;
		this.setIdentity("Patch");
		this.setPriority(1);
	}

	public Patch(int sugarAmount, int sugarGrowBackRate) {
		super();
		this.setSugarAmount(sugarAmount);
		this.sugarGrowBackRate = sugarGrowBackRate;
	}
	
	public void growBack() {
		this.setSugarAmount(this.getSugarAmount() + this.sugarGrowBackRate);
	}
	
	public void beingEaten() {
		this.setSugarAmount(0);
	}

	public int getSugarAmount() {
		return sugarAmount;
	}

	public void setSugarAmount(int sugarAmount) {
		this.sugarAmount = sugarAmount;
	}
}
