package Game;
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);
    public int x, y;
    public Coordinate v;
    Direction (int x, int y) {
        this.x = x;
        this.y = y;
        this.v = new Coordinate(x,y);
    }


}
