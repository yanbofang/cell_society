package backend.WaTorSimulation;

/**
 * This class is an extension of Cell. 
 * This class is used for WaTor Simulation. Shark's priority is 0.
 * @author chenxingyu
 *
 */

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
