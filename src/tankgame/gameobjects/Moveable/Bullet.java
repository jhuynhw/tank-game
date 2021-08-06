package tankgame.gameobjects.Moveable;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import tankgame.GameConstants;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.TankRotation;
import tankgame.gameobjects.Stationary.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Bullet extends GameObject {
    int x, y, vx, vy;
    float angle;
    int R = 7;
    BufferedImage bulletImage;
    Rectangle hitBox;
    public boolean hasCollided = false;

    public Bullet(int x, int y, float angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    public void moveForward() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
//        checkBorder();
        this.hitBox.setLocation(x,y);
    }

    public void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    public void update() {
        moveForward();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bulletImage.getWidth() / 2.0, this.bulletImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage, rotation, null);
//        g2d.setColor(Color.RED);
        g2d.drawRect(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void collision(GameObject obj) {
        if (obj instanceof Tank) {
            try {
                this.bulletImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/smallExplode.gif")));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            hasCollided = true;
        }
        if (obj instanceof Wall) {
            try {
                this.bulletImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/smallExplode.gif")));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            hasCollided = true;
        }
    }
}
