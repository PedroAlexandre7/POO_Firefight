package pt.iul.poo.firefight.AbstractClasses;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.Engine.GameEngine;
import pt.iul.poo.firefight.GameElements.Abies;
import pt.iul.poo.firefight.GameElements.Bulldozer;
import pt.iul.poo.firefight.GameElements.Bush;
import pt.iul.poo.firefight.GameElements.Dirt;
import pt.iul.poo.firefight.GameElements.Eucalyptus;
import pt.iul.poo.firefight.GameElements.Fire;
import pt.iul.poo.firefight.GameElements.FireTruck;
import pt.iul.poo.firefight.GameElements.Fireman;
import pt.iul.poo.firefight.GameElements.FiremanBot;
import pt.iul.poo.firefight.GameElements.FuelBarrel;
import pt.iul.poo.firefight.GameElements.Pine;
import pt.iul.poo.firefight.GameElements.Plane;

public abstract class GameElement implements ImageTile, Comparable<GameElement> {

	public static GameEngine engine = GameEngine.getInstance();

	public static void call(int key) {
		if (key == KeyEvent.VK_P)
			engine.addImageTile(create("Plane", Plane.getStartingPoint()));
	}

	public static GameElement create(String type, Point2D position) {
//		Point2D point = new Point2D(x, y);

		if (validPosition(position)) {
			Direction defaultDirection = Direction.LEFT;

			switch (type) {
			case "p":
				return new Pine(position);
			case "e":
				return new Eucalyptus(position);
			case "m":
				return new Bush(position);
			case "_":
				return new Dirt(position);
			case "a":
				return new Abies(position);
			case "b":
				return new FuelBarrel(position);
			case "Fire":
				return new Fire(position);
			case "Bulldozer":
				return new Bulldozer(position, defaultDirection);
			case "FireTruck":
				return new FireTruck(position, defaultDirection);
			case "Fireman":
				return new Fireman(position, defaultDirection); // tornar case insensitive se for preciso
			case "FiremanBot":
				return new FiremanBot(position, defaultDirection, true);
			case "Plane":
				return new Plane(position, Direction.UP);
			default:
				throw new IllegalArgumentException("O tipo '" + type + "' não existe ou não foi implementado");
			}

		} else
			throw new IllegalArgumentException("Endireita-te meu, a posição " + position
					+ " não é válida!\nAs posições têm que estar dentro de uma grelha de " + GameEngine.GRID_WIDTH
					+ " por " + GameEngine.GRID_HEIGHT);
	}

	public String toString() {
		return getName() + " " + getPosition();
	}

	public static boolean validPosition(Point2D p) {
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;
	}

	public int compareTo(GameElement element) {
		int thisX = getPosition().getX();
		int thisY = getPosition().getY();
		int thisLayer = getLayer();

		int thatX = element.getPosition().getX();
		int thatY = element.getPosition().getY();
		int thatLayer = element.getLayer();

		if (thisY < thatY)
			return -1;
		if (thisY > thatY)
			return 1;
		if (thisY == thatY && thisX < thatX)
			return -1;
		if (thisY == thatY && thisX > thatX)
			return 1;
		if (thisLayer < thatLayer)
			return -1;
		if (thisLayer > thatLayer)
			return 1;
		if (hashCode() < element.hashCode())
			return -1;
		if (hashCode() > element.hashCode())
			return 1;

		return 0;
	}

}
