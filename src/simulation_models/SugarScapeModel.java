package simulation_models;

import java.util.List;

import xml.XMLSimulation;

/**
 * SugarScapeModel
 * 
 * @author Yanbo Fang
 *
 */
public class SugarScapeModel extends SimulationModel {

	private int numberOfStates = 3;
	private int sugarMetabolism;
	private int sugarGrowBackRate;
	private List<Integer> myAmounts;

	public SugarScapeModel(XMLSimulation simulation) {
		super(simulation);
		// TODO Auto-generated constructor stub
		sugarMetabolism = simulation.getSugarMetabolism();
		sugarGrowBackRate = simulation.getSugarGrowBackRate();
		myAmounts = simulation.getAmounts();
	}

	/**
	 * Get the int value of sugar metabolism
	 * 
	 * @return
	 */
	public int getSugarMetabolism() {
		return this.sugarMetabolism;
	}

	/**
	 * Get the int value of sugar grow-back rate
	 * 
	 * @return
	 */
	public int getSugarGrowBackRate() {
		return this.sugarGrowBackRate;
	}

	/**
	 * Set the int value of sugar metabolism
	 * @param meta
	 */
	public void setSugarMetabolism(int meta){
		this.sugarMetabolism = meta;
	}
	
	/**
	 * Set the sugar grow-back rate
	 * @param rate
	 */
	public void setSugarGrowBackRate(int rate){
		this.sugarGrowBackRate = rate;
	}

	@Override
	/**
	 * Return the number of states for this simulation
	 */
	public int numberOfStates() {
		return this.numberOfStates;
	}

}
