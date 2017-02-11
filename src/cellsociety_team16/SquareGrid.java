package cellsociety_team16;

import javafx.scene.shape.Shape;
import backend.Simulation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SquareGrid extends Grid {

	public SquareGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}

	@Override
	protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize) {
		return new Rectangle(xLoc, yLoc, xSize, ySize);
	}
}
