package pt.iul.poo.firefight.AbstractClasses;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.Interfaces.Updatable;

public abstract class OnePlayElement extends GameElement implements Updatable {
	
	private Point2D position;

	public OnePlayElement(Point2D position) {
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public void update() {
		engine.removeImage(this);
	}

}
