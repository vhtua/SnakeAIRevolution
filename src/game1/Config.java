package game1;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class implements all the Configurations for the Game
 */
public class Config {
    // Game General Config
    public static boolean running = false;
    public static final int WIDTH = 750, HEIGHT = 750;
    public static final int SQUARE_SIZE = 30;
    public static final int boundSquare = 2;
    public static final Color BACKGROUND = Color.DARK_GRAY;
    public static boolean moveAtleastASpace = false;
    public static Font SCORE_FONT = new Font("Comic Sans", Font.PLAIN, 24);
    public Path configPath = Paths.get("./src/Game/gameSettings.txt");

    // Prey Type
    public static File APPLE_SKIN = new File("./src/skin/apple8bit.png");
    public static File CHERRY_SKIN = new File("./src/skin/cherry.png");
    public static File BANANA_SKIN = new File("./src/skin/banana.png");
    public static File MOUSE_SKIN = new File("./src/skin/mouse.png");
    public File SKIN;
    public int DELAY;
    public int appleTimer;

    // Single Player Game Mode
    public enum GameDifficulty {
        Easy(0),
        Normal(1),
        Hard(2),
        Extreme(3),
        Ultra(4);

        public final int difficultyOrdinal;
        GameDifficulty (int difficulty) {
            this.difficultyOrdinal = difficulty;
        }
    }

    public GameDifficulty gameDifficulty;

    // Snake Config
    public Color snakeColor;

    // GameBoard Config
    public Color boardColor;

    // botVsbot Config
    public String bot01Name;
    public String bot02Name;
    public Color bot01Color;
    public Color bot02Color;
    public int numberOfTournaments;
    public static String[] botNameArr = {"a_zhuchkov", "anhsBot", "SampleBot", "tunaBot", "v_smirnov"};

    // ============================= METHODS ======================================

    /**
     * initialize all the Game Configs
     * @throws IOException
     */
    public void loadAllConfig() throws IOException {
        this.loadPreySkin();
        this.loadGameDifficulty();
        this.loadSnakeColor();
        this.loadBoardColor();
    }

    /**
     * load the skin for the prey in the Single Player Mode
     * @throws IOException
     */
    public void loadPreySkin() throws IOException {
        // default skin = apple
        SKIN = new File("./src/Game/skin/apple8bit.png");
        String skinInput = Files.readAllLines(configPath).get(7);

        switch (skinInput) {
            case "apple"    -> SKIN = APPLE_SKIN;
            case "banana"   -> SKIN = BANANA_SKIN;
            case "cherry"   -> SKIN = CHERRY_SKIN;
            case "mouse"    -> SKIN = MOUSE_SKIN;
        }
    }

    /**
     * load the game difficulties in Single Player Mode
     * @throws IOException
     */
    public void loadGameDifficulty() throws IOException {
        GameDifficulty gameModeInput = GameDifficulty.valueOf(Files.readAllLines(configPath).get(1));
        this.gameDifficulty = gameModeInput;

        switch (gameModeInput) {
            case Easy -> {
                this.DELAY = 125;
                this.appleTimer = 80;
            }
            case Normal -> {
                this.DELAY = 100;
                this.appleTimer = 65;
            }
            case Hard -> {
                this.DELAY = 60;
                this.appleTimer = 40;
            }
            case Extreme -> {
                this.DELAY = 30;
                this.appleTimer = 30;
            }
            case Ultra -> {
                this.DELAY = 20;
                this.appleTimer = 10;
            }
        }
    }


    /**
     * load the Snake Color of the Single Player Mode
     * @throws IOException
     */
    public void loadSnakeColor() throws IOException {
        String snakeColorInput = Files.readAllLines(configPath).get(5);

        switch (snakeColorInput) {
            case "red"      -> this.snakeColor = Color.RED;
            case "blue"     -> this.snakeColor = Color.BLUE;
            case "yellow"   -> this.snakeColor = Color.YELLOW;
            case "green"    -> this.snakeColor = Color.GREEN;
            case "white"    -> this.snakeColor = Color.WHITE;
            case "sky blue" -> this.snakeColor = new Color(123,213,213);
        }
    }

    /**
     * load the Color of the Board Border in the Single Player Mode
     * @throws IOException
     */
    public void loadBoardColor() throws IOException {
        String boardColorInput = Files.readAllLines(configPath).get(3);

        switch (boardColorInput) {
            case "black"        -> this.boardColor = Color.BLACK;
            case "gray"         -> this.boardColor = Color.GRAY;
            case "violet"       -> this.boardColor = new Color(127, 0, 255);
            case "brown"        -> this.boardColor = new Color(150,75,0);
            case "periwinkle"   -> this.boardColor = new Color(204, 204, 255);
        }
    }

    /**
     * load all configs for the BotvsBot Mode (the names and the color of the 2 bots)
     * @throws IOException
     */
    public void loadBotvsBotMode() throws IOException {
        String bot01ColorInput = Files.readAllLines(configPath).get(10);
        String bot02ColorInput = Files.readAllLines(configPath).get(13);

        this.bot01Name = "bot." + Files.readAllLines(configPath).get(9);
        this.bot02Name = "bot." + Files.readAllLines(configPath).get(12);

        switch (bot01ColorInput) {
            case "sky blue"     -> this.bot01Color = new Color(92, 192, 255);
            case "violet"       -> this.bot01Color = new Color(127, 0, 255);
            case "lime green"   -> this.bot01Color = new Color(120,190,33);
            case "purple"       -> this.bot01Color = new Color(128, 0, 128);
        }

        switch (bot02ColorInput) {
            case "white"    -> this.bot02Color = new Color(255, 255, 255);
            case "gray"     -> this.bot02Color = new Color(128, 128, 128);
            case "orange"   -> this.bot02Color = Color.ORANGE;
            case "yellow"   -> this.bot02Color = Color.YELLOW;
        }

        this.numberOfTournaments = Integer.parseInt(Files.readAllLines(configPath).get(15));
    }

}
