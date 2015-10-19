package walkers.mapobject;

/**
 * This class simulates person behavior.
 * 
 * @author Anton Milenin
 */
public abstract class Person {
	protected int oX, oY;
	
	protected MapObject[][] map;

	/**
	 * Class constructor.
	 * 
	 * @param map
	 *            array that shows positions of all objects on the field
	 */
	Person(MapObject[][] map){
		this.map = map;		
	}

	/**	
	 * @return oX coordinate of the person
	 */
	public int getOX() {
		return oX;
	}

	/**	
	 * @return oY coordinate of the person
	 */
	public int getOY() {
		return oY;
	}

	/**
	 * This method is called to simulate persons' movement.
	 */
	abstract void move();
}
