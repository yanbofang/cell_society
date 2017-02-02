package backend;

public class CellInfo {
	private String cellType;
	private int posX;
	private int posY;
	
	
	public CellInfo(String cellType, int posX, int posY) {
		this.cellType=cellType;
		this.posX=posX;
		this.posY=posY;
	}
	
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
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
