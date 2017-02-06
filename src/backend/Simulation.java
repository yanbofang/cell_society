package backend;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team16.SimulationModel;

public class Simulation {
	public Grid thisRoundGrid;
	private int n=0;
	private int m=0;
	
	public ArrayList<Container> startNewRoundSimulation() {
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				System.out.print(thisRoundGrid.getContainer(i*5+j).getMyCell());
			}
			System.out.println();
		}
		Grid nextRoundGrid = new Grid(n,m);
		thisRoundGrid.connectWith(nextRoundGrid);
		SpreadingFireHandler a = new SpreadingFireHandler();
		a.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 3);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		return thisRoundGrid.getContainerlist();
	}
	
	public void setInitialGrid(SimulationModel model) {
		this.n=model.getRows();
		this.m=model.getCols();
		List<Integer> initialStatus=model.getPositions();
		this.thisRoundGrid = new Grid(this.n,this.m);
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				int curPos=i*n+j;
				int curPosStats=initialStatus.get(curPos);
				thisRoundGrid.getContainer(curPos).setCell(this.createNewCell(model.getName(),curPosStats));
			}
		}
	}
	
	private Cell createNewCell(String modelName, int curPosStats) {
		if (modelName.compareTo("GameOfLife")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new Life();
			if (curPosStats==2) return null;			
		}

		if (modelName.compareTo("Segregation")==0) {
			if (curPosStats==0) return new EmptyCell();
			if (curPosStats==1) return new People();
			if (curPosStats==2) return null;			
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
	}
	public static void main(String[] args) {
		Simulation test = new Simulation();
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(5);a.add(6);a.add(7);a.add(11);a.add(12);a.add(13);
		test.setInitialGrid(a, 5, 5);
		for (int i=0;i<200;i++) {
			test.startNewRoundSimulation();
		}
	}
}
