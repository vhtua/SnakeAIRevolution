package game;

/**
 * This enumeration specifies the particular actions for the Snake based on its 2D xy-Coordinate
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);
    public final int x, y;
    public final Coordinate v;

    Direction (int x, int y) {
        this.x = x;
        this.y = y;
        this.v = new Coordinate(x,y);
    }
}
