package backend;

import java.util.ArrayList;

public class EmptyCell extends Cell{
	public EmptyCell() {
		this.setPriority(2);
		this.setIdentity("EmptyCell");
	}
	
	public String toString() {
		return "0";
	}
}
