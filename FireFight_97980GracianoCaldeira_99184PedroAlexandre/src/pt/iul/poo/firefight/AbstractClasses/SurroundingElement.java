package pt.iul.poo.firefight.AbstractClasses;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.Interfaces.Burnable;

public abstract class SurroundingElement extends GameElement implements Burnable {

	private Point2D position;
	private final int PERCENTAGE_OF_PROPAGATION;
	private int burnedPoints;

	protected SurroundingElement(Point2D position, int perc_OF_PROPAGATION, int defaultBurnedPoints) {
		super();
		this.PERCENTAGE_OF_PROPAGATION = perc_OF_PROPAGATION;
		this.position = position;
		burnedPoints = defaultBurnedPoints;
	}

	public Point2D getPosition() {
		return position;
	}

	public int getLayer() {
		return 0;
	}

	public boolean isBurned() {
		if (burnedPoints < 0)
			System.err.println(this + " foi queimado demasiadas vezes, os pontos são: " + burnedPoints);
		return burnedPoints == 0;
	}

	public boolean spreadsTo() {
		return (Math.random() * 101) <= PERCENTAGE_OF_PROPAGATION && !isBurned();
	}

	public boolean burn() {
		if (!isBurned())
			burnedPoints--;
		return isBurned();
	}

	protected int getBurnedPoints() {
		return burnedPoints;
	}

}
