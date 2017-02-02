package backend;

import java.util.ArrayList;

public class EmptyCell extends Cell{
	private Container myContainer;

	public String toString() {
		return "0";
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setContainer(Container a) {
		// TODO Auto-generated method stub
		this.myContainer=a;
	}

}
