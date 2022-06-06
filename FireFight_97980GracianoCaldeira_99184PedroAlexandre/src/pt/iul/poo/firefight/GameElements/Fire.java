package pt.iul.poo.firefight.GameElements;

import java.util.List;

import pt.iul.poo.firefight.AbstractClasses.GameElement;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;
import pt.iul.poo.firefight.Interfaces.Burnable;
import pt.iul.poo.firefight.Interfaces.FireExtinguisher;
import pt.iul.poo.firefight.Interfaces.Interactable;
import pt.iul.poo.firefight.Interfaces.Updatable;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElement implements Interactable, Updatable {

	private Point2D position;

	public Fire(Point2D position) {
		super();
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public String getName() {
		return "fire";
	}

	public int getLayer() {
		return 1;
	}

	public void interact(GameElement ge, Direction d) {
		if (ge instanceof FireExtinguisher)
			((FireExtinguisher) ge).extinct(this, d);
	}

	public void update() {
		if (!burn())
			propagate();
	}

	public void propagate() {
		List<Point2D> points = getPosition().getNeighbourhoodPoints();
		for (Point2D point : points)
			if (fireSpreads(point))
				engine.addImageTile((Fire) create("Fire", point));

	}

	private boolean fireSpreads(Point2D newPoint) {
		if (validPosition(newPoint)) {
			Burnable burnable = (Burnable) engine.getElement(newPoint, ge1 -> ge1 instanceof Burnable);
			ImageTile ge = engine.getElement(newPoint, ge2 -> !(ge2 instanceof Burnable) && ge2 != null);
			if (burnable.spreadsTo() && ge == null)
				return true;
		}
		return false;
	}

	public boolean burn() {
		SurroundingElement terrain = (SurroundingElement) engine.getElement(getPosition(),
				ge -> ge instanceof SurroundingElement);
		if (terrain.burn()) {
			engine.removeImage(this);
			engine.addPoints(-5);
			return true;
		}
		return false;
	}
}