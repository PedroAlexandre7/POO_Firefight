package pt.iul.poo.firefight.Scores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class LevelsManager {

	private static final int PLAYER_RANKING = 5;

	private static LevelsManager INSTANCE;

	private ImageMatrixGUI gui;

	private List<File> levels;
	private int currentLevel = 0;
	private PlayerScore playerS;

	public static LevelsManager getInstance() throws FileNotFoundException {
		if (INSTANCE == null)
			INSTANCE = new LevelsManager();
		return INSTANCE;
	}

	private LevelsManager() throws FileNotFoundException {
		gui = ImageMatrixGUI.getInstance();
		File[] dir = (new File("levels")).listFiles();
		if (dir.length == 0)
			throw new FileNotFoundException("No levels to load");
		levels = Arrays.asList(dir);

		String str = JOptionPane.showInputDialog("INPUT USERNAME");
		if (str == null)
			throw new NullPointerException("olha meste");
		while (str.equals("") || str.equals("\t") || str.contains(" "))
			str = JOptionPane.showInputDialog("INPUT USERNAME", "DefaultUser");

		playerS = new PlayerScore(str);
	}

	public File getFirstLevel() {
		
		gui.setName(levelName(levels.get(0)));
		return levels.get(currentLevel);
	}

	public File getNextLevel() {

		if (currentLevel < levels.size()) {
			saveInLeaderBoard(playerS);

			gui.setMessage(" A tua maior pontuação no nível " + levelName(levels.get(currentLevel)) + ": "
					+ playerS.getScore());
			playerS.setScore(0);
		}

		if (++currentLevel < levels.size()) {
			gui.setName(levelName(levels.get(currentLevel)));
			return levels.get(currentLevel);
		} else
			return null;
	}

	private static String levelName(File file) {
		String[] s = file.getName().split("\\.");
		return s[0];
	}

	private void saveInLeaderBoard(PlayerScore playerS) {

		String curLevelName = levelName(levels.get(currentLevel));
		File leaderboardFile = new File("levelsScores\\" + curLevelName + "Scores.txt");

		List<PlayerScore> psList = giveLeaderScoresFile_ChangingPlayerSIfNeeded(leaderboardFile);

		psList.add(playerS);
		psList.sort((ps1, ps2) -> ps2.getScore() - ps1.getScore());

		writeLeaderScoresFile(leaderboardFile, psList);
	}

	private void writeLeaderScoresFile(File leaderboardFile, List<PlayerScore> psList) {
		try {
			PrintWriter pw = new PrintWriter(leaderboardFile);
			for (int i = 0; i < PLAYER_RANKING && i < psList.size(); i++) {
				String name = psList.get(i).getName();
				int score = psList.get(i).getScore();
				pw.println(name + "\t" + score);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't save");
		}
	}

	private List<PlayerScore> giveLeaderScoresFile_ChangingPlayerSIfNeeded(File leaderboardFile) {
		List<PlayerScore> psList = new LinkedList<>();
		try {

			Scanner sc = new Scanner(leaderboardFile);
			while (sc.hasNext()) {

				String name = sc.next();
				int score = sc.nextInt();

				if (!name.equals(playerS.getName())) {
					PlayerScore ps = new PlayerScore(name, score);
					psList.add(ps);
				} else if (name.equals(playerS.getName()) && score > playerS.getScore()) {
					playerS.setScore(score);
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find the file");
		}
		return psList;
	}

	public void addPoints(int points) {
		int newScore = playerS.getScore() + points;
		if (newScore >= 0)
			playerS.setScore(newScore);
	}

	public void update() {
		gui.setStatusMessage(" A tua pontuação: " + playerS.getScore());
	}
}
