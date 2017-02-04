package backend;

public class Shark extends Fish {
	public Shark() {
		this.setPriority(0);
		this.setIdentity("Shark");
	}

	public String toString() {
		return "2";
	}
	
	@Override
	public Shark breed() {
		return new Shark();
	}

}
