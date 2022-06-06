
package pt.iul.poo.firefight.GameElements;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.OnePlayElement;

public class Water extends OnePlayElement {

	private Direction d;

	public Water(Point2D position, Direction d) {
		super(position);
		this.d = d;
	}

	public String getName() {
		switch (d) {
		case UP:
			return "water_up";
		case DOWN:
			return "water_down";
		case LEFT:
			return "water_left";
		default:
			return "water_right";
		}
	}

	public int getLayer() {
		return 2;
	}

}
