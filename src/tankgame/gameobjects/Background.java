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

public class Background extends Wall {
    Rectangle hitbox;
    int x, y;
    BufferedImage backgroundImg;

    public Background(int x, int y) {
        this.x = x;
        this.y = y;
//        try {
//            backgroundImg = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/background.png")));
//        }
//        catch (IOException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
        this.hitbox = hitbox;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.backgroundImg, x, y, null);
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public void collision(GameObject obj) {

    }
}
