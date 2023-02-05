package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreMain {

    public static final String READ_PATH = "./src/Game/allScoreLog.csv";
    public static final String WRITE_PATH = "./src/Game/allHighscore.csv";

    public static boolean append = false;
    public static ArrayList<String> StringList = new ArrayList<String>();

    public static void execution() throws Exception {
        readAllLinesFromFile(READ_PATH);
//        System.out.println("Unsorted:\n");
//        for(String aSnake: StringList){
//            System.out.println(aSnake +"\n");
//        }

        ArrayList<SnakeScore> SnakeList = convertListToSnake(StringList);
        writeToHighScore(SnakeList);

    }

    private static void writeToHighScore(ArrayList<SnakeScore> snakeList) throws IOException {
        FileWriter results_fw;
        results_fw = new FileWriter(WRITE_PATH, append);

        for(SnakeScore snake : snakeList){
//            System.out.println(snake.toString());
            results_fw.write(snake.toString() + "\n");
        }

        results_fw.close();
    }

    private static ArrayList<SnakeScore> convertListToSnake(ArrayList<String> aList) {
        ArrayList<SnakeScore> snake = new ArrayList<>();
        aList.remove(0);
        for(String snakePart : aList) {
            String[] parts = snakePart.split(",");
            String player = parts[0];
            int score = Integer.parseInt(parts[1]);
            Config.GameDifficulty difficulty = Config.GameDifficulty.valueOf((parts[2]));
            String preyType = parts[3];
            String date = parts[4];
            snake.add(new SnakeScore(player,score,difficulty,preyType,date));
        }

        snake.sort((o1, o2) -> o1.compareTo(o2));
        return snake;
    }


    public static ArrayList<String> readAllLinesFromFile(String path) throws IOException {

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while( (line = bufferedReader.readLine()) != null){
            StringList.add(line);
        }
        bufferedReader.close();

        return StringList;

    }

}