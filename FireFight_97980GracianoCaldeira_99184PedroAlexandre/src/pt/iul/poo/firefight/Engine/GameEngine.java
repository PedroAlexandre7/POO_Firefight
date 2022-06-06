package pt.iul.poo.firefight.Engine;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.io.FileNotFoundException;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.poo.firefight.GameElements.Dirt;
import pt.iul.poo.firefight.GameElements.Fire;
import pt.iul.poo.firefight.GameElements.Fireman;
import pt.iul.poo.firefight.Interfaces.Updatable;
import pt.iul.poo.firefight.Scores.LevelsManager;
import pt.iul.poo.firefight.AbstractClasses.GameElement;
import pt.iul.poo.firefight.AbstractClasses.SurroundingElement;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private static GameEngine INSTANCE;

	private ImageMatrixGUI gui;
	private LevelsManager levelsM;

	private Map<Point2D, Fire> fireMap;
	private Set<Updatable> updatableSet;
	private Fireman fireman;
	private SurroundingElement[][] surroundingElements;
	private List<ImageTile> crap;

	private GameEngine() {
		gui = ImageMatrixGUI.getInstance();
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this);
		gui.go();

		try {
			levelsM = LevelsManager.getInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		updatableSet = new TreeSet<>();
		surroundingElements = new SurroundingElement[GRID_WIDTH][GRID_HEIGHT];
		fireMap = new HashMap<>();
		crap = new LinkedList<>();
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public void start() {
		readFile(levelsM.getFirstLevel());
	}

	public void nextLevel() {
		File nextLevelFile = levelsM.getNextLevel();
		if (nextLevelFile != null) {
			clearMap();
			readFile(nextLevelFile);
		}
	}

	private void clearMap() {
		fireman = null;
		fireMap.clear();
		updatableSet.clear();
		gui.clearImages();
	}

	private void readFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			createTerrain(sc);
			createMoreStuff(sc);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createTerrain(Scanner sc) {
		String str;
		for (int y = 0; y < GRID_HEIGHT && sc.hasNextLine(); y++) {
			str = sc.nextLine();
			for (int x = 0; x < GRID_WIDTH; x++)
				addImageTile(GameElement.create(String.valueOf(str.charAt(x)), new Point2D(x, y)));
		}
	}

	private void createMoreStuff(Scanner sc) {

		while (sc.hasNext()) {
			String s = sc.next();
			Point2D p = new Point2D(sc.nextInt(), sc.nextInt());
			GameElement nemGE = GameElement.create(s, p);

			if (nemGE instanceof Fire && getElement(p, ge -> ge instanceof Dirt) != null)
				System.err.println("Fogo não criado em cima de Dirt na posição: " + p);
			else
				addImageTile(nemGE);
		}
	}

	@Override
	public void update(Observed source) {

		if (fireMap.isEmpty()) {
			nextLevel();
			return;
		}

		List<Updatable> updatableList = new ArrayList<>(updatableSet);

		for (Updatable up : updatableList)
			up.update();

		fireman.update();
//		removeCrap();

		List<Fire> fireList = new ArrayList<>(fireMap.values());
		for (Fire fire : fireList)
			fire.update();

		removeCrap();

		levelsM.update();
		gui.update();
//		readGrid();
	}

	private void removeCrap() {
		for (ImageTile pieceOfCrap : crap) {
			updatableSet.remove(pieceOfCrap);
			if (pieceOfCrap instanceof Fire)
				fireMap.remove(pieceOfCrap.getPosition());
			gui.removeImage(pieceOfCrap);
		}
		crap.clear();
	}

	public void removeImage(ImageTile it) {
		updatableSet.remove(it);
		crap.add(it);
	}

	public void addPoints(int points) {
		levelsM.addPoints(points);
	}

	public void addImageTiles(List<ImageTile> firesToHandle) {
		for (ImageTile it : firesToHandle)
			addImageTile(it);
	}

	public void addImageTile(ImageTile imageTile) {

		if (imageTile instanceof Fire) {
			Fire fire = fireMap.put(imageTile.getPosition(), (Fire) imageTile);
			if (fire != null)
				gui.removeImage(fire);
		} else if (imageTile instanceof SurroundingElement)
			surroundingElements[imageTile.getPosition().getX()][imageTile.getPosition()
					.getY()] = (SurroundingElement) imageTile;
		else if (imageTile instanceof Fireman)
			fireman = (Fireman) imageTile;
//			firemanList.add((Fireman) imageTile)?
		else if (imageTile instanceof Updatable)
			updatableSet.add((Updatable) imageTile);
		gui.addImage(imageTile);
	}

	public int getPressedKey() {
		return gui.keyPressed();
	}

	public Set<Point2D> getFirePos() {
		return fireMap.keySet();
	}

	public int getNumFires() {
		return fireMap.size();
	}

	public ImageTile getElement(Point2D p, Predicate<ImageTile> pre) {

		ImageTile element;

		// getFireman at p
		element = fireman;
		if (pre.test(element) && fireman.getPosition().equals(p))
			return element;

		// getTerrain
		if (GameElement.validPosition(p)) {
			element = surroundingElements[p.getX()][p.getY()];
			if (pre.test(element))
				return element;
		}

		// getFire
		element = fireMap.get(p);
		if (pre.test(element))
			return element;

		// getUpdatable
		for (Updatable up : updatableSet)
			if (pre.test(up) && up.getPosition().equals(p))
				return up;

		return null;
	}

	public Fireman getFireman() {
		return fireman;
	}

	public void enterVehicle() {
		gui.removeImage(fireman);
	}

	public void readGrid() { // mostra a grid
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < surroundingElements.length; y++) {
			s.append("\n\n");
			for (int x = 0; x < surroundingElements[0].length; x++)
				if (surroundingElements[x][y] != null) {
					String space = (surroundingElements[x][y].getName().length() >= 5) ? "\t" : "\t\t";
					s.append(surroundingElements[x][y].getName() + space);
				} else
					s.append("\t");
		}
		System.out.println(s);
	}
}
