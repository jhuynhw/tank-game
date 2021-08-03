package tankgame.gameobjects;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class SpeedShotPowerUp extends PowerUp {
    Rectangle hitBox;
    int x, y;
    BufferedImage speedImage;

    public SpeedShotPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            speedImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/speedShotPowerUp.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, this.speedImage.getWidth(), this.speedImage.getHeight());
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.speedImage, x, y, null);
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void collision(GameObject obj) {
        this.hitBox.x = 0;
        this.hitBox.y = 0;
        try {
            speedImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/background.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
