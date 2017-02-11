package cellsociety_team16;

import javafx.scene.shape.Shape;
import backend.Simulation;
import javafx.scene.shape.Rectangle;

public class SquareGrid extends Grid {
	/**
	 * Draws a grid of squares using
	 * 
	 * @param simulationModel
	 * @param simulation
	 */
	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}

	@Override
	protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize) {
		return new Rectangle(xLoc, yLoc, xSize, ySize);
	}
}
