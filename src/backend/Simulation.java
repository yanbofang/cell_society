package backend;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team16.SimulationModel;
import cellsociety_team16.*;

public class Simulation {
	public Grid thisRoundGrid;
	private int n=0;
	private int m=0;
	//private double fireProb=0;
	//private double treeProb=0;
	
	private Handler myHandler;
	
	
	public ArrayList<Container> startNewRoundSimulation() {
		for (int i=0;i<this.n;i++) {
			for (int j=0;j<this.m;j++) {
				System.out.print(thisRoundGrid.getContainer(i*5+j).getMyCell());
			}
			System.out.println();
		}
		Grid nextRoundGrid = new Grid(this.n,this.m);
		thisRoundGrid.connectWith(nextRoundGrid);
		myHandler.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 3);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		return thisRoundGrid.getContainerlist();
	}
	
	public void setInitialGrid(SimulationModel modelGeneral) {
		this.myHandler = this.setupHandler(modelGeneral);
		List<Integer> initialStatus=modelGeneral.getPositions();
		this.n=modelGeneral.getRows();
		this.m=modelGeneral.getCols();
		this.thisRoundGrid = new Grid(this.n,this.m);
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				int curPos=i*n+j;
				int curPosStats=initialStatus.get(curPos);
				thisRoundGrid.getContainer(curPos).setCell(this.createNewCell(modelGeneral.getName(),curPosStats));
			}
		}
	}
	
	private Cell createNewCell(String modelName, int curPosStats) {
		if (modelName.compareTo("GameOfLife")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new Life();
			if (curPosStats==2) return new Life();			
		}

		if (modelName.compareTo("Segregation")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new People();
			if (curPosStats==2) return new People();			
		}

		if (modelName.compareTo("SpreadingFire")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new Tree();
			if (curPosStats==2) return new Fire();			
		}

		if (modelName.compareTo("WaTor")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new Fish();
			if (curPosStats==2) return new Shark();			
		}
		return null;
	}
	
	private Handler setupHandler(SimulationModel modelGeneral) {
		String modelName=modelGeneral.getName();
		if (modelName.compareTo("SpreadingFire")==0) {
			SpreadingFireModel model = (SpreadingFireModel) modelGeneral;
			double fireProb=model.getFireProbability();
			double treeProb=model.getTreeProbability();
			return new SpreadingFireHandler(fireProb,treeProb);
		}
		
		if (modelName.compareTo("WaTor")==0) {
			WaTorModel model = (WaTorModel) modelGeneral;
			int fishBreed = model.getFishBreed();
			int sharkBreed = model.getSharkBreed();
			return new WaTorHandler(fishBreed);
		}
		
		if (modelName.compareTo("GameOfLife")==0) {
			GameOfLifeModel model = (GameOfLifeModel) modelGeneral;
			return new GameOfLifeHandler();
		}
		
		if (modelName.compareTo("Segregation")==0) {
			SegregationModel model = (SegregationModel) modelGeneral;
			double percent = model.getSatisfactionRate();
			return new SegregationHandler(percent);
		}
		return null;
	}
}
