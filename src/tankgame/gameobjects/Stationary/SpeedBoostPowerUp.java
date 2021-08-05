package tankgame.gameobjects.Stationary;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.TankRotation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class SpeedBoostPowerUp extends PowerUp {
    Rectangle hitBox;
    int x, y;
    BufferedImage speedImage;
    public boolean captured;

    public SpeedBoostPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            speedImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/speedBoostPowerUp.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, this.speedImage.getWidth(), this.speedImage.getHeight());
        this.captured = false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.speedImage, x, y, null);
    }

    @Override
    public boolean getCaptured() {
        return this.captured;
    }

    @Override
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    @Override
    public void reset() {
        this.x = x;
        this.y = y;
        try {
            speedImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/speedBoostPowerUp.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, this.speedImage.getWidth(), this.speedImage.getHeight());
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
        this.captured = true;
    }
}
