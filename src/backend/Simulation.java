package backend;

import java.util.ArrayList;

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
		WaTorHandler a = new WaTorHandler();
		a.startNewRoundSimulation(thisRoundGrid, nextRoundGrid, 3);
		thisRoundGrid = nextRoundGrid;
		System.out.println();
		return thisRoundGrid.getContainerlist();
	}
	
	public void setInitialGrid(ArrayList<Integer> a, int n, int m) {
		this.thisRoundGrid = new Grid(n,m);
		this.n=n;
		this.m=m;
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				boolean flag = false;
				for (int k=0; k<a.size();k++) {
					if (i*m+j==a.get(k)) {
						if (i % 2==0) 
							this.thisRoundGrid.getContainer(i*m+j).setCell(new Fish());
						else 
							this.thisRoundGrid.getContainer(i*m+j).setCell(new Shark());
						flag=true;
						break;
					}
				}
				if (!flag) this.thisRoundGrid.getContainer(i*m+j).setCell(new EmptyCell());
			}
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
