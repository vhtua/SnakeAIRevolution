package game;
import java.awt.*;

/**
 * This class implements the attributes of the Snake
 */
public class Snake {
    private final Coordinate xyCoor;
    public Snake(int xCoor, int yCoor) {
        this.xyCoor = new Coordinate(xCoor, yCoor);
    }

    /**
     * initialize each body block (color, shape) of the Snake
     * @param g     graphics
     * @param c     snake body color
     */
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(xyCoor.x * Config.SQUARE_SIZE, xyCoor.y * Config.SQUARE_SIZE,
                                    Config.SQUARE_SIZE, Config.SQUARE_SIZE);
    }

    /**
     * initialize the head block (color, shape) of the Snake
     * @param g     graphics
     * @param c     snake head color
     */
    public void drawHead(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(xyCoor.x * Config.SQUARE_SIZE + 2, xyCoor.y * Config.SQUARE_SIZE + 2,
                                    Config.SQUARE_SIZE - 4, Config.SQUARE_SIZE - 4);
    }

    public int getxCoor() {return xyCoor.x;}
    public int getyCoor() {return xyCoor.y;}
    public Coordinate getXyCoor() {return xyCoor;}

}
