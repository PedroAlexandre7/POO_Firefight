package pt.iul.poo.firefight.GameElements;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class FuelBarrel extends SurroundingElement {

	public FuelBarrel(Point2D position) {
		super(position, 90, 3);
	}

	public String getName() {
		if (isBurned())
			return "burntfuelbarrel";
		return "fuelbarrel";
	}

	public boolean burn() {
		if (super.burn())
			explode();
		return isBurned();
	}

	private void explode() {
		engine.addImageTile(new Explosion(getPosition()));
		List<Point2D> newPList = getPosition().getWideNeighbourhoodPoints();
		for (Point2D p : newPList) {
			if (validPosition(p)
//					&& b != null
			)
				engine.addImageTile(create("Fire", p));
		}
	}
}
