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
	}
	
	public SquareGrid(int n, int m, int neighborDefn, String boundary) {
		super(n,m);
		this.n=n;
		this.m=m;
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
	
	public List<Container> getNeighborAtDirection(Container curContainer, int i) {
		List<Container> temp=new ArrayList<Container>();
		int[][] directionX=new int[][]{{-1,-2,-3},{0,0,0},{0,0,0},{1,2,3}};
		int[][] directionY=new int[][]{{0,0,0},{1,2,3},{-1,-2,-3},{0,0,0}};
		for (int j=0;j<3;i++) {
			int xx=this.boundXHandle(curContainer.getPosX()+directionX[i][j]);
			int yy=this.boundYHandle(curContainer.getPosY()+directionY[i][j]);
			if (xx>=n || xx<0 || yy<0 || yy>=m) continue;
			temp.add(this.getContainer(xx*n+yy));
		}
		return temp;
	}
}
