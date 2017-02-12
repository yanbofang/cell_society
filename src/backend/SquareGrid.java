package backend;

import java.util.ArrayList;
import java.util.List;

public class SquareGrid extends Grid {
	public SquareGrid(int n, int m, int neighborDefn) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
	}
	
	public SquareGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
		this.setBoundary(boundary);
	}

	@Override
	public LocInfo getNeighborArrayX() {
		return new LocInfo(POSXSQUARE);
	}

	@Override
	public LocInfo getNeighborArrayY() {
		// TODO Auto-generated method stub
		return new LocInfo(POSYSQUARE);
	}
}
