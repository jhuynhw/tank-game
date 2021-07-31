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

public abstract class GameObject {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private BufferedImage img;
    private Rectangle r;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void drawImage(Graphics g);

//    public abstract void update();
//
//    public abstract void collision(Class c);
}