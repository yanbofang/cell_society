package cellsociety_team16;

import backend.Simulation;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import simulation_models.SimulationModel;

/**
 * Draws a grid of hexagons
 * 
 * @author Elbert
 *
 */
public class HexagonGrid extends Grid {
	public static final String myShape = "hexagon";
	private int HEXAGON_ANGLE;
	private int sideCount = 6;
	private static double TRANSLATION = 0.5;
	private static boolean MANIPULATABLE = false;
	public HexagonGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation, TRANSLATION, MANIPULATABLE);
	}

	@Override
	protected Shape drawShape(double xLoc, double yLoc, double cellSize, int rotationAngle) {
		// note: if xSize == ySize, will appear as a square
		// so we will assume that the viualization window is square and a bit of
		// triangular overhang is acceptable
		Polygon hexagon = new Polygon();
		// double yDifference = Math.sqrt(xSize*xSize-ySize*ySize);
		// if base hexagons a square
		// from equation for equilatural hexagon
		double yDifference = cellSize - cellSize / Math.sqrt(3);
		hexagon.getPoints()
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
		return hexagon;
	}
}
