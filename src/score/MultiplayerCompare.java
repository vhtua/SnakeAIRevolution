package score;

import game.Apple;
import game.Config;

import java.io.*;
import java.util.ArrayList;

/**
 * This class creates a table of records of all the tournament among all bots
 */
public class MultiplayerCompare {
//    public static final String LOG_PATH = "logs/total.txt";
//    public static final String RECORDTABLE = "logs/multiscoreboard.csv";
    public static final String LOG_PATH = "logs";
    public static final String RECORDTABLE = "logs";
    public static boolean append = false;
    public static ArrayList<String> aList = new ArrayList<String>();
    public static String[][] TableRecord = new String[Config.botNameArr.length][Config.botNameArr.length];
    public static final File dir = new File(LOG_PATH);

    public static void execution() throws Exception {
        FileWriter fileWriter = new FileWriter(String.format("%s\\total.txt",dir), true);

        readAllLinesFromFile();
        generateTableRecord(TableRecord);

        ArrayList<MultiplayerScore> everyRecord = convertListToEachRecord(aList);
        for (MultiplayerScore eachRecord : everyRecord) {
            addRecordtoTableRecord(eachRecord);
        }
        writeAllToHomeAwayRecordTable(append);
    }

    /**
     * fill each cell of the table record with the corresponding scores that gets from the parameter
     * @param eachRecord
     */
    private static void addRecordtoTableRecord(MultiplayerScore eachRecord) {
        String bot1 = eachRecord.getBot1();
        int bot1_pos = posBotinTable(bot1);

        String bot2 = eachRecord.getBot2();
        int bot2_pos = posBotinTable(bot2);

        int addPointHome = eachRecord.getScore1();
        int addPointAway = eachRecord.getScore2();

        String aRecord = TableRecord[bot1_pos][bot2_pos];
//        System.out.println(aRecord);
        String[] parts = aRecord.split(" - ");
        int homePtsATM = Integer.parseInt(parts[0]);
        int awayPtsATM = Integer.parseInt(parts[1]);

        int newHomePts = addPointHome + homePtsATM;
        int newAwayPts = addPointAway + awayPtsATM;

        TableRecord[bot1_pos][bot2_pos] = newHomePts + " - " + newAwayPts;
    }

    /**
     * generate initial table record filled with all scores 0-0
     * @param tableRecord
     */
    private static void generateTableRecord(String[][] tableRecord) {
        for (int i = 0 ; i < Config.botNameArr.length ; i++) {
            for (int j = 0 ; j < Config.botNameArr.length ; j++) {
                tableRecord[i][j] = "0 - 0";
            }
        }
    }

    /**
     * get score for each round
     * @param aList
     * @return
     */
    private static ArrayList<MultiplayerScore> convertListToEachRecord(ArrayList<String> aList) {
        ArrayList<MultiplayerScore> homeAwayRecord = new ArrayList<MultiplayerScore>();
        for(String record : aList) {
            if (record != null) {
                String[] parts = record.split(" ");
                String bot1 = parts[0];
                String bot2 = parts[2];
                int score1 = Integer.valueOf(parts[4]);
                int score2 = Integer.valueOf(parts[6]);
                homeAwayRecord.add(new MultiplayerScore(bot1, bot2, score1, score2));
            }
        }
        return homeAwayRecord;
    }

    /**
     * read all data lines from a file
     *
     * @return
     * @throws IOException
     */
    private static ArrayList<String> readAllLinesFromFile() throws IOException {
        FileReader fileReader = new FileReader(String.format("%s\\total.txt", dir));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while( (line = bufferedReader.readLine()) != null){
            aList.add(line);
        }
        bufferedReader.close();

        return aList;
    }

    /**
     * create the final table of record and filled
     *
     * @param appendable
     * @throws IOException
     */
    private static void writeAllToHomeAwayRecordTable( boolean appendable) throws IOException {
        String inputStr = "HOME || AWAY";
        FileWriter results_fw;
        results_fw = new FileWriter(String.format("%s\\multiscoreboard.csv", dir), appendable);

        for(String botAway : Config.botNameArr){
            inputStr += "," + botAway;
        }
        results_fw.write(inputStr+"\n");

        for (int i = 0 ; i < Config.botNameArr.length ; i++) {
            String botHome = TableRecord[i][0];
            for (int j = 1 ; j < Config.botNameArr.length ; j++) {
                botHome += "," + TableRecord[i][j];
            }
            botHome += "\n";
            results_fw.write(botHome);

        }

        results_fw.close();
    }

    /**
     * get the position of the bot name on the table record
     * @param botName
     * @return
     */
    private static int posBotinTable (String botName) {
        for (int pos = 0 ; pos < Config.botNameArr.length ; pos++) {
            if (botName.equals(Config.botNameArr[pos])) {
                return pos;
            }
        }
        return -1;
    }
}