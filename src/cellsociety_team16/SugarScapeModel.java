package cellsociety_team16;

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
	 * Get the int value of surgar grow-back rate
	 * 
	 * @return
	 */
	public int getSugarGrowBackRate() {
		return this.sugarGrowBackRate;
	}

	/**
	 * Return a list of the amounts of items at corresponding indices.
	 * 
	 * @return
	 */
	public List<Integer> getAmounts() {
		return this.myAmounts;
	}

	public void setAmounts(List<Integer> amounts){
		this.myAmounts = amounts;
	}
	
	@Override
	public int numberOfStates() {
		// TODO Auto-generated method stub
		return this.numberOfStates;
	}

}
