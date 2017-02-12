package cellsociety_team16;

import java.util.ArrayList;

import backend.Simulation;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
/**
 * Draws a grid of hexagons
 * @author Elbert
 *
 */
public class HexagonGrid extends Grid {
	private int HEXAGON_ANGLE;
	private int sideCount = 6;

	public HexagonGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation);
	}
@Override
	protected Shape drawShape(double xLoc, double yLoc, double sideSize) {
		 // note: if xSize == ySize, will appear as a square
	//so we will assume that the viualization window is square and a bit of triangular overhang is acceptable
		 Polygon h = new Polygon();
		// double yDifference = Math.sqrt(xSize*xSize-ySize*ySize);
		 h.getPoints().addAll(new Double[]{
//				 xLoc, yLoc-yDifference, 
//				xLoc+xSize/2, yLoc, 
//				xLoc+xSize, yLoc-yDifference,
//				xLoc+xSize, yLoc-yDifference-ySize/2,
//				xLoc+xSize/2, yLoc+ySize,
//				xLoc, yLoc-ySize+yDifference});

		return h;
	}
}
