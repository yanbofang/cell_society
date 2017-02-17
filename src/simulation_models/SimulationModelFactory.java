package simulation_models;

import xml.XMLSimulation;

/**
 * // This entire file is part of my masterpiece. This class serves as the
 * factory for SimulationModel, it creates concrete simulation models using
 * reflection. I chose this as my Masterpiece because this class signifies some
 * important ideas I learned during this project, one is the factory design
 * pattern, the second is the inheritance hierarchy, and the last one is the use
 * of reflection. Before the analysis period, the simulation models were created
 * inside the XMLMangaer class. The problems with the original design was that
 * the XMLManager should have its single and well defined purpose, which is to
 * manage the XML activities. It shouldn't take care the work of creating
 * simulation models. Instead, after refactoring, the simulation models are now
 * created in this simulation model factory. It not only hides the instantiation
 * logic from the user but also allow the user to create the object using a
 * common interface. I have also considered the trade of between using multiple
 * if statements and using reflection. Having many if statements to choose
 * between which class to instantiate is not flexible, since every time we add
 * another concrete subclass, I have to go inside and modify the original code.
 * This violates the open close principle. While the reflection would reduce the
 * performance of the program. Since this project is relatively small, it may
 * not matter which one to use, but when I need to deal with larger projects,
 * the trade off between performance and a more flexible code will become
 * critical.
 * 
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
