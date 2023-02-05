package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

//<--- THIS IS RESPONSIBLE FOR A WHOLE BODY + ALIVE STATUS OF A SNAKE
public class SnakeAbstract implements KeyListener {
    private List<Snake> snakeList;
    private Color snakeColor;
    private Direction snakeDir;
    private boolean aliveStatus;

    public SnakeAbstract(Snake snake, Direction snakeDir, Color snakeColor) {
        List<Snake> snakeList = new ArrayList<Snake>();
        snakeList.add(snake);
        this.snakeList = snakeList;
        this.snakeDir = snakeDir;
        this.snakeColor = snakeColor;
        this.aliveStatus = true;

        this.addTail(new Snake(Config.boundSquare+1,Config.boundSquare));
        this.addTail(new Snake(Config.boundSquare+2,Config.boundSquare));
    }

    public Coordinate getsHeadNewCoor(Coordinate xyCoor, Apple target, Direction currentDirection) {
        return xyCoor.moveTo(currentDirection);
    }

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



    public void addTail (Snake snake) {
        this.snakeList.add(snake);
    }

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public Direction getSnakeDir() {
        return snakeDir;
    }

    public void setSnakeDir(Direction snakeDir) {
        this.snakeDir = snakeDir;
    }

    public boolean isAliveStatus() {
        return aliveStatus;
    }

    public void setAliveStatus(boolean aliveStatus) {
        this.aliveStatus = aliveStatus;
    }

    public void movement(Apple target) {

        Snake sHead = snakeList.get(snakeList.size() - 1);

        Direction currentDirection = snakeDir;

        Coordinate newCoordinate = getsHeadNewCoor(sHead.getXyCoor(), target, currentDirection);
        Snake s = new Snake(newCoordinate.x, newCoordinate.y);
        snakeList.add(s);

        Config.moveAtleastASpace = true;
    }

    public void buildSnake(Graphics g) {
        for (int i = 0; i < snakeList.size(); i++) {
            snakeList.get(i).draw(g, this.snakeColor);// build body
        }

        // build head
        snakeList.get(snakeList.size()-1).drawHead(g, Color.WHITE);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

