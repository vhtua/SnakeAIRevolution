package GUI;

import DefaultBotFrameWork.SnakesUIMain;
import Game.Config;
import Game.GameFrame;
import Game.MessageWithLink;
import Game.ScoreMain;
import DefaultBotFrameWork.score.MultiplayerStat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;
import javax.swing.*;


public class StartScreen extends JFrame implements ActionListener, Runnable {
    @Serial
    private static final long serialVersionUID = 1L;

    //Main Screen general components
    final int originalTile = 16;
    final int scale = 3;
    final int tileSize = originalTile * scale;
    final int maxScreenCol = 18;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    private final ImageIcon StartScreenBackground = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/GUI/img/backgrv3.jpg")));
    private final ImageIcon SettingBackground = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/GUI/img/settingsv5.jpg")));
    private final ImageIcon SinglePlayerStatisticsBackground = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/GUI/img/SinglePlayerStatisticsv4.png")));
    private final ImageIcon BotVsBotStatisticsBackground = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/GUI/img/botStatistics3.png")));
    private ImageIcon logo;
    private JLabel labelContainer;
    JButton playButton = new JButton();
    JButton settingsButton = new JButton();
    JButton statisticsButton = new JButton();
    JButton quitgameButton = new JButton();
    JButton infoButton = new JButton();
    JButton backButton = new JButton();

    // Settings GUI.Frame
    JButton applySettingChanges = new JButton();
    JTable statisticsTable = new JTable();
    //----> single player
    private final static Combobox<String> gameDifficulty = new Combobox<>();
    private final static Combobox<String> playerColorCombobox = new Combobox<>();
    private final static Combobox<String> playerBoardColor = new Combobox<>();
    private final static Combobox<String> playerPreyType = new Combobox<>();
    //----> botVsBot
    private final static Combobox<String> bot1NameCombobox = new Combobox<>();
    private final static Combobox<String> bot2NameCombobox = new Combobox<>();
    private final static Combobox<String> bot1ColorCombobox = new Combobox<>();
    private final static Combobox<String> bot2ColorCombobox = new Combobox<>();
    private final static JTextField botNumberofTournaments = new JTextField();


    // Statistics
    public static String row = "";
    public static String[] statisticsBoard = {"BotvsBot Statistics ▶", "◀ SinglePlayer Statistics"};
    public static String nextStatistics = statisticsBoard[0];
    public static JButton changeStatisticsBoardButton = new JButton();


    // ============================== METHODS ===================================== //

