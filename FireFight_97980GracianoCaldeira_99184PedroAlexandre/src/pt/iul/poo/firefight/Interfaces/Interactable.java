package pt.iul.poo.firefight.Interfaces;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.poo.firefight.AbstractClasses.GameElement;

public interface Interactable extends ImageTile {

	public void interact(GameElement inter, Direction d);

}