package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class implements the prey (apple) of the Single Player Mode
 */
public class Apple {
    private Coordinate appleCoor;
    private int width, height;
    private Config mySkin;

    public Apple (int xApple, int yApple, int size) {
        this.appleCoor = new Coordinate(xApple, yApple);
        this.width = size;
        this.height = size;
    }

    /**
     * draw Apple skin in Single Player Mode
     * @param g
     */
    public void drawApple (Graphics g) {
        try {
            mySkin = new Config();
            mySkin.loadPreySkin();
            BufferedImage img = new BufferedImage(
                    mySkin.SKIN.getIconWidth(),
                    mySkin.SKIN.getIconHeight(),
                    BufferedImage.TYPE_INT_RGB);

//            BufferedImage img2 = ImageIO.read(mySkin.SKIN);
            Graphics gg = img.createGraphics();
// paint the Icon to the BufferedImage.
            mySkin.SKIN.paintIcon(null, gg, 0,0);
            gg.dispose();
            g.drawImage(img, appleCoor.x * width, appleCoor.y * height, width, height, null);
        } catch (Exception ex){
            g.setColor(Color.RED);
            g.fillOval(appleCoor.x * width, appleCoor.y * height, width, height);
            ex.printStackTrace();
        }
    }

    public int getxApple() {
        return appleCoor.x;
    }

    public int getyApple() {
        return appleCoor.y;
    }

    public String getSkin() {
        if (mySkin.SKIN.equals(mySkin.APPLE_SKIN)) {
            return "apple";
        } else if (mySkin.SKIN.equals(mySkin.BANANA_SKIN)) {
            return "banana";
        } else if (mySkin.SKIN.equals(mySkin.CHERRY_SKIN)) {
            return "cherry";
        } else if (mySkin.SKIN.equals(mySkin.MOUSE_SKIN)) {
            return "mouse";
        }
        return "Default Skin";
    }
}