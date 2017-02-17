package simulation_models;

import xml.XMLSimulation;

/**
 * GameOfLifeModel
 * @author Yanbo Fang
 *
 */
public class GameOfLifeModel extends SimulationModel {

	private static final int GAME_OF_LIFE_NUMBER_OF_STATES = 2;
	
	/**
	 * Constructor for GameOfLifeModel
	 * 
	 * @param simulation
	 */
	public GameOfLifeModel(XMLSimulation simulation) {
		super(simulation);
	}

	@Override
	/**
	 * Return the number of states for this simulation
	 */
	public int numberOfStates() {
		return GAME_OF_LIFE_NUMBER_OF_STATES;
	}

}
