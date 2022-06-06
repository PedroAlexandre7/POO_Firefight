package pt.iul.poo.firefight.GameElements;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.Vehicle;
import pt.iul.poo.firefight.Interfaces.FireExtinguisher;

public class FireTruck extends Vehicle implements FireExtinguisher {

	public FireTruck(Point2D position, Direction d) {
		super(position, d);
	}

	public String getName() {
		switch (getD()) {
		case RIGHT:
			return "firetruck_right";
		case UP:
			return "firetruck_right";
		default:
			return "firetruck_left";
		}
	}

	protected boolean checkStuff(Point2D newPosition, Direction d) {
		if (engine.getElement(newPosition, ge -> ge instanceof Vehicle) == null) {
			Fire it = (Fire) engine.getElement(newPosition, ge -> ge instanceof Fire);
			if (it == null)
				return true;
			else
				it.interact(this, d);
		}
		return false;
	}

	public void extinct(Fire fire, Direction d) {
		List<Point2D> newWaterPos = fire.getPosition().getFrontRect(d, 3, 2);
		for (Point2D p : newWaterPos)
			if (validPosition(p)) {
				engine.addImageTile(new Water(p, d));
				Fire f = (Fire) engine.getElement(p, ge -> ge instanceof Fire);
				if (f != null) {
					engine.removeImage(f);
					engine.addPoints(10);
				}
			}

	}

}
