package cellsociety_team16;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Loads the UI for Cell Society interface
 * 
 * @author Elbert
 *
 */
public class GUI extends Application {
	// The size of the visualization window will not change
	public static final int SCREENWIDTH = 500;
	public static final int SCREENHEIGHT = 700;
	public static final String TITLE = "Cellular Simulation";
	public static final Paint BACKGROUND = Color.WHITE;

	private Scene myScene;
	// Sets the padding for the grid
	private int padding = SCREENWIDTH / 10;
	private int myGridRows, myGridColumns;
	private String mySimType;
	private int bottomPadding = SCREENWIDTH / 4;

	// private Simulation mySimulation;
	public void GUI() {
		Stage newStage = new Stage();
		this.start(newStage);
	}

	/**
	 * Initialize the display and updates
	 */
	public void start(Stage primaryStage) {
		Scene scene = setUp(SCREENWIDTH, SCREENHEIGHT, BACKGROUND);
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
	}

	/**
	 * 
	 * @param width
	 *            sets window width
	 * @param height
	 *            sets window height
	 * @param color
	 *            set background color
	 * @return new scene with all of the parts loaded, awaiting input
	 */
	public Scene setUp(int width, int height, Paint color) {
		// Select a file
		// will automatically parse
		Group root = new Group();
		//TODO keep things centered and aligned
		//GridPlane grid = new GridPlane();
		myScene = new Scene(root, width, height, color);
//TODO add button functionality and location as per the resizable gridpane
		Group buttons = new Group();
		// TODO do not hardcode these names, likely don't even hard code the
		// cell colors
		Button play = new Button("Play");
		// TODO toggle to pause
		Button step = new Button("Step Forward");
		Button loadFile = new Button("Load a File");
		Slider speedSlider = new Slider();
		speedSlider.setMin(0);
		speedSlider.setMax(100);
		// Sets default slider location
		speedSlider.setValue(50);
		speedSlider.setBlockIncrement(10);
		Group myGrid = setUpGrid();
		root.getChildren().add(myGrid);
		return myScene;
	}

	/**
	 * Draws and colors and grid of squares
	 * 
	 * @return a new grid object
	 */

	private Group setUpGrid() {
		// mySimulation = new getSimulation(dataFile);
		// GridPane grid = new GridPane();
		Group cells = new Group();
		// myGridRows = mySimulation.getRows();
		myGridRows = 15;
		myGridColumns = 3;
		// myGridColumns = mySimulation.getColumns();

		int sideSize = Math.min((SCREENWIDTH - padding * 2) / myGridColumns,
				(SCREENHEIGHT - bottomPadding - padding) / myGridRows);

		for (int row_iter = 0; row_iter < myGridRows; row_iter++) {
			// determines place on the screen
			int rowLoc = row_iter * sideSize + padding;
			for (int col_iter = 0; col_iter < myGridColumns; col_iter++) {
				// if not empty
				Rectangle r = new Rectangle(col_iter * sideSize + padding, rowLoc, sideSize, sideSize);
				// r.setFill(mySimulation.getTypeColor);
				r.setFill(Color.rgb(15 * row_iter, 15 * col_iter, 0));
				cells.getChildren().add(r);
			}
		}
		return cells;
	}

	/**
	 * Figures out starting positions
	 */
	// private int[] initLocs(){
	//
	// }
	//
	/**
	 * Runs the program, for testing this individual segment
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
