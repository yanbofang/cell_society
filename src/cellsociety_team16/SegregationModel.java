package cellsociety_team16;

import xml.XMLSimulation;

/**
 * Simulation Model for the Segregation Simulation
 * 
 * @author Yanbo Fang
 *
 */
public class SegregationModel extends SimulationModel {

	private double satisfactionRate;
	private int numberOfStates = 2;

	/**
	 * Constructor for SegregationModel
	 * 
	 * @param simulation
	 */
	public SegregationModel(XMLSimulation simulation) {
		super(simulation);
		satisfactionRate = simulation.getSatisfactionRate();
	}

	/**
	 * Get the satisfaction rate
	 * 
	 * @return
	 */
	public double getSatisfactionRate() {
		return this.satisfactionRate;
	}

	/**
	 * Set the satisfaction rate
	 * 
	 * @param satisfactionRate
	 */
	public void setSatisfactionRate(double satisfactionRate) {
		this.satisfactionRate = satisfactionRate;
	}

	@Override
	/**
	 * Return the number of states for this simulation
	 */
	public int numberOfStates() {
		return this.numberOfStates;
	}

}
