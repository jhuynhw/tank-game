package tankgame.gameobjects;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import tankgame.GameConstants;

import java.awt.*;

public class HealthBar extends Hud{
    private Tank t1;
    private Tank t2;

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 6, 20, 2 * t1.getHealth(), 20);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4 + 15,
                20, 2 * t2.getHealth(), 20);
    }

}
