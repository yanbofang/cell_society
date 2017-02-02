package backend;

public class CellInfo {
	private Cell cellType;
	private int posX;
	private int posY;
	
	
	public CellInfo(Cell cellType, int posX, int posY) {
		this.cellType=cellType;
		this.posX=posX;
		this.posY=posY;
	}
	
	public Cell getCellType() {
		return cellType;
	}
	public void setCellType(Cell cellType) {
		this.cellType = cellType;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
}
