package tankgame.gameobjects;/*
----------------------------
Name: Johnathan Huynh
Professor:
Class: CSC
Assignment: ASMT
----------------------------
*/

import tankgame.GameConstants;
import tankgame.gameobjects.Stationary.BreakableWall;
import tankgame.gameobjects.Stationary.PowerUp;

import java.util.ArrayList;

public class ResetGame {
    public ResetGame(){}

    public static void reset(ArrayList<GameObject> gameObjects) {
        for (int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof BreakableWall) {
                resetWalls((BreakableWall) gameObjects.get(i));
            }
            if(gameObjects.get(i) instanceof PowerUp) {
                resetPowerUps((PowerUp) gameObjects.get(i));
            }
        }
    }

    public static void resetWalls(BreakableWall wall) {
        if(wall.isDestroyedWall()) {
            wall.setDestroyedWall(false);
            wall.reset();
        }
    }

    public static void resetPowerUps(PowerUp powerups) {
        if(powerups.getCaptured()) {
            powerups.setCaptured(false);
            powerups.reset();
        }
    }
}
