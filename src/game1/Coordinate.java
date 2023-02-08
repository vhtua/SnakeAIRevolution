package game1;

/**
 * This class implements 2D xy-Coordinates for the Snake of the Single Player Mode
 */
public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate add(Coordinate b) {
        return new Coordinate(this.x + b.x, this.y + b.y);
    }
    public Coordinate moveTo (Direction d) {
        return this.add(d.v);
    }
}
