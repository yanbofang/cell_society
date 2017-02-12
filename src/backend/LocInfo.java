package backend;

public class LocInfo {
	int[] neighbor;
	public LocInfo(int[] a) {
		this.neighbor=a;
	}
	
	public int get(int k) {
		return neighbor[k];
	}
}
