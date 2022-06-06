package pt.iul.poo.firefight.Interfaces;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.poo.firefight.GameElements.Fire;

public interface FireExtinguisher extends ImageTile {

	public void extinct(Fire fire, Direction d);

}