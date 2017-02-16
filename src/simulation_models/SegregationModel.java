package simulation_models;

import xml.XMLSimulation;

/**
 * Simulation Model for the Segregation Simulation
 * 
 * @author Yanbo Fang
 *
 */
public class SegregationModel extends SimulationModel {

	private static final int SEGREGATION_NUMBER_OF_STATES = 2;
	private double satisfactionRate;

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
		return SEGREGATION_NUMBER_OF_STATES;
	}

}
