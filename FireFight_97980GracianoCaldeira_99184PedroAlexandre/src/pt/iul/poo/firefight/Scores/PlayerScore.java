package pt.iul.poo.firefight.Scores;

public class PlayerScore {

	private final String nickname;
	private int score;

	public static PlayerScore givePlayer(String name, String score) {
		PlayerScore p = new PlayerScore(name);
		p.setScore(Integer.parseInt(score));
		return p;
	}

	public PlayerScore(String name, int score) {
		this.nickname = name;
		this.score = score;
	}

	public PlayerScore(String name) {
		this(name, 0);
	}

	public String getName() {
		return nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String toString() {
		return "nickname: " + getName() + "\tscore: " + getScore();
	}
}