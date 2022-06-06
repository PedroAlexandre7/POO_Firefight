package pt.iul.poo.firefight.AbstractClasses;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.GameElements.Fire;
import pt.iul.poo.firefight.GameElements.Water;
import pt.iul.poo.firefight.Interfaces.FireExtinguisher;
import pt.iul.poo.firefight.Interfaces.Updatable;

public abstract class FireFighter extends MovableElement implements FireExtinguisher, Updatable {

    protected FireFighter(Point2D position, Direction d) {
        super(position, d);
    }

    public int getLayer() {
        return 3;
    }

    public void extinct(Fire fire, Direction d) {
        engine.addImageTile(new Water(fire.getPosition(), d));
        engine.removeImage(fire);
        engine.addPoints(15);
    }

}