package cellsociety_team16;

import xml.XMLSimulation;

/**
 * Simulation Model for the Spreading Fire Simulation
 * 
 * @author Yanbo Fang
 *
 */
public class SpreadingFireModel extends SimulationModel {

	private double fireProbability;
	private double treeProbability;

	/**
	 * Constructor for SpreadingFireModel
	 * 
	 * @param simulation
	 */
	public SpreadingFireModel(XMLSimulation simulation) {
		super(simulation);
		fireProbability = simulation.getFireProbability();
		treeProbability = simulation.getTreeProbability();
	}

	/**
	 * Get the probability of the fire
	 * 
	 * @return
	 */
	public double getFireProbability() {
		return this.fireProbability;
	}

	/**
	 * Get the probability of tree growing
	 * 
	 * @return
	 */
	public double getTreeProbability() {
		return this.treeProbability;
	}

	/**
	 * Set the probability of starting fire
	 * 
	 * @param fireProbability
	 */
	public void setFireProbability(double fireProbability) {
		this.fireProbability = fireProbability;
	}

	/**
	 * Set the probability of growing trees
	 * 
	 * @param treeProbability
	 */
	public void setTreeProbability(double treeProbability) {
		this.treeProbability = treeProbability;
	}

}
