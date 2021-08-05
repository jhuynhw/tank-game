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
import tankgame.gameobjects.Moveable.Bullet;
import tankgame.gameobjects.TankRotation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class BreakableWall extends Wall {
    Rectangle hitbox;
    int x, y;
    BufferedImage wallImage;
    int health;
    public boolean destroyedWall;

    public BreakableWall(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            wallImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/breakableWall.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitbox = new Rectangle(this.x, this.y, this.wallImage.getWidth(), this.wallImage.getHeight());
        this.health = 100;
        this.destroyedWall = false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.wallImage, x, y, null);
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public void collision(GameObject obj) {
        if(obj instanceof Bullet) {
            this.health -= 50;
        }
        if(this.health <= 0) {
            try {
                this.wallImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/brokenWall.png")));
                setHitboxNone();
                this.destroyedWall = true;
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    public int getHealth() {
        return health;
    }

    public void setHitboxNone() {
        this.hitbox.x = 0;
        this.hitbox.y = 0;
    }

    public boolean isDestroyedWall() {
        return destroyedWall;
    }

    public void setDestroyedWall(boolean destroyedWall) {
        this.destroyedWall = destroyedWall;
    }

    public void reset() {
        this.x = x;
        this.y = y;
        try {
            wallImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/breakableWall.png")));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        this.hitbox = new Rectangle(this.x, this.y, this.wallImage.getWidth(), this.wallImage.getHeight());
        this.health = 100;
    }
}
