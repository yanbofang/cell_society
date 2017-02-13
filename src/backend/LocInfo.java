package backend;

/**
 * A helper class to store the locational information
 * 
 * @author chenxingyu
 *
 */
public class LocInfo {
	int[] neighbor;

	public LocInfo(int[] a) {
		this.neighbor = a;
	}

	public int get(int k) {
		return neighbor[k];
	}
}
