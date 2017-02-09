package cellsociety_team16;

import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
/**
 * Abstract class that will create a new grid in the visualization window
 * @author Elbert
 *
 */
public abstract class Grid {
	//TODO may not need
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
//Integers correspond to cell types and colors
	private List<Integer> myInts;
	private Map<Integer, Color> myColorMap;
	private boolean isGridSizeStatic;
	private Shape myShape;
/**
 * Declares a grid object
 */
	public Grid() {
	}

	public void setColor(int cellType, Color newColor) {
		myColorMap.put(cellType, newColor);
	}

	private Color getColor(int cellType) {
		return myColorMap.get(cellType);
	}

	public void setStaticGridSize(boolean yes) {
		isGridSizeStatic = yes;
	}

	abstract public Node updateGrid(int gridExtents);

	abstract public Node resetGrid();

//	abstract public List getCellPositions();

}
