package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.poo.firefight.Interfaces.Interactable;
import pt.iul.poo.firefight.AbstractClasses.FireFighter;

public class Fireman extends FireFighter
//implements Interactant
{

	private boolean insideVehicle;

	public Fireman(Point2D position, Direction d) {
		super(position, d);
	}

	public void update() {
		int key = engine.getPressedKey();
		if (!insideVehicle)
			move(key);
		call(key);

	}

	public void enterVehicle() {
		insideVehicle = true;
	}

	public void exitVehicle(Point2D newPoint) {
		insideVehicle = false;
		setPosition(newPoint);
	}

	@Override
	public String getName() {
		switch (getD()) {
		case RIGHT:
			return "fireman_right";
		case UP:
			return "fireman_right";
		default:
			return "fireman_left";
		}

	}

	@Override
	protected boolean checkStuff(Point2D newPosition, Direction d) {
		Interactable it = (Interactable) engine.getElement(newPosition, ge -> ge instanceof Interactable);
		if (it == null) {
			setPosition(newPosition);
			return true;
		} else
			it.interact(this, d);
		return false;
	}

}