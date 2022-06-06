package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class Eucalyptus extends SurroundingElement {

	public Eucalyptus(Point2D position) {
		super(position, 10, 5);
	}

	public String getName() {
		if (isBurned())
			return "burnteucalyptus";
		return "eucalyptus";
	}

}