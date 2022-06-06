package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;
import pt.iul.poo.firefight.AbstractClasses.Vehicle;
import pt.iul.poo.firefight.Interfaces.Burnable;

public class Bulldozer extends Vehicle {

	public Bulldozer(Point2D position, Direction d) {
		super(position, d);
	}

	public String getName() {
		switch (getD()) {
		case UP:
			return "bulldozer_up";
		case DOWN:
			return "bulldozer_down";
		case LEFT:
			return "bulldozer_left";
		default:
			return "bulldozer_right";
		}
	}

	protected boolean checkStuff(Point2D newPosition, Direction d) {
		if (engine.getElement(newPosition, ge -> ge instanceof Fire || ge instanceof Vehicle) == null) {
			Burnable it = (Burnable) engine.getElement(newPosition, ge -> ge instanceof SurroundingElement);
			if (!(it instanceof Dirt)) {
				engine.removeImage(it);
				engine.addImageTile(create("_", newPosition));
				engine.addPoints(5);
			}
			return true;
		}
		return false;
	}
}