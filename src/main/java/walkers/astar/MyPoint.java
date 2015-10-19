package walkers.astar;

/**
 * This class stores points' neded for aStar algorithm.
 * 
 * @author Anton Milenin
 */
public class MyPoint implements Comparable<MyPoint> {
	public final int weight;
	public final int x;
	public final int y;
	public final MyPoint ancestor;
	public final int length;

	/**
	 * Class contructor
	 */
	public MyPoint(int weight, int x, int y, int length, MyPoint ancestor) {
		this.weight = weight;
		this.x = x;
		this.y = y;
		this.ancestor = ancestor;
		this.length = length;
	}

	@Override
	public int compareTo(MyPoint o) {
		return weight - o.weight;
	}
}