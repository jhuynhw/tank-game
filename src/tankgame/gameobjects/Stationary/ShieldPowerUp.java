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

public class ShieldPowerUp extends PowerUp {
    Rectangle hitBox;
    int x, y;
    BufferedImage shieldImage;
    int totalLives;
    public boolean captured;

    public ShieldPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            shieldImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/shield.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, this.shieldImage.getWidth(), this.shieldImage.getHeight());
        this.captured = false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.shieldImage, x, y, null);
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
            shieldImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/shield.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, this.shieldImage.getWidth(), this.shieldImage.getHeight());
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
            shieldImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/background.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.captured = true;
    }
}
