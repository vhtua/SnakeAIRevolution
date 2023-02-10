package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the whole body and status of the snake
 */
public class SnakeAbstract implements KeyListener {
    private final List<Snake> snakeList;
    private final Color snakeColor;
    private Direction snakeDir;
    private boolean aliveStatus;

    /**
     * initialize the Snake and its Body, Color, Status, position
     * @param snake         input snake
     * @param snakeDir      current direction of the snake
     * @param snakeColor    current snake body color
     */
    public SnakeAbstract(Snake snake, Direction snakeDir, Color snakeColor) {
        List<Snake> snakeList = new ArrayList<>();
        snakeList.add(snake);
        this.snakeList = snakeList;
        this.snakeDir = snakeDir;
        this.snakeColor = snakeColor;
        this.aliveStatus = true;

        this.addTail(new Snake(Config.boundSquare+1,Config.boundSquare));
        this.addTail(new Snake(Config.boundSquare+2,Config.boundSquare));
    }

    /**
     * get coordinate of the Snake's Head
     * @param xyCoor            current coordinate of the snake head
     * @param target            the apple
     * @param currentDirection  the direction that the snake wants to change
     * @return                  new coordinate for the snake head
     */
    public Coordinate getHeadCoor(Coordinate xyCoor, Apple target, Direction currentDirection) {
        return xyCoor.moveTo(currentDirection);
    }

    /**
     * Key controller for the Snake
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (Config.moveAtleastASpace) {
            if (keyCode == KeyEvent.VK_RIGHT && !(this.getSnakeDir() == Direction.LEFT)) {
                this.setSnakeDir(Direction.RIGHT);
            } else if (keyCode == KeyEvent.VK_LEFT && !(this.getSnakeDir() == Direction.RIGHT)) {
                this.setSnakeDir(Direction.LEFT);
            } else if (keyCode == KeyEvent.VK_UP && !(this.getSnakeDir() == Direction.DOWN)) {
                this.setSnakeDir(Direction.UP);
            } else if (keyCode == KeyEvent.VK_DOWN && !(this.getSnakeDir() == Direction.UP)) {
                this.setSnakeDir(Direction.DOWN);
            }
            Config.moveAtleastASpace = false;
        }
    }

    /**
     * increase the size of the snake
     * @param snake the current snake
     */
    public void addTail(Snake snake) {
        this.snakeList.add(snake);
    }

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public Direction getSnakeDir() {
        return snakeDir;
    }

    /**
     * get the current direction of the Snake
     * @param snakeDir the current direction of the snake
     */
    public void setSnakeDir(Direction snakeDir) {this.snakeDir = snakeDir;}

    /**
     * get the status of the Snake
     * @return the current status of the snake
     */
    public boolean isAliveStatus() {return aliveStatus;}

    /**
     * set the status for the Snake
     * @param aliveStatus the current status of the snake
     */
    public void setAliveStatus(boolean aliveStatus) {this.aliveStatus = aliveStatus;}

    /**
     * increase the size of the Snake if it eats the Apple
     * @param target the apple
     */
    public void movement(Apple target) {
        Snake sHead = snakeList.get(snakeList.size() - 1);
        Direction currentDirection = snakeDir;
        Coordinate newCoordinate = getHeadCoor(sHead.getXyCoor(), target, currentDirection);
        Snake s = new Snake(newCoordinate.x, newCoordinate.y);
        snakeList.add(s);
        Config.moveAtleastASpace = true;
    }

    /**
     * paint the Snake on the frame
     * @param g graphics
     */
    public void buildSnake(Graphics g) {
        for (Snake snake : snakeList) {
            snake.draw(g, this.snakeColor);// build body
        }
        // build head
        snakeList.get(snakeList.size()-1).drawHead(g, Color.WHITE);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

