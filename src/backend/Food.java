package backend;

public class Food extends Cell{
	public Food() {
		this.setPriority(0);
		this.setIdentity("Food");
	}

	public String toString() {
		return "F";
	}
}
