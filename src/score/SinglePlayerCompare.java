package score;

import game.Config;

import java.io.*;
import java.util.ArrayList;

/**
 * This class create a record of Single Player Mode scores and sort them in order
 */
public class SinglePlayerCompare {
//    public static final String READ_PATH = "game/logs/allScoreLog.csv";
//    public static final String WRITE_PATH = "game/logs/allScoreLog.csv";
    public static final String READ_PATH = "logs";
    public static final File dir = new File(READ_PATH);

    public static boolean append = false;
    public static ArrayList<String> StringList = new ArrayList<String>();

    /**
     *
     * @throws Exception can not read the file
     */
    public static void execution() throws Exception {
        File dir = new File(READ_PATH);
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Cannot create log directory.");
        }
        FileWriter fileWriter = new FileWriter(String.format("%s\\allScoreLog.csv",dir), true);

        readAllLinesFromFile(READ_PATH);
        ArrayList<SinglePlayerScore> SnakeList = convertListToSnake(StringList);
        writeToHighScore(SnakeList);
    }

    /**
     * write the final sorting scores to a file
     * @param snakeList
     * @throws IOException
     */
    private static void writeToHighScore(ArrayList<SinglePlayerScore> snakeList) throws IOException {
        FileWriter results_fw;
        results_fw = new FileWriter(String.format("%s\\allHighScore.csv", dir), append);

        for(SinglePlayerScore snake : snakeList){
            results_fw.write(snake.toString() + "\n");
        }

        results_fw.close();
    }

    /**
     * sorting all the scores
     * @param aList
     * @return
     */
    private static ArrayList<SinglePlayerScore> convertListToSnake(ArrayList<String> aList) {
        ArrayList<SinglePlayerScore> snake = new ArrayList<>();
        //aList.remove(0);
        for(String snakePart : aList) {
            String[] parts = snakePart.split(",");
            String player = parts[0];
            int score = Integer.parseInt(parts[1]);
            Config.GameDifficulty difficulty = Config.GameDifficulty.valueOf((parts[2]));
            String preyType = parts[3];
            String date = parts[4];
            snake.add(new SinglePlayerScore(player,score,difficulty,preyType,date));
        }

        snake.sort((o1, o2) -> o1.compareTo(o2));
        return snake;
    }

    /**
     * read the csv file line by line
     * @param path
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readAllLinesFromFile(String path) throws IOException {

        FileReader fileReader = new FileReader(String.format("%s\\allScoreLog.csv",dir));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while( (line = bufferedReader.readLine()) != null){
            StringList.add(line);
        }
        bufferedReader.close();

        return StringList;
    }
}