package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class Abies extends SurroundingElement {

	public Abies(Point2D position) {
		super(position, 5, 20);
	}

	public String getName() {
		if (isBurned())
			return "burntabies";
		return "abies";
	}

}
