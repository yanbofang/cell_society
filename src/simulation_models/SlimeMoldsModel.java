package simulation_models;

import xml.XMLSimulation;

/**
 * SlimeMoldsModel
 * 
 * @author Yanbo Fang
 *
 */
public class SlimeMoldsModel extends SimulationModel {

	private int concentrationGate;
	private int concentrationAmount;
	private int numberOfStates = 2;

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

	@Override
	public int numberOfStates() {
		return 2;
	}

}
