package cellsociety_team16;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class SquareGrid extends Grid {

	public SquareGrid(SimulationModel simulationModel) {
		super(simulationModel);
	}

	@Override
	public Node updateGrid(int gridExtents) {
		Group cells = new Group();
		myGridRows = mySimulationModel.getRows();
		myGridColumns = mySimulationModel.getCols();

		myColors = mySimulationModel.getColors();

		int index = 0;

		int sideSize = gridExtents / (Math.min(myGridRows, myGridColumns));
		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * sideSize;

			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				Rectangle r = new Rectangle(col_iter * sideSize, rowLoc, sideSize, sideSize);

				r.setFill(myColors.get(index));
				cells.getChildren().add(r);
				index++;
			}
		}
		return cells;
	}

	@Override
	public Node resetGrid() {
		mySimulationModel.setRandomPositions();
		mySimulation.setInitialGrid(mySimulationModel);
		return updateGrid(gridSideSize);
	}

}
