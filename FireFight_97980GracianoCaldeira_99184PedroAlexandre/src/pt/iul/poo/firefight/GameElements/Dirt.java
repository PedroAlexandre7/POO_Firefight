package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class Dirt extends SurroundingElement {

	public Dirt(Point2D position) {
		super(position, 0, 0);
	}

	public String getName() {
		return "dirt";
	}

	public boolean spreadsTo() {
		return false;
	}

}