package pt.iul.poo.firefight.GameElements;

import java.util.Set;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.Engine.GameEngine;
import pt.iul.poo.firefight.AbstractClasses.FireFighter;

public class FiremanBot extends FireFighter {

	private boolean smart;

	public FiremanBot(Point2D position, Direction d, boolean smart) {
		super(position, d);
		this.smart = smart;
	}

	public void update() {
		if (smart) {
			Direction dToGo = directionToFire();
			if (dToGo != null)
				move(directionToFire());
		} else
			move(Direction.random());
	}

	private Direction directionToFire() {

		int minD = (int) Math.sqrt(Math.pow(GameEngine.GRID_HEIGHT, 2) + Math.pow(GameEngine.GRID_HEIGHT, 2)) + 1;
		Set<Point2D> firePos = engine.getFirePos();
		Point2D closestFP = null;

		for (Point2D p : firePos) {
			int d = getPosition().distanceTo(p);
			if (d < minD && d > 0) {
				minD = d;
				closestFP = p;
				if (d < 2)
					break;
			}
		}

		if (closestFP == null)
			return null;
		return getPosition().directionTo(closestFP);

	}

	public String getName() {
		switch (getD()) {
		case RIGHT:
			return "firemanbot_right";
		case UP:
			return "firemanbot_right";
		default:
			return "firemanbot_left";
		}
	}

	protected boolean checkStuff(Point2D newPosition, Direction d) {
		ImageTile it = engine.getElement(newPosition, ge -> (ge instanceof Fire) || ge instanceof FireFighter);
		if (it == null) {
			setPosition(newPosition);
			return true;
		} else if (it instanceof Fire)
			((Fire) it).interact(this, d);
		return false;
	}

}