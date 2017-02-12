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
	private static boolean offsetHalf;
	//false because squares do not need an offselt
	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation, offsetHalf);
	}
	Shape drawShape(double xLoc, double yLoc, double cellSize) {
		//makes a square
		//double sideSize = Math.min(xSize, ySize);
		return new Rectangle(xLoc, yLoc, cellSize, cellSize);
	}
}