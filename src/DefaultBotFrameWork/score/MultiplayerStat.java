package DefaultBotFrameWork.score;

import Game.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiplayerStat {

    public static final String LOG_PATH = "./logs/total.txt";
    public static final String RECORDTABLE = "./logs/multiscoreboard.csv";
    public static boolean append = false;
    public static ArrayList<String> aList = new ArrayList<String>();
    public static String[][] TableRecord = new String[Config.botNameArr.length][Config.botNameArr.length];


    public static void run() throws Exception {
        readAllLinesFromFile(LOG_PATH);
        System.out.println("Unsorted:\n");
        for(String aRecord: aList){
            System.out.println(aRecord +"\n");
        }

        generateTableRecord(TableRecord);

        ArrayList<HeadToHead> everyRecord = convertListToEachRecord(aList);
        for (HeadToHead eachRecord : everyRecord) {
            addRecordtoTableRecord(eachRecord);
        }

        writeAllToHomeAwayRecordTable(RECORDTABLE, append);

    }

    private static void addRecordtoTableRecord(HeadToHead eachRecord) {
        String bot1 = eachRecord.getBot1();
        int bot1_pos = posBotinTable(bot1);

        String bot2 = eachRecord.getBot2();
        int bot2_pos = posBotinTable(bot2);

        int addPointHome = eachRecord.getScore1();
        int addPointAway = eachRecord.getScore2();

        String aRecord = TableRecord[bot1_pos][bot2_pos];
        String[] parts = aRecord.split(" - ");
        int homePtsATM = Integer.parseInt(parts[0]);
        int awayPtsATM = Integer.parseInt(parts[1]);

        int newHomePts = addPointHome + homePtsATM;
        int newAwayPts = addPointAway + awayPtsATM;

        TableRecord[bot1_pos][bot2_pos] = newHomePts + " - " + newAwayPts;
    }

    private static void generateTableRecord(String[][] tableRecord) {
        for (int i = 0 ; i < Config.botNameArr.length ; i++) {
            for (int j = 0 ; j < Config.botNameArr.length ; j++) {
                tableRecord[i][j] = "0 - 0";
            }
        }
    }

    private static ArrayList<HeadToHead> convertListToEachRecord(ArrayList<String> aList) {
        ArrayList<HeadToHead> homeAwayRecord = new ArrayList<>();
        for(String record : aList) {
            String[] parts = record.split(" ");
            String bot1 = parts[0];
            String bot2 = parts[2];
            int score1 = Integer.valueOf(parts[4]);
            int score2 = Integer.valueOf(parts[6]);
            homeAwayRecord.add(new HeadToHead(bot1,bot2,score1,score2));
        }
        return homeAwayRecord;
    }


    private static ArrayList<String> readAllLinesFromFile(String path) throws IOException {

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while( (line = bufferedReader.readLine()) != null){
            aList.add(line);
        }
        bufferedReader.close();

        return aList;
    }

    private static void csvFullExport(String path, boolean appendable) throws IOException {

        String inputStr = "HOME || AWAY";
        FileWriter results_fw;
        results_fw = new FileWriter(path, appendable);

        for(String botAway : Config.botNameArr){
//            System.out.print("," + botAway);
            inputStr += "," + botAway;
        }
        results_fw.write(inputStr+"\n");

        for (int i = 0 ; i < Config.botNameArr.length ; i++) {
            String botHome = Config.botNameArr[i];
            for (int j = 0 ; j < Config.botNameArr.length ; j++) {
                botHome += "," + TableRecord[i][j];
            }
            botHome += "\n";
            results_fw.write(botHome);

        }

        results_fw.close();
    }

    private static void writeAllToHomeAwayRecordTable(String path, boolean appendable) throws IOException {

        String inputStr = "HOME || AWAY";
        FileWriter results_fw;
        results_fw = new FileWriter(path, appendable);

        for(String botAway : Config.botNameArr){
//            System.out.print("," + botAway);
            inputStr += "," + botAway;
        }
        results_fw.write(inputStr+"\n");

        for (int i = 0 ; i < Config.botNameArr.length ; i++) {
//            String botHome = Config.botNameArr[i];
            String botHome = TableRecord[i][0];
            for (int j = 1 ; j < Config.botNameArr.length ; j++) {
                botHome += "," + TableRecord[i][j];
            }
            botHome += "\n";
            results_fw.write(botHome);

        }

        results_fw.close();
    }

    private static int posBotinTable (String botName) {
        for (int pos = 0 ; pos < Config.botNameArr.length ; pos++) {
            if (botName.equals(Config.botNameArr[pos])) {
                return pos;
            }
        }
        return -1;
    }


}