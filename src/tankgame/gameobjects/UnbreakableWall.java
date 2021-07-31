package tankgame.gameobjects;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413
Assignment: Tank Game
----------------------------
*/

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {
    int x, y;
    BufferedImage wallImage;

    public UnbreakableWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.wallImage, x, y, null);
    }
}
