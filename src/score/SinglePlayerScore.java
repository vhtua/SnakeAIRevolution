package score;

import game.Config;

class SinglePlayerScore implements Comparable<SinglePlayerScore> {

    private String player;
    private int score;
    private Config.GameDifficulty difficulty;
    private String preyType;
    private String date;
    public SinglePlayerScore(String playerName, int score, Config.GameDifficulty difficulty, String prey, String date) {
        this.player = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.date = date;
        this.preyType = prey;
    }

    @Override
    public int compareTo(SinglePlayerScore other) {
        if (this.score == other.score && this.difficulty.difficultyOrdinal > other.difficulty.difficultyOrdinal) {
            return -1;
        } else if (this.score > other.score) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return player + "," + score + "," + difficulty + "," + preyType + ',' + date;
    }
}