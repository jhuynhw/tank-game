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

public abstract class GameObject {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void drawImage(Graphics g);

    public abstract Rectangle getHitBox();

    public abstract void collision(GameObject obj);

}