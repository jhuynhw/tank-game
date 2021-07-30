package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class BreakWall extends Wall {
    int x, y;
    BufferedImage wallImage;

    public BreakWall(int x, int y, BufferedImage wallImage) {
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
