package backend;

import java.util.ArrayList;
import java.util.List;

import backend.GameOfLifeSimulation.GameOfLifeHandler;
import backend.GameOfLifeSimulation.Life;
import backend.SegregationSimulation.People;
import backend.SegregationSimulation.SegregationHandler;
import backend.SlimeSimulation.Slime;
import backend.SlimeSimulation.SlimeHandler;
import backend.SpreadingFireSimulation.Fire;
import backend.SpreadingFireSimulation.SpreadingFireHandler;
import backend.SpreadingFireSimulation.Tree;
import backend.Sugarscape.Agent;
import backend.Sugarscape.Patch;
import backend.Sugarscape.SugarscapeHandler;
import backend.WaTorSimulation.Fish;
import backend.WaTorSimulation.Shark;
import backend.WaTorSimulation.WaTorHandler;
import cellsociety_team16.*;
import simulation_models.GameOfLifeModel;
import simulation_models.SegregationModel;
import simulation_models.SimulationModel;
import simulation_models.SpreadingFireModel;
import simulation_models.WaTorModel;
import simulation_models.SugarScapeModel;

/**
 * This class is in charge of the simulation and the connection with the front
 * end. After set up with the initial grid, the class will call Handler to
 * handle the next round of simulation.
 * 
 * 2 public methods are given. setIntialGrid() and startNewRoundSimulation().
 * 
 * @author chenxingyu
 *
 */

public class Simulation {
	private static final String SLIME_MOLDS = "SlimeMolds";
	private static final String SUGAR_SCAPE = "SugarScape";
	private static final String HEXAGON = "Hexagon";
	private static final String SQUARE = "Square";
	private static final String TRIANGLE = "Triangle";
	private static final String GAME_OF_LIFE = "GameOfLife";
	private static final String SEGREGATION = "Segregation";
	private static final String WA_TOR = "WaTor";
	private static final String SPREADING_FIRE = "SpreadingFire";
	private SimulationModel model;
	private Grid thisRoundGrid;
	private String shape;
	private int n = 0;
	private int m = 0;

	private Handler myHandler;

