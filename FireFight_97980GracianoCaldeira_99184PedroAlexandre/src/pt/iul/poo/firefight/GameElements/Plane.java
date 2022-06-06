package pt.iul.poo.firefight.GameElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.AbstractClasses.MovableElement;
import pt.iul.poo.firefight.Engine.GameEngine;
import pt.iul.poo.firefight.Interfaces.FireExtinguisher;
import pt.iul.poo.firefight.Interfaces.Updatable;

public class Plane extends MovableElement implements Updatable, FireExtinguisher {

	private static final int N_OF_TILES_TO_MOVE = 2;

	public Plane(Point2D position, Direction d) {
		super(position, d);
		Fire fire = (Fire) engine.getElement(position, ge -> ge instanceof Fire);
		if (fire != null)
			engine.removeImage(fire);
	}

	public String getName() {
		return "plane";
	}

	public int getLayer() {
		return 5;
	}

	public static Point2D getStartingPoint() {
		Set<Point2D> firePos = engine.getFirePos();
		int[] col = new int[GameEngine.GRID_WIDTH];
		int max = 0;
		int colWithMoreFires = 0;

		for (Point2D position : firePos) {
			int x = position.getX();
			col[x]++;
			if (col[x] > max) {
				max = col[x];
				colWithMoreFires = x;
			}
		}
		return new Point2D(colWithMoreFires, GameEngine.GRID_HEIGHT - 1);
	}

	public void update() {
		move(0);
	}

	public boolean move(int key) {
		Point2D pos = getPosition();
		List<ImageTile> waters = new ArrayList<>();

		for (int i = 0; i < N_OF_TILES_TO_MOVE; i++) {
			pos = pos.plus(getD().asVector());
			waters.add(new Water(pos.plus(Direction.DOWN.asVector()), Direction.DOWN));
			if (validPosition(pos)) {
				Fire fire = (Fire) engine.getElement(pos, ge -> ge instanceof Fire);
				if (fire != null) {
					fire.interact(this, getD());
				}
			} else
				continue;
		}
		engine.addImageTiles(waters);
		if (validPosition(pos)) {
			setPosition(pos);

			return true;
		} else
			engine.removeImage(this);
		return false;
	}

	protected boolean checkStuff(Point2D newPosition, Direction d) {
		return false;
	}

	public void extinct(Fire fire, Direction d) {
		engine.removeImage(fire);
		engine.addPoints(10);
	}
}