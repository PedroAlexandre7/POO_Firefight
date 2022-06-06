package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class Bush extends SurroundingElement {

	public Bush(Point2D position) {
		super(position, 15, 3);
	}

	public String getName() {
		if (isBurned())
			return "burntbush";
		return "bush";
	}

}