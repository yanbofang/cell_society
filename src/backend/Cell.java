package backend;

public abstract class Cell {
	public abstract void setContainer(Container a);
	public abstract int getPriority();
	public abstract String ruleCheck();
}
