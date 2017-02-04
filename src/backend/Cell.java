package backend;

import java.util.ArrayList;

public abstract class Cell {
	private int lifeSpan=0;
	private String identity;
	private Container myContainer;
	private int priority = 0;

	public int getPriority() {
		return priority;
	}
	public Container getMyContainer() {
		return myContainer;
	}
	public void setMyContainer(Container myContainer) {
		this.myContainer = myContainer;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public boolean is(String a) {
		return this.getIdentity().compareTo(a)==0;
	}
	public int getLifeSpan() {
		return lifeSpan;
	}
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	public void increaseLifeSpan() {
		this.lifeSpan++;
	}
	
}
