package pt.iul.poo.firefight.AbstractClasses;

import java.awt.event.KeyEvent;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.GameElements.Fireman;
import pt.iul.poo.firefight.Interfaces.Interactable;
import pt.iul.poo.firefight.Interfaces.Updatable;

public abstract class Vehicle extends MovableElement implements Updatable, Interactable {

	private boolean running = false;

	protected Vehicle(Point2D position, Direction d) {
		super(position, d);
	}

	public int getLayer() {
		return 2;
	}

	public void interact(GameElement ge, Direction d) {
		if (ge instanceof Fireman) {
			Fireman fman = (Fireman) ge;
			engine.removeImage(fman);
			setD(d);
			running = true;
			fman.enterVehicle();
		}
	}

	public void update() {
		int key = engine.getPressedKey();
		if (key == KeyEvent.VK_ENTER && running) {
			running = false;
			Fireman fireman = engine.getFireman();
			fireman.exitVehicle(getPosition());
			engine.addImageTile(fireman);
		} else if (running)
			move(key);
	}

}