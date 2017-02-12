package cellsociety_team16;

import xml.XMLSimulation;

/**
 * GameOfLifeModel
 * @author Yanbo Fang
 *
 */
public class GameOfLifeModel extends SimulationModel {

	private int numberOfStates = 2;
	
	/**
	 * Constructor for GameOfLifeModel
	 * 
	 * @param simulation
	 */
	public GameOfLifeModel(XMLSimulation simulation) {
		super(simulation);
	}

	@Override
	public int numberOfStates() {
		return this.numberOfStates;
	}

}
