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

public class TankRotation extends JPanel implements Runnable {

    private BufferedImage world;
    public static BufferedImage bulletImage;
    public static BufferedImage heartImage;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    static long tick = 0;

    ArrayList<Wall> walls;
    ArrayList<PowerUp> powerUps;
    ArrayList<GameObject> gameObjects;

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

               Collisions.collisionsHandler(this.gameObjects);
//               Collisions tankCollision1 = new Collisions(t1, t2);
//               Collisions tankCollision2 = new Collisions(t2, t1);
//               for(Wall w : walls) {
//                   Collisions wallCollision1 = new Collisions(t1, w);
//                   Collisions wallCollision2 = new Collisions(t2, w);
//                   wallCollision1.collisionsHandler();
//                   wallCollision2.collisionsHandler();
//               }
//               for(Bullet b : t1.getAmmo()) {
//                   Collisions bulletCollision = new Collisions(b, t2);
//                   bulletCollision.collisionsHandler();
//               }
//               for(Bullet b : t2.getAmmo()) {
//                   Collisions bulletCollision = new Collisions(b, t1);
//                   bulletCollision.collisionsHandler();
//               }
//               tankCollision1.collisionsHandler();
//               tankCollision2.collisionsHandler();
               this.repaint();   // redraw game
               this.tick++;
               Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(t1.getLives() <= 0 || t2.getLives() <= 0){
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
        this.t2.setY(600);
        this.t1.setLives(3);
        this.t2.setLives(3);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        walls = new ArrayList<>();
        powerUps = new ArrayList<>();
        gameObjects = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/tank1.png")));
            t2img = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/tank1.png")));
            heartImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/heart.png")));
            TankRotation.bulletImage = read(Objects.requireNonNull(TankRotation.class.getClassLoader().getResource("spritemap/bullet.png")));
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
//                        case "0":
//                            Background background = new Background(curCol*30, curRow*30);
//                            gameObjects.add(background);
//                            break;
                        case "2":
                            // if sees "2", make breakable wall
                            BreakableWall breakableWall = new BreakableWall(curCol*30, curRow*30);
                            gameObjects.add(breakableWall);
                            break;
                        case "4":
                            ShieldPowerUp healthPowerUp = new ShieldPowerUp(curCol*30, curRow*30);
                            gameObjects.add(healthPowerUp);
                            break;
                        case "5":
                            SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(curCol*30, curRow*30);
                            gameObjects.add(speedBoostPowerUp);
                            break;
                        case "6":
                            SpeedShotPowerUp speedShotPowerUp = new SpeedShotPowerUp(curCol*30, curRow*30);
                            gameObjects.add(speedShotPowerUp);
                            break;
                        case "3":
                        case "9":
                            UnbreakableWall unbreakableWall = new UnbreakableWall(curCol*30, curRow*30);
                            gameObjects.add(unbreakableWall);
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        // player 2 copied from vid CSC 413 Term Project Starter Code Demo 38:45
        t1 = new Tank(300, 300, 0, 0, 45, t1img);
        this.gameObjects.add(t1);
        t2 = new Tank(900, 600, 0, 0, 225, t2img);
        this.gameObjects.add(t2);
        TankControl tc1 = new TankControl(t1,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2,
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
//        super.paint(g2);
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.DARK_GRAY);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        // WALLS
//        this.walls.forEach(wall -> wall.drawImage(buffer));
//        this.powerUps.forEach(wall -> wall.drawImage(buffer));
//        this.t1.drawImage(buffer);
//        this.t2.drawImage(buffer);
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));
        BufferedImage leftHalf = world.getSubimage(
                (t1.getX() - GameConstants.GAME_SCREEN_WIDTH / 4 > 0) ?
                        Math.min(t1.getX() - GameConstants.GAME_SCREEN_WIDTH / 4, GameConstants.GAME_SCREEN_WIDTH - (GameConstants.GAME_SCREEN_WIDTH / 2)) :
                        Math.max(t1.getX() - GameConstants.GAME_SCREEN_WIDTH / 4, 0),
                (t1.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2 > 0) ?
                        Math.min(t1.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) :
                        Math.max(t1.getY() - (GameConstants.GAME_SCREEN_HEIGHT / 2), 0),
                GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);

        BufferedImage rightHalf = world.getSubimage(
                (t2.getX() - GameConstants.GAME_SCREEN_WIDTH / 4 > 0) ?
                        Math.min(t2.getX() - GameConstants.GAME_SCREEN_WIDTH / 4, GameConstants.GAME_SCREEN_WIDTH - (GameConstants.GAME_SCREEN_WIDTH / 2)) :
                        Math.max(t2.getX() - GameConstants.GAME_SCREEN_WIDTH / 4, 0),
                (t2.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2 > 0) ?
                        Math.min(t2.getY() - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) :
                        Math.max(t2.getY() - (GameConstants.GAME_SCREEN_HEIGHT / 2), 0),
                GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0,0,GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 5,0,null);
        g2.drawImage(mm, GameConstants.GAME_SCREEN_WIDTH / 2 - GameConstants.WORLD_WIDTH / 15 + 12,
                GameConstants.GAME_SCREEN_HEIGHT - GameConstants.WORLD_HEIGHT / 8 + 19,
                GameConstants.WORLD_WIDTH / 8,
                GameConstants.WORLD_HEIGHT / 10, null);
        //        g2.drawImage(world,0,0,null);

        int location, position;
        for(int i = 1; i <= t1.getLives(); i++){
            location = (heartImage.getWidth() + 30) * i;
            position = location/2 + GameConstants.GAME_SCREEN_WIDTH / 21;
            g2.drawImage(heartImage, position, 10, null);
        }

        for(int i = 1; i <= t2.getLives(); i++){
            location = (heartImage.getWidth() + 40) * i;
            position = location/2 + GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2 + 170;
            g2.drawImage(heartImage, position, 10, null);
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 6, 20, 2 * t1.getHealth(), 20);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4 + 15,
                20, 2 * t2.getHealth(), 20);
    }

}
