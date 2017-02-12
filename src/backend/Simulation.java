package backend;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team16.SimulationModel;
import cellsociety_team16.*;
/**
 * This class is in charge of the simulation and the connection with the front end.
 * After set up with the initial grid, the class will call Handler to handle the next
 * round of simulation. 
 * 
 * 2 public methods are given. setIntialGrid() and startNewRoundSimulation().
 * 
 * @author chenxingyu
 *
 */

public class Simulation {
	private static final String GAME_OF_LIFE = "GameOfLife";
	private static final String SEGREGATION = "Segregation";
	private static final String WA_TOR = "WaTor";
	private static final String SPREADING_FIRE = "SpreadingFire";
	private Grid thisRoundGrid;
	private int n=0;
	private int m=0;
	
	private Handler myHandler;
	
	/**
	 * This function is the interface with the GUI. Each time GUI needs an update over the 
	 * status of the cell, this method will be called and return a list of Integer, which
	 * represents the status of the cell.
	 * @return List<Integer>
	 */
	public List<Integer> startNewRoundSimulation() {
		for (int i=0;i<this.n;i++) {
			for (int j=0;j<this.m;j++) {
				System.out.print(thisRoundGrid.getContainer(i*5+j).getMyCell());
			}
			System.out.println();
		}
		Grid nextRoundGrid = new SquareGrid(this.n,this.m, 8);
		thisRoundGrid.connectWith(nextRoundGrid);
		myHandler.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 3);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		List<Integer> result = new ArrayList<Integer>();
		for (int i=0;i<thisRoundGrid.getSize();i++) {
			result.add(Integer.parseInt(thisRoundGrid.getContainer(i).getMyCell().toString()));
		}
		return result;
	}
	/**
	 * This function is the interface with the GUI. At the start of each simulation, the 
	 * @param modelGeneral
	 */
	public void setInitialGrid(SimulationModel modelGeneral) {
		this.myHandler = this.setupHandler(modelGeneral);
		List<Integer> initialStatus=modelGeneral.getPositions();
		this.n=modelGeneral.getRows();
		this.m=modelGeneral.getCols();
		this.thisRoundGrid = new SquareGrid(this.n,this.m, 8);
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				int curPos=i*n+j;
				int curPosStats=initialStatus.get(curPos);
				thisRoundGrid.getContainer(curPos).setMyCell(this.createNewCell(modelGeneral.getName(),curPosStats));
			}
		}
	}
	
	/**
	 * This method is a helper method in charge of creating a new cell based on the current simulation
	 * type. For different simulation, it will interpret the states into different kinds of cell and return
	 * a cell of that specific type accordingly.
	 * 
	 * @param modelName
	 * @param curPosStats
	 * @return Cell according to the current simulation we are supposed to run
	 */
	private Cell createNewCell(String modelName, int curPosStats) {
		if (curPosStats==0) return new EmptyCell();
		
		if (modelName.compareTo(GAME_OF_LIFE)==0) {
			if (curPosStats==1) return new Life();
			if (curPosStats==2) return new Life();			
		}

		if (modelName.compareTo(SEGREGATION)==0) {
			if (curPosStats==1) return new People();
			if (curPosStats==2) return new People();			
		}

		if (modelName.compareTo(SPREADING_FIRE)==0) {
			if (curPosStats==1) return new Tree();
			if (curPosStats==2) return new Fire();			
		}

		if (modelName.compareTo(WA_TOR)==0) {
			if (curPosStats==1) return new Fish();
			if (curPosStats==2) return new Shark();			
		}
		return null;
	}
	
	/**
	 * This method is a helper method which creates a handler for each simulation based on the parameter passed
	 * in by SimulationModel. It will detect the model type and return the handler as needed.
	 * @param modelGeneral
	 * @return Handler
	 */
	
	private Handler setupHandler(SimulationModel modelGeneral) {
		String modelName=modelGeneral.getName();
		if (modelName.compareTo(SPREADING_FIRE)==0) {
			SpreadingFireModel model = (SpreadingFireModel) modelGeneral;
			double fireProb=model.getFireProbability();
			double treeProb=model.getTreeProbability();
			return new SpreadingFireHandler(fireProb,treeProb);
		}
		
		if (modelName.compareTo(WA_TOR)==0) {
			WaTorModel model = (WaTorModel) modelGeneral;
			int fishBreed = model.getFishBreed();
			int sharkBreed = model.getSharkBreed();
			return new WaTorHandler(fishBreed);
		}
		
		if (modelName.compareTo(GAME_OF_LIFE)==0) {
			GameOfLifeModel model = (GameOfLifeModel) modelGeneral;
			return new GameOfLifeHandler();
		}
		
		if (modelName.compareTo(SEGREGATION)==0) {
			SegregationModel model = (SegregationModel) modelGeneral;
			double percent = model.getSatisfactionRate();
			return new SegregationHandler(percent);
		}
		return null;
	}
}
