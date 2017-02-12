package backend;

public class HexagonGrid extends Grid{
	public HexagonGrid(int n, int m, int neighborDefn) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
		this.fillContainer();
	}
	
	public HexagonGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		this.setNeighborDefn(neighborDefn);
		this.setBoundary(boundary);
		this.fillContainer();
	}

	@Override
	public LocInfo getNeighborArrayX(int x, int y) {
		return new LocInfo(POSXHEXAGON);
	}

	@Override
	public LocInfo getNeighborArrayY(int x, int y) {
		// TODO Auto-generated method stub
		return new LocInfo(POSYHEXAGON);
	}
}
