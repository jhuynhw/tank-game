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

import java.awt.*;

public abstract class PowerUp extends GameObject {
    public abstract void drawImage(Graphics g);
    public abstract boolean getCaptured();
    public abstract void setCaptured(boolean captured);
    public abstract void reset();
}
