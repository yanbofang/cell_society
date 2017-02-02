package backend;

import java.util.ArrayList;

public class Simulation {
	public Grid thisRoundGrid;
	public Grid startNewRoundSimulation() {
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				System.out.print(thisRoundGrid.getContainer(i*5+j).getMyCell());
			}
			System.out.println();
		}
		Grid nextRoundGrid = new Grid(5,5);
		thisRoundGrid.connectWith(nextRoundGrid);
		SegregationHandler a = new SegregationHandler();
		a.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 2);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		return thisRoundGrid;
	}
	
	public static void main(String[] args) {
		Simulation test = new Simulation();
		test.thisRoundGrid=new Grid(5,5);
		for (int i=0;i<test.thisRoundGrid.getSize();i++) {
			if (i==5 || i==6 || i==7 || i==11 || i==12 || i==13) {
				test.thisRoundGrid.getContainer(i).setCell(new People());
			} else {
				test.thisRoundGrid.getContainer(i).setCell(new EmptyCell());
			}
		}
		for (int i=0;i<1000;i++) {
			test.startNewRoundSimulation();
		}
	}
}
