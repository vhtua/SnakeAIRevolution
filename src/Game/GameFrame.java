package Game;

import javax.swing.*;
import java.io.IOException;

public class GameFrame {
    public GameFrame() throws IOException {
        JFrame frame = new JFrame();
        GameMap map = new GameMap();

        frame.add(map);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Snake Game");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

