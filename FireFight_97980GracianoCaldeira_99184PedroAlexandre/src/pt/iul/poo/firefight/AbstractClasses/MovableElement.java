package pt.iul.poo.firefight.AbstractClasses;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Direction;

public abstract class MovableElement extends GameElement {

	private Direction d;
	private Point2D position;

	protected MovableElement(Point2D position, Direction d) {
		super();
		this.d = d;
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public boolean move(Direction d) {// é boolean caso seja preciso saber se se moveu

		boolean moving = false;
		Point2D newPosition = getPosition().plus(d.asVector());
		this.d = d;
		
		if (validPosition(newPosition))
			if (checkStuff(newPosition, d)) {
				moving = true;
				setPosition(newPosition);
			}

		return moving;
	}

	public boolean move(int key) {

		boolean moving = false;
		if (Direction.isDirection(key)) {
			d = Direction.directionFor(key);
			moving = move(d);
		}
		return moving;
	}

	protected abstract boolean checkStuff(Point2D newPosition, Direction d);

	public Direction getD() {
		return d;
	}

	public void setD(Direction d) {
		this.d = d;
	}
}
