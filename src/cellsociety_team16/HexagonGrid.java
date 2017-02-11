package cellsociety_team16;

import java.util.ArrayList;

import backend.Simulation;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

//TODO figure out if it is just the mere existance of this file that makes it always come up as a merge conflict
//Wait dude this is my old comment I used to have a different cry for help why is this no longer deleted where did it not go
public class HexagonGrid extends Grid {
	private int HEXAGON_ANGLE;
	private int sideCount = 6;

	public HexagonGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}

	@Override
	protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize) {
		// ArrayList<Double> pointArray = new ArrayList<Double>();
		// Polygon h = new Polygon();
		//
		// for (int i = 0; i < sideCount * 2; i += 2) {
		//
		// }
		// return new Polygon(pointArray);
		return null;
	}
}
