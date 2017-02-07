package backend;

/**
 * This class is an extension of Cell. 
 * This class is used for Fire Spreading Simulation. Tree's priority is 1.
 * @author chenxingyu
 *
 */
public class Tree extends Cell{
	public Tree() {
		this.setPriority(1);
		this.setIdentity("Tree");
	}

	public String toString() {
		return "1";
	}
}
