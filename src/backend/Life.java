package backend;

import java.util.ArrayList;

public class Life extends Cell {
	private Container myContainer;
	private int priority = 0;
	
	public Life() {
	}
		
	public String toString() {
		return "1";
	}

	public Container getMyContainer() {
		return myContainer;
	}

	public void setMyContainer(Container myContainer) {
		this.myContainer = myContainer;
	}
	

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return this.priority;
	}

	@Override
	public void setContainer(Container a) {
		// TODO Auto-generated method stub
		this.myContainer=a;
	}
}
