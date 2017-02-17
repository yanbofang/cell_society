package backend.Sugarscape;

import backend.Cell;

/**
 * This class is an extension of Cell. This class is used for SugerScape
 * Simulation. Patch's priority is 1.
 * 
 * @author chenxingyu
 *
 */
public class Patch extends Cell {
	private static final int defaultRate = 4;

	private int sugarAmount = 0;
	private int sugarGrowBackRate = 0;

	public Patch() {
		this.setSugarAmount(0);
		this.sugarGrowBackRate = defaultRate;
		this.setIdentity("Patch");
		this.setPriority(1);
	}

	public Patch(int sugarAmount, int sugarGrowBackRate) {
		this();
		this.setSugarAmount(sugarAmount);
		this.sugarGrowBackRate = sugarGrowBackRate;
	}

	/**
	 * Method called when it can grow back an amount of sugar
	 */
	public void growBack() {
		this.setSugarAmount(this.getSugarAmount() + this.sugarGrowBackRate);
	}

	/**
	 * Method called when it is eaten by agent
	 */
	public void beingEaten() {
		this.setSugarAmount(0);
	}

	public int getSugarAmount() {
		return sugarAmount;
	}

	public void setSugarAmount(int sugarAmount) {
		this.sugarAmount = sugarAmount;
	}

	public String toString() {
		return "1";
	}
}
