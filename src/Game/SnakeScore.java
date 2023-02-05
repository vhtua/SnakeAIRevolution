package Game;

class SnakeScore implements Comparable<SnakeScore> {

    private String player;
    private int score;
    private Config.GameDifficulty difficulty;
    private String preyType;
    private String date;
    public SnakeScore(String playerName, int score, Config.GameDifficulty difficulty, String prey, String date) {
        this.player = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.date = date;
        this.preyType = prey;
    }

    @Override
    public int compareTo(SnakeScore other) {

        if (this.score == other.score && this.difficulty.difficultyOrdinal > other.difficulty.difficultyOrdinal) {
            return -1;
        } else if (this.score > other.score) {
            return -1;
        } else {
            return 1;
        }
    }


    @Override
    public String toString() {
        return player + "," + score + "," + difficulty + "," + preyType + ',' + date;
    }
}