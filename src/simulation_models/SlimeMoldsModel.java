package simulation_models;

import xml.XMLSimulation;

/**
 * SlimeMoldsModel
 * 
 * @author Yanbo Fang
 *
 */
public class SlimeMoldsModel extends SimulationModel {
	
	private static final int SLIME_MOLDS_NUMBER_OF_STATES = 2;
	private int concentrationGate;
	private int concentrationAmount;

	public SlimeMoldsModel(XMLSimulation simulation) {
		super(simulation);
		concentrationGate = simulation.getConcentrationGate();
		concentrationAmount = simulation.getConcentrationAmount();
	}

	/**
	 * Return the concentration gate
	 * 
	 * @return
	 */
	public int getConcentrationGate() {
		return this.concentrationGate;
	}

	/**
	 * Set the concentration gate
	 * 
	 * @param gate
	 */
	public void setConcentrationGate(int gate) {
		this.concentrationGate = gate;
	}

	/**
	 * Return the concentration amount
	 * 
	 * @return
	 */
	public int getConcetrationAmount() {
		return this.concentrationAmount;
	}

	/**
	 * Set the concentration amount
	 * 
	 * @param amount
	 */
	public void setConcentrationAmount(int amount) {
		this.concentrationAmount = amount;
	}

	/**
	 * Return the number of states for this simulation
	 */
	@Override
	public int numberOfStates() {
		return SLIME_MOLDS_NUMBER_OF_STATES;
	}

}
