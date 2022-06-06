package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.OnePlayElement;

public class Explosion extends OnePlayElement {

	public Explosion(Point2D position) {
		super(position);
	}

	public String getName() {
		return "explosion";
	}

	public int getLayer() {
		return 1;
	}

}