package backend;

import java.util.ArrayList;
import java.util.List;

public class SquareGrid extends Grid {
	private int n;
	private int m;
	public SquareGrid(int n, int m, int neighborDefn) {
		super(n,m);
		this.n=n;
		this.m=m;
		this.setNeighborDefn(neighborDefn);
		this.fillContainer();
	}
	
	public SquareGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		this.n=n;
		this.m=m;
		this.setNeighborDefn(neighborDefn);
		this.setBoundary(boundary);
		this.fillContainer();
	}

	@Override
	public LocInfo getNeighborArrayX(int x, int y) {
		return new LocInfo(POSXSQUARE);
	}

	@Override
	public LocInfo getNeighborArrayY(int x, int y) {
		// TODO Auto-generated method stub
		return new LocInfo(POSYSQUARE);
	}
}
