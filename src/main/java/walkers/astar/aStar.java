package walkers.astar;

import java.awt.Point;
import java.util.ArrayList;

import walkers.Model;
import walkers.mapobject.MapObject;

/**
 * This class searches quickest path using aStar algorithm.
 * 
 * @author Anton Milenin
 */
public class aStar {
	private ArrayList<MyPoint> points = new ArrayList<>();
	private Point result;
	private MapObject[][] map;
	private boolean[][] visited = new boolean[Model.maxOX + 1][Model.maxOY + 1];
	private Point target;

	/**
	 * This method searches for the next step from start point to target point
	 * using aStar algorithm.
	 * 
	 * @return the next step from start point to target point
	 * 
	 * @param start
	 *            starting point of the path
	 * @param target
	 *            end point of the path
	 */
	public Point find(Point start, Point target) {
		this.target = target;
		points.add(createPoint(start.x, start.y, null));
		while (!points.isEmpty() && result == null) {
			int minWeight = Integer.MAX_VALUE;
			int index = -1;
			for (int i = 0; i < points.size(); i++) {
				if (points.get(i).weight < minWeight) {
					index = i;
					minWeight = points.get(i).weight;
				}
			}
			MyPoint p = points.get(index);
			points.remove(index);
			visited[p.x][p.y] = true;
			check(p, p.x + 1, p.y);
			check(p, p.x - 1, p.y);
			check(p, p.x, p.y + 1);
			check(p, p.x, p.y - 1);
		}
		points.clear();
		for (int x = 0; x <= Model.maxOX; x++) {
			for (int y = 0; y <= Model.maxOY; y++) {
				visited[x][y] = false;
			}
		}
		if (result == null)
			return null;
		target = null;
		Point tmp = (Point) result.clone();
		result = null;
		return tmp;
	}

	/**
	 * Class constructor.
	 * 
	 * @param map
	 *            array that shows positions of all objects on the field
	 */
	public aStar(MapObject[][] map) {
		this.map = map;
	}

	private MyPoint createPoint(int x, int y, MyPoint point) {
		int length = 1;
		if (point != null) {
			length += point.length;
		}
		return new MyPoint(Math.abs(x - target.x) + Math.abs(y - target.y) + length, x, y, length, point);
	}

	private void check(MyPoint from, int x, int y) {
		if (x == target.x && y == target.y) {
			if (from.ancestor == null) {
				result = new Point(x, y);
			} else {
				MyPoint backTrack = from;
				while (backTrack.ancestor.ancestor != null) {
					backTrack = backTrack.ancestor;
				}
				result = new Point(backTrack.x, backTrack.y);
			}
		} else {
			if (result != null || x > Model.maxOX || x < 0 || y > Model.maxOY || y < 0 || map[x][y] != null
					|| visited[x][y])
				return;

			points.add(createPoint(x, y, from));
		}

	}
}
