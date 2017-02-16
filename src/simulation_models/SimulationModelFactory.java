package simulation_models;

import xml.XMLSimulation;

public class SimulationModelFactory {
	public static final String MODEL_PACKAGE = "simulation_models.";

	public SimulationModel createSimulationModel(XMLSimulation xml){
		try {
			Class<?> model = Class.forName(MODEL_PACKAGE + xml.getName() + "Model");
			return (SimulationModel) model.getDeclaredConstructor(XMLSimulation.class).newInstance(xml);
		} catch (Exception e) {
			Class<?> model;
			try {
				model = Class.forName(MODEL_PACKAGE + xml.getName().substring(0, xml.getName().length() - 3) + "Model");
			} catch (Exception ee) {
				return null;
			}
		}
		return null;
	}

}
