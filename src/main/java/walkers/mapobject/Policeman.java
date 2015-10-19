package walkers.mapobject;

import java.awt.Point;
import java.util.Stack;

import walkers.Model;
import walkers.astar.aStar;

/**
 * This class simulates policeman behavior.
 * 
 * @author Anton Milenin
 */
public class Policeman extends Person implements MapObject {
	private Point target;
	private State state = State.STANDBY;
	private Stack<Walker> illuminatedWalkers;
	private aStar pathFinder;

	/**
	 * Class constructor.
	 * 
	 * @param map
	 *            array that shows positions of all objects on the field
	 * @param illuminatedWalkers
	 *            walkers that don't move and were illuminated buy lamp
	 */
	public Policeman(MapObject[][] map, Stack<Walker> illuminatedWalkers) {
		super(map);
		this.illuminatedWalkers = illuminatedWalkers;
		pathFinder = new aStar(map);
	}

	@Override
	public char getMapToken() {
		return 'P';
	}

	@Override
	public void move() {
		switch (state) {
		case STANDBY:
			if (!illuminatedWalkers.isEmpty() && map[Model.maxOX][Model.policeStationOy] == null) {
				Walker targetWalker = illuminatedWalkers.pop();
				target = new Point(targetWalker.getOX(), targetWalker.getOY());
				map[Model.maxOX][Model.policeStationOy] = this;
				state = State.GOING_TO_WALKER;
				oX = Model.maxOX;
				oY = Model.policeStationOy;
			}
			break;
		case GOING_TO_STATION:
			if (oX == Model.maxOX && oY == Model.policeStationOy) {
				map[Model.maxOX][Model.policeStationOy] = null;
				state = State.STANDBY;
			} else {
				Point nextStep = pathFinder.find(new Point(oX, oY), target);
				if (nextStep != null) {
					map[nextStep.x][nextStep.y] = this;
					map[oX][oY] = null;
					oX = nextStep.x;
					oY = nextStep.y;
				}
			}
			break;
		case GOING_TO_WALKER:
			Point nextStep = pathFinder.find(new Point(oX, oY), target);
			if (nextStep != null) {
				map[nextStep.x][nextStep.y] = this;
				map[oX][oY] = null;
				oX = nextStep.x;
				oY = nextStep.y;
				if (nextStep.equals(target)) {
					state = State.GOING_TO_STATION;
					target = new Point(Model.maxOX, Model.policeStationOy);
				}
			}
			break;
		}
	}

	private enum State {
		STANDBY, GOING_TO_STATION, GOING_TO_WALKER
	}
}
