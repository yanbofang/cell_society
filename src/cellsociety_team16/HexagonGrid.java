package cellsociety_team16;

import java.util.ArrayList;

import backend.Simulation;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

/**
 * Draws a grid of hexagons
 * 
 * @author Elbert
 *
 */
public class HexagonGrid extends Grid {
	private int HEXAGON_ANGLE;
	private int sideCount = 6;
	private static boolean offsetHalf = true;

	public HexagonGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation, offsetHalf);
	}

	@Override
	protected Shape drawShape(double xLoc, double yLoc, double cellSize) {
		// note: if xSize == ySize, will appear as a square
		// so we will assume that the viualization window is square and a bit of
		// triangular overhang is acceptable
		Polyline h = new Polyline();
		// double yDifference = Math.sqrt(xSize*xSize-ySize*ySize);
		// if base hexagons a square
		// from equation for equilatural hexagon
		double yDifference = cellSize / Math.sqrt(3);
		h.getPoints()
				.addAll(new Double[] { 
						xLoc + cellSize / 2, yLoc, 
						xLoc + cellSize, yLoc + yDifference, 
						xLoc + cellSize, yLoc + cellSize, 
						xLoc + cellSize / 2, yLoc + yDifference + cellSize, 
						xLoc, yLoc + cellSize, 
						xLoc, yLoc + yDifference 
						});
		//makes a sick chevron
//		xLoc + cellSize / 2, yLoc, 
//		xLoc + cellSize, yLoc + yDifference, 
//		xLoc + cellSize, yLoc - cellSize, 
//		xLoc + cellSize / 2, yLoc - yDifference - cellSize, 
//		xLoc, yLoc - cellSize, 
//		xLoc, yLoc + yDifference 
		return h;
	}
}
