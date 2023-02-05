package DefaultBotFrameWork.score;

class HeadToHead {

    private String bot1;
    private String bot2;
    private int score1;
    private int score2;
    public HeadToHead(String bot1, String bot2, int score1, int score2) {
        this.bot1 = bot1;
        this.bot2 = bot2;
        this.score1 = score1;
        this.score2 = score2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public String getBot1() {
        return bot1;
    }

    public String getBot2() {
        return bot2;
    }
}