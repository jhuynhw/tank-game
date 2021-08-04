package tankgame.gameobjects.Moveable;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import tankgame.gameobjects.*;
import tankgame.gameobjects.Stationary.ShieldPowerUp;
import tankgame.gameobjects.Stationary.SpeedBoostPowerUp;
import tankgame.gameobjects.Stationary.SpeedShotPowerUp;
import tankgame.gameobjects.Stationary.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends GameObject {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private int health;
    private int lives;
    private Point point;
    private int bulletVelocity;
    private int spawnx;
    private int spawny;

    private int R = 2;
    private final float ROTATIONSPEED = 2.0f;
    private Rectangle hitBox;
    private ArrayList<Bullet> ammo;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    public Tank(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        this.spawnx = x;
        this.spawny = y;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.ammo = new ArrayList<>();
        this.health = 100;
        this.lives = 3;
        this.point = new Point(this.x, this.y);
        this.bulletVelocity = 20;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void collision(GameObject obj) {
        if (obj instanceof Tank){
            if (!obj.getHitBox().contains(point)){
                this.x = point.x;
                this.y = point.y;
            }
        }
        if(obj instanceof Bullet) {
            this.health -= 25;
        }
        if (obj instanceof Wall){
            if (!obj.getHitBox().contains(point)){
                this.x = point.x;
                this.y = point.y;
            }
        }
        if (obj instanceof ShieldPowerUp) {
            this.health = 150;
        }
        if (obj instanceof SpeedBoostPowerUp) {
            this.R = 3;
        }
        if (obj instanceof SpeedShotPowerUp) {
            bulletVelocity = 10;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this. y = y;
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public ArrayList<Bullet> getAmmo() {
        return this.ammo;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public boolean isRightPressed() {
        return RightPressed;
    }

    public boolean isLeftPressed() {
        return LeftPressed;
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    public void healthPowerUp(int value) {
        if(health + value >= 100){
            health = 100;
        }
        else {
            health += value;
        }
    }

    public void addLife() {
        this.lives += 1;
    }

    public void removeLife(){
        if(lives == 0){
            health = 0;
        }
        else {
            lives -= 1;
            healthPowerUp(100);
        }
    }

    public void update() {
        this.point.move(this.x, this.y);
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TankRotation.tick % bulletVelocity == 0) {
            Bullet b = new Bullet(x + 12,y + 8,angle,TankRotation.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
        this.hitBox.setLocation(x,y);

        if(this.health <= 0) {
            this.lives -= 1;
            this.health = 100;
            this.x = spawnx;
            this.y = spawny;
        }
        if(this. lives == 0) {
            this.health = 0;
            System.out.println("Winner!");
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
//        checkBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
//        checkBorder();
        this.hitBox.setLocation(x,y);
    }

//    private void checkBorder() {
//        if (x < 30) {
//            x = 30;
//        }
//        if (x >= GameConstants.WORLD_WIDTH - 100) {
//            x = GameConstants.WORLD_WIDTH - 100;
//        }
//        if (y < 35) {
//            y = 35;
//        }
//        if (y >= GameConstants.WORLD_HEIGHT - 115) {
//            y = GameConstants.WORLD_HEIGHT - 115;
//        }
//    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g; // use for walls class
        g2d.drawImage(this.img, rotation, null);
        this.ammo.forEach(bullet -> bullet.drawImage(g));
//        g2d.setColor(Color.RED);
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight()); // draws rectangle to see hitbox
        // copied from CSC 413 Term Project Starter Code Demo video 30:30
        // rotates screen around rectangle
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
    }



}
