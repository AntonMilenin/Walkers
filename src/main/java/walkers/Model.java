package walkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class models field with walkers, post and pub. It also contains global
 * setup variables.
 * 
 * @author Anton Milenin
 */
public class Model {
	/**
	 * Maximum index of oX variables of walkers. It equals to field size - 1.
	 */
	public final static int maxOX = 14;
	/**
	 * Maximum index of oY variables of walkers. It equals to field height - 1.
	 */
	public final static int maxOY = 14;
	/**
	 * oX coordinate of the post.
	 */
	public final static int postOX = 7;

	/**
	 * oY coordinate of the post.
	 */
	public final static int postOY = 7;

	/**
	 * Number of turns for walker to sleep after running into a post. 
	 */
	public final static int sleepingTime = 5;

	/**
	 * oX coordinate of the pub. 
	 */
	public final static int pubOx = 9; // we assume that pubOy == -1 to not
										// spoil Walker implementation by
										// excessive generalization
	/**
	 * Number of turns of simulation. 
	 */
	public final static int finalTurn = 500;
	/**
	 * Periodicity of walker appearance. 
	 */
	public final static int newWalkerDelay = 20;
	
	private Walker[][] map = new Walker[maxOX + 1][maxOY + 1];
	private Random random = new Random();

	private int turn = 0;
	private List<Walker> activeWalkers = new ArrayList<>();

	/**
	 * Simulates walkers behavior.
	 */
	public void simulate() {
		while (turn < finalTurn) {
			if ((turn % newWalkerDelay) == 0) {
				activeWalkers.add(new Walker(map, random));
			}
			Iterator<Walker> iterator = activeWalkers.iterator();
			while (iterator.hasNext()) {
				Walker walker = iterator.next();
				walker.move();
				if (walker.getState() == WalkerState.ASLLEP_FOREWER)
					iterator.remove();
			}
			drawMap();
			turn++;
		}
	}

	/**
	 * Simulates walkers behavior.
	 */
	private void drawMap() {
		for (int i = 0; i < pubOx; i++) {
			System.out.print(" ");
		}
		System.out.print("T");
		System.out.println("");
		for (int y = 0; y <= maxOY; y++) {
			for (int x = 0; x <= maxOX; x++) {
				if (x == postOX && y == postOY) {
					System.out.print("C");
				} else if (map[x][y] == null) {
					System.out.print(".");
				} else if (map[x][y].getState() == WalkerState.WALKING) {
					System.out.print("D");
				} else {
					System.out.print("Z");
				}
			}
			System.out.println("");
		}
	}

	public static void main(String[] argv) {
		new Model().simulate();
	}
}
