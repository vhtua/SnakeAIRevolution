package game;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * This class implements all the Configurations for the Game
 */
public class Config {
    // Game General Config
    public static boolean running = false;
    public static final int WIDTH = 750, HEIGHT = 750;
    public static final int SQUARE_SIZE = 30;
    public static final int boundSquare = 2;
    public static final Color BACKGROUND = Color.BLACK;
    public static boolean moveAtleastASpace = false;
    public static Font SCORE_FONT = new Font("Comic Sans", Font.PLAIN, 24);
    private static final String LOG_DIRECTORY_PATH = "logs";
    public Path configPath = Paths.get("logs\\gameSettings.txt");

    // Prey Type
    public ImageIcon APPLE_SKIN = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/skin/apple8bit.png")));

    public ImageIcon CHERRY_SKIN = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/skin/cherry.png")));
    public ImageIcon BANANA_SKIN = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/skin/banana.png")));
    public ImageIcon MOUSE_SKIN = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/skin/mouse.png")));
    public ImageIcon SKIN;
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

    static {
        try {
            initialSetting();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * initialize all the Game Configs
     * @throws IOException can not read the config file
     */
    public void loadAllConfig() throws IOException {
        this.loadPreySkin();
        this.loadGameDifficulty();
        this.loadSnakeColor();
        this.loadBoardColor();
    }

    /**
     * load the skin for the prey in the Single Player Mode
     * @throws IOException can not read the config file
     */
    public void loadPreySkin() throws IOException {
//        java.net.URL imageURL = getClass().getResource("/skin/apple8bit.png");
//        if (imageURL != null) {
//            Image appleImage = new ImageIcon(imageURL).getImage().getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10,
//                    Image.SCALE_SMOOTH);
//            APPLE_SKIN = new ImageIcon(appleImage);
//        }
//
//        java.net.URL imageURL2 = getClass().getResource("/skin/mouse.png");
//        if (imageURL2 != null) {
//            Image mouseImage = new ImageIcon(imageURL2).getImage().getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10,
//                    Image.SCALE_SMOOTH);
//            MOUSE_SKIN = new ImageIcon(mouseImage);
//        }


        // default skin = apple
        SKIN = APPLE_SKIN;
        String skinInput = Files.readAllLines(configPath).get(7);

//        FileReader fileReader = new FileReader(String.format("%s\\gameSettings.txt", "logs"));
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String line = null;
//            for (int i = 0; i < 7; i++) {
//                bufferedReader.readLine();
//            }
//            String extractedLine = bufferedReader.readLine();
//            System.out.println(extractedLine);

        switch (skinInput) {
            case "apple"    -> SKIN = APPLE_SKIN;
            case "banana"   -> SKIN = BANANA_SKIN;
            case "cherry"   -> SKIN = CHERRY_SKIN;
            case "mouse"    -> SKIN = MOUSE_SKIN;
        }
    }

    /**
     * load the game difficulties in Single Player Mode
     * @throws IOException can not read the config file
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
     * @throws IOException can not read the config file
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
     * @throws IOException can not read the config file
     */
    public void loadBoardColor() throws IOException {
        String boardColorInput = Files.readAllLines(configPath).get(3);

        switch (boardColorInput) {
            case "black"        -> this.boardColor = new Color(40,40,40);
            case "gray"         -> this.boardColor = Color.GRAY;
            case "violet"       -> this.boardColor = new Color(127, 0, 255);
            case "brown"        -> this.boardColor = new Color(150,75,0);
            case "periwinkle"   -> this.boardColor = new Color(204, 204, 255);
        }
    }

    /**
     * load all configs for the BotvsBot Mode (the names and the color of the 2 bots)
     * @throws IOException can not read the config file
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

    public static void initialSetting() throws IOException {
        File dir = new File("logs");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Cannot create log directory.");
        }
        FileWriter myWriter = new FileWriter(String.format("%s\\gameSettings.txt","logs"), false);
        myWriter.write("gameDifficulty\n" +
                "Easy\n" +
                "boardColor\n" +
                "black\n" +
                "snakeColor\n" +
                "red\n" +
                "preyType\n" +
                "apple\n" +
                "bot01\n" +
                "a_zhuchkov\n" +
                "sky blue\n" +
                "bot02\n" +
                "a_zhuchkov\n" +
                "white\n" +
                "numberOfTournaments\n" +
                "5");

        myWriter.close();
    }
}