    /**
     * centre the frame on the window screen
     * @param frame
     */
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }


    /**
     * initialize all the buttons and components when create an instance of StartScreen
     * @throws Exception
     */
    public StartScreen() throws Exception {
        backButton.setBounds(2 + 680,tileSize*14 + 2, tileSize*3,tileSize);
        backButton.setText("◀ Back");
        backButton.setFont(new Font("Comic Sans",Font.BOLD,21));
        backButton.setFocusable(false);
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        backButton.setForeground(Color.white);
        backButton.setBackground(Color.black);
        backButton.addActionListener(this);

        changeStatisticsBoardButton.setBounds(380,tileSize*14 + 2, tileSize*3 + 150,tileSize);
        changeStatisticsBoardButton.setText(nextStatistics);
        changeStatisticsBoardButton.setFont(new Font("Comic Sans",Font.BOLD,21));
        changeStatisticsBoardButton.setFocusable(false);
        changeStatisticsBoardButton.setHorizontalTextPosition(JButton.CENTER);
        changeStatisticsBoardButton.setVerticalTextPosition(JButton.CENTER);
        changeStatisticsBoardButton.setForeground(Color.white);
        changeStatisticsBoardButton.setBackground(new Color(16, 145, 55));
        changeStatisticsBoardButton.addActionListener(this);

        gameDifficulty.addItem("Easy");
        gameDifficulty.addItem("Normal");
        gameDifficulty.addItem("Hard");
        gameDifficulty.addItem("Extreme");
        gameDifficulty.addItem("Ultra");

        playerColorCombobox.addItem("red");
        playerColorCombobox.addItem("blue");
        playerColorCombobox.addItem("yellow");
        playerColorCombobox.addItem("green");
        playerColorCombobox.addItem("white");
        playerColorCombobox.addItem("sky blue");

        playerPreyType.addItem("apple");
        playerPreyType.addItem("banana");
        playerPreyType.addItem("cherry");
        playerPreyType.addItem("mouse");

        playerBoardColor.addItem("black");
        playerBoardColor.addItem("gray");
        playerBoardColor.addItem("violet");
        playerBoardColor.addItem("brown");
        playerBoardColor.addItem("periwinkle");

        applySettingChanges.setBounds(2 + 425,tileSize*14 + 2, tileSize*3 + 100,tileSize);
        applySettingChanges.setText("Apply Changes");
        applySettingChanges.setFont(new Font("Comic Sans",Font.BOLD,21));
        applySettingChanges.setFocusable(false);
        applySettingChanges.setHorizontalTextPosition(JButton.CENTER);
        applySettingChanges.setVerticalTextPosition(JButton.CENTER);
        applySettingChanges.setForeground(Color.white);
        applySettingChanges.setBackground(new Color(16, 145, 55));
        applySettingChanges.addActionListener(this);

        for (int i = 0; i < Config.botNameArr.length; ++i) {
            bot1NameCombobox.addItem(Config.botNameArr[i]);
            bot2NameCombobox.addItem(Config.botNameArr[i]);
        }

        bot1ColorCombobox.addItem("sky blue");
        bot1ColorCombobox.addItem("violet");
        bot1ColorCombobox.addItem("lime green");
        bot1ColorCombobox.addItem("purple");

        bot2ColorCombobox.addItem("white");
        bot2ColorCombobox.addItem("gray");
        bot2ColorCombobox.addItem("orange");
        bot2ColorCombobox.addItem("yellow");

        playButton.setBounds(tileSize*5,tileSize*3 + 50, tileSize*7,tileSize);
        playButton.setText("▷       SINGLE PLAYER");
        playButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        playButton.setFocusable(false);
        playButton.setHorizontalAlignment(SwingConstants.LEFT);
        playButton.setForeground(Color.white);
        playButton.setBackground(new Color(0,204,102));
        playButton.addActionListener(this);

        settingsButton.setBounds(tileSize*5,tileSize*3 + 140, tileSize*7,tileSize);
        settingsButton.setText("⬡       SETTINGS");
        settingsButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        settingsButton.setFocusable(false);
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setForeground(Color.white);
        settingsButton.setBackground(new Color(0,204,102));
        settingsButton.addActionListener(this);

        statisticsButton.setBounds(tileSize*5,tileSize*3 + 230, tileSize*7,tileSize);
        statisticsButton.setText("≡        STATISTICS");
        statisticsButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        statisticsButton.setFocusable(false);
        statisticsButton.setHorizontalAlignment(SwingConstants.LEFT);
        statisticsButton.setForeground(Color.white);
        statisticsButton.setBackground(new Color(0,204,102));
        statisticsButton.addActionListener(this);

        quitgameButton.setBounds(tileSize*5,tileSize*3 + 320, tileSize*7,tileSize);
        quitgameButton.setText("⤬       QUIT GAME");
        quitgameButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        quitgameButton.setFocusable(false);
        quitgameButton.setHorizontalAlignment(SwingConstants.LEFT);
        quitgameButton.setForeground(Color.white);
        quitgameButton.setBackground(new Color(0,204,102));
        quitgameButton.addActionListener(this);

        infoButton.setBounds(2 + 5,tileSize*14 - 10, tileSize*3,tileSize);
        infoButton.setText("Credits");
        infoButton.setFont(new Font("Comic Sans",Font.BOLD,21));
        infoButton.setFocusable(false);
        infoButton.setHorizontalTextPosition(JButton.CENTER);
        infoButton.setVerticalTextPosition(JButton.CENTER);
        infoButton.setForeground(Color.white);
        infoButton.setBackground(Color.black);
        infoButton.addActionListener(this);

        ScoreMain.execution();
    }


    public void paintStartScreen() {
        logo = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/GUI/img/logo.png")));
        this.setIconImage(logo.getImage());

        labelContainer = new JLabel(StartScreenBackground);
        labelContainer.setSize(new Dimension(screenWidth, screenHeight));

        this.setTitle("SnakeAI Revolution");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(102,204,0));

        this.add(playButton);
        this.add(settingsButton);
        this.add(statisticsButton);
        this.add(quitgameButton);
        this.add(infoButton);
        this.add(labelContainer);

        centreWindow(this);
    }

    public void paintStartScreen2() {
        this.setIconImage(logo.getImage());
        labelContainer = new JLabel(StartScreenBackground);
        labelContainer.setSize(new Dimension(screenWidth, screenHeight));

        this.setTitle("SnakeAI Revolution");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(102,204,0));
        this.add(playButton);
        this.add(settingsButton);
        this.add(statisticsButton);
        this.add(quitgameButton);
        this.add(infoButton);

        this.add(labelContainer);
        //centreWindow(this);

    }


    public void paintSettingsFrame() {
        this.setTitle("SnakeAI Revolution/Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);


        gameDifficulty.setPreferredSize(new Dimension(200, 40));
        this.add(gameDifficulty);
        Insets insets = this.getInsets();
        Dimension size = gameDifficulty.getPreferredSize();
        gameDifficulty.setBounds(245 + insets.left, 142 + insets.top, size.width, size.height);

        playerColorCombobox.setPreferredSize(new Dimension(200, 40));
        this.add(playerColorCombobox);
        playerColorCombobox.setBounds(245 + insets.left, 255 + insets.top, size.width, size.height);
        playerColorCombobox.addActionListener(this);

        playerPreyType.setPreferredSize(new Dimension(200, 40));
        this.add(playerPreyType);
        playerPreyType.setBounds(586 + insets.left, 255 + insets.top, size.width, size.height);

        playerBoardColor.setPreferredSize(new Dimension(200, 40));
        this.add(playerBoardColor);
        playerBoardColor.setBounds(586 + insets.left, 142 + insets.top, size.width, size.height);


        // botVsbot Settings
        bot1NameCombobox.setPreferredSize(new Dimension(200, 40));
        this.add(bot1NameCombobox);
        bot1NameCombobox.setBounds(242 + insets.left, 428 + insets.top, size.width, size.height);

        bot2NameCombobox.setPreferredSize(new Dimension(200, 40));
        this.add(bot2NameCombobox);
        bot2NameCombobox.setBounds(541 + insets.left, 428 + insets.top, size.width, size.height);

        bot1ColorCombobox.setPreferredSize(new Dimension(200, 40));
        this.add(bot1ColorCombobox);
        bot1ColorCombobox.setBounds(242 + insets.left, 512 + insets.top, size.width, size.height);

        bot2ColorCombobox.setPreferredSize(new Dimension(200, 40));
        this.add(bot2ColorCombobox);
        bot2ColorCombobox.setBounds(541 + insets.left, 512 + insets.top, size.width, size.height);



        botNumberofTournaments.setPreferredSize(new Dimension(150, 40));
        this.add(botNumberofTournaments);
        botNumberofTournaments.setBounds(593 + insets.left, 577 + insets.top, 150, 40);


        applySettingChanges.addActionListener(e -> {

        });


        labelContainer = new JLabel(SettingBackground);
        labelContainer.setSize(new Dimension(screenWidth, screenHeight));

        this.add(applySettingChanges);
        this.add(backButton);
        this.add(labelContainer);
        this.setVisible(true);

        //centreWindow(this);
    }


    public int countLineNumberCSV(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
    }

    public void paintSinglePlayerStatisticsFrame() throws IOException {
        this.setTitle("SnakeAI Revolution/Single Player Statistics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);
        labelContainer = new JLabel(SinglePlayerStatisticsBackground);
        labelContainer.setSize(new Dimension(screenWidth, screenHeight));
        this.add(changeStatisticsBoardButton);
        this.add(backButton);
        this.add(labelContainer);

        int numberOfRows = 14;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./src/Game/allHighscore.csv"));
            for (int rowCounter = 1 ; rowCounter <= numberOfRows && (row = csvReader.readLine()) != null ; rowCounter++) {
                String[] data = row.split(",");
                drawRow_Statistics(data, rowCounter);
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void drawRow_Statistics(String[] data, int rowCount) {
        for (int i = 0 ; i < data.length  ; i++) {
            JLabel l = new JLabel(data[i]);
            l.setFont(new Font("Arial", Font.BOLD, 20));
            l.setForeground(Color.WHITE);
            l.setBounds(80*(i+1) + 100, 30*rowCount + 150, 600,100);
            labelContainer.add(l);
            this.add(labelContainer);
        }
    }


    private void paintBotVsBotStatisticsFrame() {
        this.setTitle("SnakeAI Revolution/Bot vs Bot Statistics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(screenWidth,screenHeight);
        this.setVisible(true);
        labelContainer = new JLabel(BotVsBotStatisticsBackground);
        labelContainer.setSize(new Dimension(screenWidth, screenHeight));
        this.add(changeStatisticsBoardButton);
        this.add(backButton);
        this.add(labelContainer);

        int numberOfRows = 5;
        try {
            boolean firstLine = true;
            BufferedReader csvReader = new BufferedReader(new FileReader("./logs/multiscoreboard.csv"));
            for (int rowCounter = 0 ; rowCounter <= numberOfRows && (row = csvReader.readLine()) != null ; rowCounter++) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                } else {
                    String[] data = row.split(",");
                    //System.out.println(data[1]);
                    drawRow_BotStatistics(data, rowCounter);
                }

            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawRow_BotStatistics(String[] data, int rowCount) {
        for (int i = 0 ; i < data.length  ; i++) {
            JLabel l = new JLabel(data[i]);
            l.setFont(new Font("Arial", Font.BOLD, 20));
            l.setForeground(Color.WHITE);
            l.setBounds(100*(i+1) + 150, 60*rowCount + 175, 600,100);
            labelContainer.add(l);
            this.add(labelContainer);
        }
    }


    /**
     * create events for every button of the screen
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playButton) {
            this.setVisible(false);
            this.dispose();

            new Thread(new Runnable(){
                @Override
                public void run(){
                    try {
                        SnakesUIMain.main(null);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (e.getSource() == settingsButton) {
            this.getContentPane().removeAll();
            this.validate();
            this.repaint();
            this.paintSettingsFrame();
            
        } else if (e.getSource() == applySettingChanges) {
            try {
                FileWriter myWriter = new FileWriter("./src/Game/gameSettings.txt");
                myWriter.write("gameDifficulty\n");
                myWriter.write(gameDifficulty.getSelectedItem() + "\n");
                myWriter.write("boardColor\n");
                myWriter.write(playerBoardColor.getSelectedItem() + "\n");
                myWriter.write("snakeColor\n");
                myWriter.write(playerColorCombobox.getSelectedItem() + "\n");
                myWriter.write("preyType\n");
                myWriter.write( playerPreyType.getSelectedItem() + "\n");
                myWriter.write("bot01\n");
                myWriter.write( bot1NameCombobox.getSelectedItem() + "\n");
                myWriter.write( bot1ColorCombobox.getSelectedItem() + "\n");
                myWriter.write("bot02\n");
                myWriter.write( bot2NameCombobox.getSelectedItem() + "\n");
                myWriter.write( bot2ColorCombobox.getSelectedItem() + "\n");
                myWriter.write("numberOfTournaments\n");
                myWriter.write( botNumberofTournaments.getText().isEmpty() ? "5" : botNumberofTournaments.getText());
                myWriter.close();
                System.out.println("Successfully Change.");
                JOptionPane.showMessageDialog(null, "Successfully Updated Settings!", "Game Settings", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(null, "New Changes are applied!", "Game Settings", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException fileEx) {
                System.out.println("An error occurred.");
                fileEx.printStackTrace();
            }

        } else if (e.getSource() == statisticsButton) {
            this.getContentPane().removeAll();
            this.validate();
            this.repaint();
            if (nextStatistics.equals(statisticsBoard[0])) {
                try {
                    this.paintSinglePlayerStatisticsFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                this.paintBotVsBotStatisticsFrame();
            }

        } else if (e.getSource() == quitgameButton) {
//            int respone = JOptionPane.showConfirmDialog(null, "You are about to exit the Game. Are you sure?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
//            if (respone == JOptionPane.YES_OPTION) {
//                this.setVisible(false);
//                this.dispose();
//            }
            try {
                new GameFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == infoButton) {
            JOptionPane.showMessageDialog(null, new MessageWithLink("Snake Revolution" +
                    "<br>version 1.0.0<br><br>" +
                    "▶ Authors:<br>" +
                    "Nguyen Phuoc Bao Minh<br>" +
                    "Nguyen Vu Doanh Khoa<br>" +
                    "Vu Hoang Tuan Anh<br>" +
                    "Ba Nguyen Quoc Anh<br><br>" +
                    "▶ Project Link on Github:<br>" +
                    "<a href=\"https://github.com/minhnguyen1312/ProjectSnakeAI\">https://github.com/minhnguyen1312/ProjectSnakeAI</a>"), "About us", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Snake Revolution\nversion 1.0.0\n\nAuthors:\nNguyen Phuoc Bao Minh\nNguyen Vu Doanh Khoa\nVu Hoang Tuan Anh\nBa Nguyen Quoc Anh", "About us", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            this.validate();
            this.repaint();
            this.paintStartScreen2();

        } else if (e.getSource() == changeStatisticsBoardButton) {
            if (nextStatistics.equals(statisticsBoard[0])) {
                // switch to BotvsBot Mode
                try {
                    MultiplayerStat.run();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                this.getContentPane().removeAll();
                this.validate();
                this.repaint();
                this.paintBotVsBotStatisticsFrame();
                nextStatistics = statisticsBoard[1];
                changeStatisticsBoardButton.setText(nextStatistics);
            } else {
                this.getContentPane().removeAll();
                this.validate();
                this.repaint();
                try {
                    this.paintSinglePlayerStatisticsFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                nextStatistics = statisticsBoard[0];
                changeStatisticsBoardButton.setText(nextStatistics);
            }
        }

    }

    @Override
    public void run() {
        paintStartScreen();
    }
}
