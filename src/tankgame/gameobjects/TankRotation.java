/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame.gameobjects;


import tankgame.GameConstants;
import tankgame.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TankRotation extends JPanel implements Runnable {

    public static final int WORLD_WIDTH = 2000;
    public static final int WORLD_HEIGHT = 2000;
    public static final int SCREEN_WIDTH = 1305; // 43 columns
    public static final int SCREEN_HEIGHT = 1000; // 32 rows
    private BufferedImage world;
    public static BufferedImage bulletImage;
//    public static BufferedImage bulletImage;
//    private Graphics2D buffer;
//    private JFrame jFrame;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    static long tick = 0;

    // WALLS
    ArrayList<Wall> walls;

    public TankRotation(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
               this.t1.update(); // update tank1
               this.t2.update(); // update tank2
               this.repaint();   // redraw game
               if (this.t1.getHitBox().intersects(this.t2.getHitBox())) {
//                   if(Tank.t1.isDownPressed()){
//                       Tank.t1.setX(Tank.t1,getX() - Tank.t1.getVx());
//                       Tank.t1.setY(Tank.t1,getY() - Tank.t1.getVx());
//                   }
//                   if(Tank.t2.isDownPressed()){
//                       Tank.t2.setX(Tank.t2,getX() - Tank.t2.getVx());
//                       Tank.t2.setY(Tank.t2,getY() - Tank.t2.getVx());
//                   }
                   System.out.println("TANKS HAVE HIT");
               }
               this.tick++;
               Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 2000){
                    this.lf.setFrame("end");
                    return;
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(900);
        this.t2.setY(300);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        // WALLS
        BufferedImage background = null;
        BufferedImage unbreakWall = null;
        BufferedImage breakWall = null;
        walls = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/tank1.png")));
            t2img = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/tank1.png")));
            TankRotation.bulletImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/bullet.png")));
            // WALLS
//            background = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/background.jpg")));
            unbreakWall = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/unbreak.gif")));
            breakWall = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/break.gif")));
            InputStreamReader isr = new InputStreamReader(TankRotation.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null) {
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\\s"); // uses tab as delimiter for reading map
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++) { // reads map
                row = mapReader.readLine();
                mapInfo = row.split("\\s");
                for(int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "0":
                            this.walls.add(new Background(curCol*30, curRow*30, background));
                            break;
                        case "2":
                            // if sees "2", make breakable wall
                            this.walls.add(new BreakableWall(curCol*30, curRow*30, breakWall));
                            break;
                        case "3":
                        case "9":
                            this.walls.add(new UnbreakableWall(curCol*30, curRow*30, unbreakWall));
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        // player 2 copied from vid CSC 413 Term Project Starter Code Demo 38:45
        t1 = new Tank(300, 300, 0, 0, 0, t1img);
        t2 = new Tank(500, 500, 0, 0, 0, t2img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
//        super.paint(g2);
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        // WALLS
        this.walls.forEach(wall -> wall.drawImage(buffer));

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
//        BufferedImage leftHalf = world.getSubimage(0, 0, TankRotation.SCREEN_WIDTH/2, TankRotation.SCREEN_HEIGHT);
//        BufferedImage rightHalf = world.getSubimage(TankRotation.SCREEN_WIDTH/2, 0, TankRotation.SCREEN_WIDTH/2, TankRotation.SCREEN_HEIGHT);
//        BufferedImage mm = world.getSubimage(0,0,TankRotation.WORLD_WIDTH, TankRotation.WORLD_HEIGHT);
//        g2.drawImage(leftHalf,0,0,null);
//        g2.drawImage(rightHalf,TankRotation.SCREEN_WIDTH/2,0,null);
//        g2.scale(.10,10);
//        g2.drawImage(mm, 200, 200, null);
        g2.drawImage(world,0,0,null);
    }

}
