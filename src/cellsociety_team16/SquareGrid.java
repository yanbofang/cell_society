package cellsociety_team16;

import backend.Simulation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SquareGrid extends Grid {
/**
 * Draws a grid of rectangles
 * @param simulationModel
 * @param simulation
 */
	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}
	Shape drawShape(double xLoc, double yLoc, double xSize, double ySize) {
		//makes a square
		double sideSize = Math.min(xSize, ySize);
		return new Rectangle(xLoc, yLoc, sideSize, sideSize);
	}
}