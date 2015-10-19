package walkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import walkers.mapobject.Lamp;
import walkers.mapobject.MapObject;
import walkers.mapobject.Policeman;
import walkers.mapobject.Post;
import walkers.mapobject.Walker;
import walkers.mapobject.WalkerState;

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
	 * oY coordinate of the police station.
	 */
	public final static int policeStationOy = 3;

	/**
	 * oX coordinate of the lamp.
	 */
	public final static int lampOx = 10;

	/**
	 * oY coordinate of the lamp.
	 */
	public final static int lampOy = 3;

	/**
	 * Number of turns of simulation.
	 */
	public final static int finalTurn = 500;

	/**
	 * Periodicity of walker appearance.
	 */
	public final static int newWalkerDelay = 20;

	/**
	 * This value corresponds to chance to loose a bottle. Actual chance is
	 * 1/this value.
	 */
	public final static int bottleLossCahnce = 30;

	/**
	 * Radius of lamps' illumination
	 */
	public final static int lightRadius = 3;

	private MapObject[][] map;
	private Random random = new Random();
	private int turn = 0;
	private List<Walker> activeWalkers = new ArrayList<>();
	private Stack<Walker> illuminatedWalkers = new Stack<>();
	private Policeman policeman; 

	/**
	 * Class constructor.
	 */
	public Model() {
		map = new MapObject[maxOX + 1][maxOY + 1];
		map[postOX][postOY] = new Post();
		map[lampOx][lampOy] = new Lamp();
		policeman = new Policeman(map, illuminatedWalkers); 
	}

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
				if (walker.getState() == WalkerState.ASLEEP || walker.getState() == WalkerState.LYING) {
					if (Math.abs(walker.getOX() - lampOx) + Math.abs(walker.getOY() - lampOy) <= lightRadius)
						illuminatedWalkers.add(walker);
					iterator.remove();
				}
			}
			policeman.move();
			drawMap();
			turn++;
		}
	}

	/**
	 * Simulates walkers behavior.
	 */
	private void drawMap() {
		if(policeman.getOX()==0||policeman.getOX()==14)
			return;
		for (int i = 0; i < pubOx; i++) {
			System.out.print(" ");
		}
		System.out.print("T");
		System.out.println("");
		for (int y = 0; y <= maxOY; y++) {
			for (int x = 0; x <= maxOX; x++) {
				if (map[x][y] == null) {
					System.out.print('.');
				} else {
					System.out.print(map[x][y].getMapToken());
				}
			}
			if (y==3)
				System.out.print('S');
			System.out.println("");
		}
	}

	public static void main(String[] argv) {
		new Model().simulate();
	}
}
