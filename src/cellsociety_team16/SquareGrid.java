package cellsociety_team16;

import backend.Simulation;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import simulation_models.SimulationModel;

public class SquareGrid extends Grid {
	/**
	 * Draws a grid of rectangles
	 * 
	 * @param simulationModel
	 * @param simulation
	 */
	public static final String myShape = "square";
	private static double TRANSLATION = 0;
	private static boolean MANIPULATED;

	// false because squares do not need an offselt
	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation, TRANSLATION, MANIPULATED);
	}
	@Override
	Shape drawShape(double xLoc, double yLoc, double cellSize, int rotationAngle) {
		// makes a square
		// double sideSize = Math.min(xSize, ySize);
		return new Rectangle(xLoc, yLoc, cellSize, cellSize);
	}
}