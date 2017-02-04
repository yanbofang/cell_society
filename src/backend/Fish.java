package backend;

public class Fish extends Cell {
	
	public Fish() {
		this.setPriority(1);
		this.setIdentity("Fish");
	}

	public String toString() {
		return "1";
	}

	public Fish breed() {
		return new Fish();
	}
}
