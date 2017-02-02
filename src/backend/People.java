package backend;

public class People extends Cell {
	private int priority = 0;
	private Container myContainer;

	@Override
	public void setContainer(Container a) {
		// TODO Auto-generated method stub
		this.setMyContainer(a);
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return this.priority;
	}

	public Container getMyContainer() {
		return myContainer;
	}

	public void setMyContainer(Container myContainer) {
		this.myContainer = myContainer;
	}

	public String toString() {
		return "1";
	}
}
