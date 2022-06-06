package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class Pine extends SurroundingElement {

	public Pine(Point2D position) {
		super(position, 5, 10);
	}

	public String getName() {
		if (isBurned())
			return "burntpine";
		return "pine";
	}
}