package backend;

public class TriangleGrid extends Grid{
	public TriangleGrid(int n, int m, int neighborDefn) {
		super(n,m);
		//System.out.println("neighborDefn="+neighborDefn);
		this.setNeighborDefn(neighborDefn);
		this.fillContainer();
	}

	public TriangleGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		//System.out.println("neighborDefn="+neighborDefn);
		this.setNeighborDefn(neighborDefn);
		this.setBoundary(boundary);
		this.fillContainer();
	}

	
	@Override
	public LocInfo getNeighborArrayX(int x, int y) {
		if ((x+y) % 2==1) return new LocInfo(POSXTRIANGLE); else return new LocInfo(POSXTRIANGLEREVERSE);
	}

	@Override
	public LocInfo getNeighborArrayY(int x, int y) {
		// TODO Auto-generated method stub
		return new LocInfo(POSYTRIANGLE);
	}

}
