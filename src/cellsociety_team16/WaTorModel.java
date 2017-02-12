package cellsociety_team16;

import xml.XMLSimulation;

/**
 * Simulation Model for the WaTor Simulation
 * 
 * @author Yanbo Fang
 *
 */
public class WaTorModel extends SimulationModel {

	private int fishBreed;
	private int sharkBreed;
	private int numberOfStates = 3;

	/**
	 * Constructor for WaTorModel
	 * 
	 * @param simulation
	 */
	public WaTorModel(XMLSimulation simulation) {
		super(simulation);
		fishBreed = simulation.getFishBreed();
		sharkBreed = simulation.getSharkBreed();
	}

	/**
	 * Get the value of how many rounds for a fish to breed
	 * 
	 * @return
	 */
	public int getFishBreed() {
		return this.fishBreed;
	}

	/**
	 * Get the value of how many rounds for a shark to breed
	 * 
	 * @return
	 */
	public int getSharkBreed() {
		return this.sharkBreed;
	}

	/**
	 * Set the value of how many rounds for a fish to breed
	 * 
	 * @param fishBreed
	 */
	public void setFishBreed(int fishBreed) {
		this.fishBreed = fishBreed;
	}

	/**
	 * Set the value of how many rounds for a shark to breed
	 * 
	 * @param sharkBreed
	 */
	public void setSharkBreed(int sharkBreed) {
		this.sharkBreed = sharkBreed;
	}

	@Override
	public int numberOfStates() {
		return this.numberOfStates;
	}

}
