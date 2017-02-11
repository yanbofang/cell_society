package cellsociety_team16;

import java.util.ArrayList;

import backend.Simulation;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class HexagonGrid extends Grid {
	private int HEXAGON_ANGLE;
	private int sideCount = 6;

	public HexagonGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}

	@Override
	protected Shape drawShape(int xLoc, int yLoc, int xSize, int ySize) {
//		ArrayList<Double> pointArray = new ArrayList<Double>();
//		Polygon h = new Polygon();
//
//		for (int i = 0; i < sideCount * 2; i += 2) {
//
//		}
//	
//	return new Polygon(pointArray);
		return null;
	}
}
