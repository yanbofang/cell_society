package backend;

import java.util.ArrayList;
/**
 * This class represents the general empty cell.
 * It's used for all three simulations and it has priority 2.
 * @author chenxingyu
 *
 */
public class EmptyCell extends Cell{
	public EmptyCell() {
		this.setPriority(2);
		this.setIdentity("EmptyCell");
	}
	
	public String toString() {
		return "0";
	}
}
