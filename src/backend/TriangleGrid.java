package backend;

public class TriangleGrid extends Grid{
	public TriangleGrid(int n, int m, int neighborDefn) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
	}

	public TriangleGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
		this.setBoundary(boundary);
	}

	
	@Override
	public LocInfo getNeighborArrayX() {
		return new LocInfo(POSXTRIANGLE);
	}

	@Override
	public LocInfo getNeighborArrayY() {
		// TODO Auto-generated method stub
		return new LocInfo(POSYTRIANGLE);
	}

}
