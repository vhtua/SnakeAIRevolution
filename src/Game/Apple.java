package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Apple {
    private Coordinate appleCoor;
    private int width, height;
    private Config mySkin;

    public Apple (int xApple, int yApple, int size) {
        this.appleCoor = new Coordinate(xApple, yApple);
        this.width = size;
        this.height = size;
    }


    public void drawApple (Graphics g) {
        try {
            mySkin = new Config();
            mySkin.loadPreySkin();
            BufferedImage img = ImageIO.read(mySkin.SKIN);
            g.drawImage(img, appleCoor.x * width, appleCoor.y * height, width, height, null);
        } catch (Exception ex){
            g.setColor(Color.RED);
            g.fillOval(appleCoor.x * width, appleCoor.y * height, width, height);
            //ex.printStackTrace();
        }
    }

    public int getxApple() {
        return appleCoor.x;
    }
    public int getyApple() {
        return appleCoor.y;
    }

    public String getSkin() {
        if (mySkin.SKIN.equals(Config.APPLE_SKIN)) {
            return "apple";
        } else if (mySkin.SKIN.equals(Config.BANANA_SKIN)) {
            return "banana";
        } else if (mySkin.SKIN.equals(Config.CHERRY_SKIN)) {
            return "cherry";
        } else if (mySkin.SKIN.equals(Config.MOUSE_SKIN)) {
            return "mouse";
        }
        return "Default Skin";
    }

}