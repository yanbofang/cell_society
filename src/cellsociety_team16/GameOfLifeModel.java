package cellsociety_team16;

import xml.XMLSimulation;

public class GameOfLifeModel extends SimulationModel {

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
		return 2;
	}

}
