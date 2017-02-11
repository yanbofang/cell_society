package cellsociety_team16;

import backend.Simulation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SquareGrid extends Grid {

	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}

	@Override
	Shape drawShape(int xLoc, int yLoc, int xSize, int ySize) {
		return new Rectangle(xLoc, yLoc, xSize, ySize);
	}
}