	/**
	 * This function is the interface with the GUI. Each time GUI needs an
	 * update over the status of the cell, this method will be called and return
	 * a list of Integer, which represents the status of the cell.
	 * 
	 * @return List<Integer>
	 */
	public GridInfo startNewRoundSimulation() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				System.out.print(thisRoundGrid.getContainer(i * 5 + j).getMyCell());
			}
			System.out.println();
		}
		Grid nextRoundGrid = createNewGrid(shape, this.n, this.m, 4);
		thisRoundGrid.connectWith(nextRoundGrid);
		myHandler.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 3);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < thisRoundGrid.getSize(); i++) {
			int max = 0;
			for (int k = 0; k < thisRoundGrid.getContainer(i).numCellContain(); k++) {
				if (Integer.parseInt(thisRoundGrid.getContainer(i).getIthCell(k).toString()) > max) {
					max = Integer.parseInt(thisRoundGrid.getContainer(i).getIthCell(k).toString());
				}
			}
			result.add(max);
		}
		List<Integer> amount = new ArrayList<Integer>();
		for (int i = 0; i < thisRoundGrid.getSize(); i++) {
			amount.add(thisRoundGrid.getContainer(i).numCellContain());
		}
		return new GridInfo(result, amount);
	}

	/**
	 * This function is the interface with the GUI. At the start of each
	 * simulation, the
	 * 
	 * @param modelGeneral
	 */
	public void setInitialGrid(SimulationModel modelGeneral) {
		this.myHandler = this.setupHandler(modelGeneral);
		this.model = modelGeneral;
		List<Integer> initialStatus = modelGeneral.getPositions();
		List<Integer> initialAmount = modelGeneral.getAmounts();
		this.n = modelGeneral.getRows();
		this.m = modelGeneral.getCols();
		this.shape = modelGeneral.getCellShape();
		this.thisRoundGrid = createNewGrid(this.shape, this.n, this.m, 4);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int curPos = i * n + j;
				int curPosStats = initialStatus.get(curPos);
				int curAmount = initialAmount.get(curPos);
				Container curContainer = thisRoundGrid.getContainer(curPos);
				if (model.getName().compareTo(SLIME_MOLDS) == 0 && curPosStats == 0) {
					curContainer.setPheromone("Food", curAmount);
				}
				curContainer.setMyCell(this.createNewCell(modelGeneral.getName(), curPosStats, curAmount));
			}
		}
	}

	/**
	 * This method is a helper method in charge of creating a new cell based on
	 * the current simulation type. For different simulation, it will interpret
	 * the states into different kinds of cell and return a cell of that
	 * specific type accordingly.
	 * 
	 * @param modelName
	 * @param curPosStats
	 * @return Cell according to the current simulation we are supposed to run
	 */
	private Cell createNewCell(String modelName, int curPosStats, int curAmount) {
		if (curPosStats == 0)
			return new EmptyCell();

		if (modelName.compareTo(GAME_OF_LIFE) == 0) {
			if (curPosStats == 1)
				return new Life();
			if (curPosStats == 2)
				return new Life();
		}

		if (modelName.compareTo(SEGREGATION) == 0) {
			if (curPosStats == 1)
				return new People();
			if (curPosStats == 2)
				return new People();
		}

		if (modelName.compareTo(SPREADING_FIRE) == 0) {
			if (curPosStats == 1)
				return new Tree();
			if (curPosStats == 2)
				return new Fire();
		}

		if (modelName.compareTo(WA_TOR) == 0) {
			if (curPosStats == 1)
				return new Fish();
			if (curPosStats == 2)
				return new Shark();
		}

		if (modelName.compareTo(SUGAR_SCAPE) == 0) {
			if (curPosStats == 1)
				return new Patch(curAmount, ((SugarScapeModel) model).getSugarGrowBackRate());
			if (curPosStats == 2)
				return new Agent(curAmount, ((SugarScapeModel) model).getSugarMetabolism());
		}

		if (modelName.compareTo(SLIME_MOLDS) == 0) {
			if (curPosStats == 1)
				return new Slime();
		}
		return null;
	}

	/**
	 * This method is a helper method for creating new grid based on the grid
	 * type passed in by SimulationModel
	 * 
	 * @param gridType
	 * @param n
	 * @param m
	 * @param num
	 * @return
	 */
	private Grid createNewGrid(String gridType, int n, int m, int num) {
		System.out.println(gridType);
		if (gridType.compareTo(TRIANGLE) == 0) {
			return new TriangleGrid(n, m, num);
		}

		if (gridType.compareTo(SQUARE) == 0) {
			return new SquareGrid(n, m, num);
		}

		if (gridType.compareTo(HEXAGON) == 0) {
			return new HexagonGrid(n, m, num);
		}
		return null;
	}

	/**
	 * This method is a helper method which creates a handler for each
	 * simulation based on the parameter passed in by SimulationModel. It will
	 * detect the model type and return the handler as needed.
	 * 
	 * @param modelGeneral
	 * @return Handler
	 */

	private Handler setupHandler(SimulationModel modelGeneral) {
		String modelName = modelGeneral.getName();
		if (modelName.compareTo(SPREADING_FIRE) == 0) {
			SpreadingFireModel model = (SpreadingFireModel) modelGeneral;
			double fireProb = model.getFireProbability();
			double treeProb = model.getTreeProbability();
			return new SpreadingFireHandler(fireProb, treeProb);
		}

		if (modelName.compareTo(WA_TOR) == 0) {
			WaTorModel model = (WaTorModel) modelGeneral;
			int fishBreed = model.getFishBreed();
			int sharkBreed = model.getSharkBreed();
			return new WaTorHandler(fishBreed);
		}

		if (modelName.compareTo(GAME_OF_LIFE) == 0) {
			GameOfLifeModel model = (GameOfLifeModel) modelGeneral;
			return new GameOfLifeHandler();
		}

		if (modelName.compareTo(SEGREGATION) == 0) {
			SegregationModel model = (SegregationModel) modelGeneral;
			double percent = model.getSatisfactionRate();
			return new SegregationHandler(percent);
		}

		if (modelName.compareTo(SUGAR_SCAPE) == 0) {
			return new SugarscapeHandler();
		}

		if (modelName.compareTo(SLIME_MOLDS) == 0) {
			// SlimeMoldsModel model=(SlimeMoldsModel) modelGeneral;
			return new SlimeHandler();
		}
		return null;
	}
}
