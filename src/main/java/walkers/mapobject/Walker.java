package walkers.mapobject;

import java.util.Random;

import walkers.Model;

/**
 * This class simulates walker behavior.
 * 
 * @author Anton Milenin
 */
public class Walker implements MapObject {
	private int sllepingTime = 0;
	private int oX, oY;
	private WalkerState state = WalkerState.AT_START;
	private MapObject[][] map;
	private Random random;

	/**
	 * Class constructor
	 * 
	 * @param map
	 *            array that shows positions of all walkers on the field
	 * @param random
	 *            randomizer that we to have opportunity to control random
	 *            output via randomizer seeds
	 */
	public Walker(MapObject[][] map, Random random) {
		this.map = map;
		this.random = random;
	}

	/**
	 * This method is called to simulate walkers' movement.
	 */
	public void move() {
		switch (state) {
		case ASLEEP:
			sllepingTime--;
			if (sllepingTime == 0)
				state = WalkerState.WALKING;
			break;
		case ASLLEP_FOREWER:
			break;
		case AT_START:
			if (map[Model.pubOx][0] == null) {
				map[Model.pubOx][0] = this;
				state = WalkerState.WALKING;
				oX = Model.pubOx;
				oY = 0;
			}
			break;
		case WALKING:
			if (oX == 0) {
				if (oY == 0) {
					switch (random.nextInt(2)) {
					case 0:
						tryToMove(1, 0);
						break;
					case 1:
						tryToMove(0, 1);
					}
				} else if (oY == Model.maxOY) {
					switch (random.nextInt(2)) {
					case 0:
						tryToMove(1, Model.maxOY);
						break;
					case 1:
						tryToMove(0, Model.maxOY - 1);
					}
				} else {
					switch (random.nextInt(3)) {
					case 0:
						tryToMove(1, oY);
						break;
					case 1:
						tryToMove(0, oY + 1);
						break;
					case 2:
						tryToMove(0, oY - 1);
					}
				}
			} else if (oX == Model.maxOX) {
				if (oY == 0) {
					switch (random.nextInt(2)) {
					case 0:
						tryToMove(Model.maxOX - 1, 0);
						break;
					case 1:
						tryToMove(Model.maxOX, 1);
					}
				} else if (oY == 14) {
					switch (random.nextInt(2)) {
					case 0:
						tryToMove(Model.maxOX - 1, Model.maxOY);
						break;
					case 1:
						tryToMove(Model.maxOX, Model.maxOY - 1);
					}
				} else {
					switch (random.nextInt(3)) {
					case 0:
						tryToMove(Model.maxOX - 1, oY);
						break;
					case 1:
						tryToMove(Model.maxOX, oY + 1);
						break;
					case 2:
						tryToMove(Model.maxOX, oY - 1);
					}
				}

			} else if (oY == 0) {
				switch (random.nextInt(3)) {
				case 0:
					tryToMove(oX, 1);
					break;
				case 1:
					tryToMove(oX + 1, 0);
					break;
				case 2:
					tryToMove(oX - 1, 0);
				}
			} else if (oY == 14) {
				switch (random.nextInt(3)) {
				case 0:
					tryToMove(oX, Model.maxOY - 1);
					break;
				case 1:
					tryToMove(oX + 1, Model.maxOY);
					break;
				case 2:
					tryToMove(oX - 1, Model.maxOY);
				}
			} else {
				switch (random.nextInt(4)) {
				case 0:
					tryToMove(oX, oY + 1);
					break;
				case 1:
					tryToMove(oX + 1, oY);
					break;
				case 2:
					tryToMove(oX, oY - 1);
					break;
				case 3:
					tryToMove(oX - 1, oY);
				}
			}
		}
	}

	/**
	 * 
	 * @return state of the walker
	 */
	public WalkerState getState() {
		return state;
	}

	/**
	 * This method is called to to move walker to corresponding position.
	 * 
	 * @param newOX
	 *            new oX coordinate
	 * @param newOY
	 *            new oY coordinate
	 */
	private void tryToMove(int newOX, int newOY) {
		if (map[newOX][newOY] == null) {
			map[newOX][newOY] = this;
			map[oX][oY] = null;
			oX = newOX;
			oY = newOY;
		} else {
			switch (map[newOX][newOY].getMapToken()) {
			case 'Z':
				state = WalkerState.ASLLEP_FOREWER;
				break;
			case 'D':
				break;
			case 'C':
				state = WalkerState.ASLEEP;
				sllepingTime = Model.sleepingTime;
			}
		}
	}

	@Override
	public char getMapToken() {
		if (state == WalkerState.ASLEEP || state == WalkerState.ASLLEP_FOREWER) {
			return 'Z';
		} else {
			return 'D';
		}
	}

}
