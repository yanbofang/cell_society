package simulation_models;

import xml.XMLSimulation;

/**
 * // This entire file is part of my masterpiece.

 * Factory for SimulationModel
 * 
 * @author Yanbo Fang
 *
 */
public class SimulationModelFactory {

	private static final String MODEL_PACKAGE = "simulation_models.";
	private static final String MODEL = "Model";
	private static final int SUFFIX_LENGTH = 3;

	/**
	 * Create and return a SimulationModel from the XMLSimulation
	 * 
	 * @param xml
	 * @return
	 */
	public SimulationModel createSimulationModel(XMLSimulation xml) {
		try {
			Class<?> model = Class.forName(MODEL_PACKAGE + xml.getName() + MODEL);
			return (SimulationModel) model.getDeclaredConstructor(XMLSimulation.class).newInstance(xml);
		} catch (Exception e) {
			Class<?> model;
			try {
				model = Class.forName(
						MODEL_PACKAGE + xml.getName().substring(0, xml.getName().length() - SUFFIX_LENGTH) + MODEL);
			} catch (Exception ee) {
				return null;
			}
		}
		return null;
	}

}
