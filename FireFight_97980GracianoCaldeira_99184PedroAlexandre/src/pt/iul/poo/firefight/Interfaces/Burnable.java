package pt.iul.poo.firefight.Interfaces;

import pt.iul.ista.poo.gui.ImageTile;

public interface Burnable extends ImageTile{

	public boolean spreadsTo();

	public boolean isBurned();
}
