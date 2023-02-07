package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameMap extends JPanel implements ActionListener, KeyListener {
    private JFrame frame = new JFrame();

    // Default Settings for the Snake
    private Snake classic = new Snake(Config.boundSquare,Config.boundSquare);
    private SnakeAbstract aSnake;

    // Apple attributes
    private Apple apple;
    private final int appleSize = Config.SQUARE_SIZE;
    private boolean appleAppear;
    private int appleTimer; //moves
    private Timer timer;
    private Random r;

    // Game stat
    private Integer score;
    private Integer bestscore = 0;
    private String playerName;

    // Game Config
    private Config gameConfig;

    // ============================= METHODS ======================================

    /**
     * start creating the Map and the Snake
     * @throws IOException
     */
    public GameMap() throws IOException {
        // init the game Config
        gameConfig = new Config();
        gameConfig.loadAllConfig();

        setFocusable(true);
        setPreferredSize(new Dimension(Config.WIDTH, Config.HEIGHT));

        aSnake = new SnakeAbstract(classic, Direction.RIGHT, gameConfig.snakeColor);
        addKeyListener(aSnake);

        // init the score
        score = 0;
        addKeyListener(this);//for space

        r = new Random();
        appleAppear = false;
        start();

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setTitle("Snake Game");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * start the gameplay
     */
    public void start() {
        timer = new Timer(gameConfig.DELAY, this);
        timer.start();
    }

    /**
     * stop the gameplay
     */
    public void stop() {
        Config.running = false;
        timer.stop();
    }

    /**
     * while the game is running, the frame is being updated
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Config.running) {
            repaint();
        }
    }

    /**
     * spawn new Apple position after it is eaten by a snake or after an amount of time
     * @param g
     */
    public void newApple(Graphics g) {
        if (!appleAppear || appleTimer == 1) {
            //it disappears or 20secs passed
            appleTimer = gameConfig.appleTimer; //moves
            int[] appleCoor = findNonOccupiedAppleSpace();
            int xApple = appleCoor[0];
            int yApple = appleCoor[1];
            // find random unoccupied coordinate

            apple = new Apple(xApple, yApple, appleSize);
            appleAppear = true;
        } else {
            appleTimer--;
        }
        apple.drawApple(g);
    }

    /**
     * return the proper position for the apple to respawn (on the bound of the game ground and not on the snake)
     * @return
     */
    public int[] findNonOccupiedAppleSpace() {
        int[] coor = new int[2];
        while (true) {
            boolean newAppleCoor = true;
            int xApple = r.nextInt(Config.WIDTH / appleSize);       // bound for xApple
            int yApple = r.nextInt(Config.HEIGHT / appleSize);      // bound for yApple
            int n = Config.HEIGHT/Config.SQUARE_SIZE - Config.boundSquare;
            for (int i = 0; i < aSnake.getSnakeList().size() ; i++) {
                if (xApple == aSnake.getSnakeList().get(i).getxCoor() &&
                        yApple == aSnake.getSnakeList().get(i).getyCoor()) {
                    newAppleCoor = false;   //apple on snake!
                    break;
                }
                if ((xApple < Config.boundSquare || xApple > Config.WIDTH/Config.SQUARE_SIZE - Config.boundSquare-1) ||
                        (yApple < Config.boundSquare || yApple > Config.HEIGHT/Config.SQUARE_SIZE - Config.boundSquare-1)) {
                    newAppleCoor = false;   //apple out of bound!
                    break;
                }
            }

            if (newAppleCoor) {
                coor[0] = xApple;
                coor[1] = yApple;
                break;
            }
        }
        return coor;
    }

    /**
     * check the status of the snake consecutively while the gameplay is running
     * @param snake
     * @param g
     */
    public void checkSnake(SnakeAbstract snake, Graphics g) {
        if (!snake.isAliveStatus()) {
            //end game
            if (bestscore < score) {
                bestscore = score;
            }
            stop();
            saveResult();
        } else {
            snake.buildSnake(g);
            snake.movement(apple);
            snakeCollidesWall(snake);
            snakeCollidesBody(snake);

            //snake eats apple?
            if (snakeEatApple(snake)) {
                score++;
                appleAppear = false;
            } else {
                snake.getSnakeList().remove(0);
            }
        }
    }

    /**
     * save the gameplay result to a file
     */
    private void saveResult() {
        try {
            String path = "./logs/allScoreLog.csv";
            FileWriter write = new FileWriter(path,true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            //format for saving
            List<String> record = Arrays.asList("Player",score.toString(),gameConfig.gameDifficulty.toString(),apple.getSkin(),dtf.format(now));
            write.append(String.join(",", record));
            write.append("\n");
            write.flush();
            write.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame.dispose();
    }

    /**
     * check whether the Snake ate the apple at the current time or not
     * @param snake
     * @return
     */
    public boolean snakeEatApple(SnakeAbstract snake) {
        int snakeSize = snake.getSnakeList().size();
        return snake.getSnakeList().get(snakeSize - 1).getxCoor() == apple.getxApple()
                && snake.getSnakeList().get(snakeSize - 1).getyCoor() == apple.getyApple();
    }

    /**
     * check whether collisions happen between the Snake and the boundaries of the gameplay
     * @param snake
     */
    public void snakeCollidesWall (SnakeAbstract snake) {
        int listSize = snake.getSnakeList().size();
        if (snake.getSnakeList().get(listSize - 1).getxCoor() < Config.boundSquare
                || snake.getSnakeList().get(listSize - 1).getxCoor() > Config.WIDTH/ Config.SQUARE_SIZE - Config.boundSquare-1
                || snake.getSnakeList().get(listSize - 1).getyCoor() < Config.boundSquare
                || snake.getSnakeList().get(listSize - 1).getyCoor() > Config.HEIGHT/ Config.SQUARE_SIZE - Config.boundSquare-1) {
            snake.setAliveStatus(false);
        }
    }

    /**
     * check whether the Snake collides with its own body
     * @param aSnake
     */
    public void snakeCollidesBody(SnakeAbstract aSnake) {
        Snake s = aSnake.getSnakeList().get(aSnake.getSnakeList().size() - 1);
        //snake collides its own body
        for (int i = 0 ; i < aSnake.getSnakeList().size() - 1 ; i++) {
            if (s.getxCoor() == aSnake.getSnakeList().get(i).getxCoor()
                    && s.getyCoor() == aSnake.getSnakeList().get(i).getyCoor()) {
                aSnake.setAliveStatus(false);
                break;
            }
        }
    }

    /**
     * paint the game board component, game score and instruction texts
     * @param g
     */
    public void paint(Graphics g) {
        g.clearRect(0, 0, Config.WIDTH, Config.HEIGHT);

        //BACKGROUND color
        g.setColor(gameConfig.boardColor);
        g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

        // draw grid
//        g.setColor(new Color(194, 245, 66));
//        for (int i = 0; i < Config.HEIGHT / Config.SQUARE_SIZE; ++i) {
//            g.drawLine(0, i*Config.SQUARE_SIZE, Config.WIDTH, i*Config.SQUARE_SIZE );
//            //g.drawLine(i*Config.SQUARE_SIZE, 0, Config.HEIGHT, i*Config.SQUARE_SIZE );
//            g.drawLine(i*Config.SQUARE_SIZE, 0, i*Config.SQUARE_SIZE, Config.HEIGHT);
//        }

        // PLAYGROUND color
        g.setColor(Config.BACKGROUND);
        g.fillRect(Config.SQUARE_SIZE* Config.boundSquare, Config.SQUARE_SIZE*Config.boundSquare,
                Config.WIDTH - Config.SQUARE_SIZE*Config.boundSquare*2,
                Config.HEIGHT - Config.SQUARE_SIZE*Config.boundSquare*2);

        g.setFont(Config.SCORE_FONT);
        g.setColor(Color.WHITE);
        g.drawString("SCORE: " + String.format("%04d", score), Config.SQUARE_SIZE*2 , Config.SQUARE_SIZE*2 - 20);

        if (!Config.running) {
            g.setFont(Config.SCORE_FONT);
            g.setColor(Color.WHITE);
            g.drawString("Press space to start", Config.SQUARE_SIZE*8 + 20 , Config.SQUARE_SIZE*12);
        }

        newApple(g);
        checkSnake(aSnake, g);
    }

    /**
     * check whether the space key is pressed, the game will begin
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (aSnake.isAliveStatus() && e.getKeyCode() == KeyEvent.VK_SPACE) {
            Config.running = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}