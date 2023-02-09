package score;

import game.Config;

/**
 * This class implements the score type of Single Player Mode
 */
public class SinglePlayerScore implements Comparable<SinglePlayerScore> {
    private String player;
    private int score;
    private Config.GameDifficulty difficulty;
    private String preyType;
    private String date;

    /**
     * constructor
     * @param playerName
     * @param score
     * @param difficulty
     * @param prey
     * @param date
     */
    public SinglePlayerScore(String playerName, int score, Config.GameDifficulty difficulty, String prey, String date) {
        this.player = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.date = date;
        this.preyType = prey;
    }

    /**
     * The rule for sorting the score of Single Player Mode
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(SinglePlayerScore other) {
        if (this.score == other.score) {
            if (this.difficulty.difficultyOrdinal > other.difficulty.difficultyOrdinal) {
                return -1;
            } else {
                return 0;
            }
        } else if (this.score > other.score) {
            return -1;
        }
        return 1;
    }

    /**
     * convert final score to string
     * @return
     */
    @Override
    public String toString() {
        return player + "," + score + "," + difficulty + "," + preyType + ',' + date;
    }
}