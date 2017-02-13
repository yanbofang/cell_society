package cellsociety_team16;

import backend.Simulation;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class TriangleGrid extends Grid {
	public static final String myShape = "triangle";
	private static double TRANSLATION = 0;
	private static boolean MANIPULATED = true;

	public TriangleGrid(SimulationModel simulationModel, Simulation simulation) {
		super(simulationModel, simulation, TRANSLATION, MANIPULATED);

	}
	
	@Override
	protected Shape drawShape(double xLoc, double yLoc, double cellSize, int rotationAngle) {
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(
				new Double[] { 
						xLoc, yLoc + cellSize, 
						xLoc + cellSize, yLoc, 
						xLoc + 2*cellSize, yLoc + cellSize 
						});
		triangle.getTransforms().add(new Rotate(rotationAngle,xLoc+cellSize,yLoc+cellSize/2));
		return triangle;
	}

}
