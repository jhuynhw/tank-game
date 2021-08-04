package tankgame.gameobjects;
/*
----------------------------
Name: Johnathan Huynh
Professor: Anthony Souza
Class: CSC 413-01
Assignment: Tank Game
----------------------------
*/

import tankgame.gameobjects.Moveable.Tank;
import tankgame.gameobjects.Stationary.PowerUp;
import tankgame.gameobjects.Stationary.Wall;

import java.util.ArrayList;

public class Collisions {

    GameObject obj1, obj2;

    public Collisions(GameObject obj1, GameObject obj2){
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    // More optimized, less code/if statements
    public static void collisionsHandler(ArrayList<GameObject> gameObjects) {
        for(GameObject tank : gameObjects) {
            if(tank instanceof Tank) {
                for(int i = 0; i < gameObjects.size(); i++) {
                    collidesTank(tank, gameObjects.get(i));
                    collidesPowerUp(tank, gameObjects.get(i));
                    collidesBullet(tank, gameObjects.get(i), gameObjects);
                }
        }

//        if (obj1 instanceof Tank && obj2 instanceof Tank){
//            if(obj1.getHitBox().intersects(obj2.getHitBox())) {
//                System.out.println("tank to tank collision");
//                obj1.collision(obj2);
//                obj2.collision(obj1);
//            }
//        }
//        if (obj1 instanceof Tank && obj2 instanceof Wall){
//            if(obj1.getHitBox().intersects(obj2.getHitBox())) {
//                System.out.println("tank to wall collision");
//                obj1.collision(obj2);
//            }
//        }
//        if (obj1 instanceof Bullet && obj2 instanceof Tank){
//            if(obj1.getHitBox().intersects(obj2.getHitBox())) {
//                System.out.println("bullet to tank collision");
//                obj1.collision(obj2);
//                obj2.collision(obj1);
//                ((Tank) obj2).getAmmo().remove(obj2);
//            }
        }
//        if (obj1 instanceof Bullet && obj2 instanceof Wall){
//            if(obj1.getHitBox().intersects(obj2.getHitBox())) {
//                System.out.println("tank to wall collision");
//                obj1.collision(obj2);
//            }
//        }
    }

    public static void collidesTank(GameObject tank, GameObject obj) {
        if(tank.getHitBox().intersects(obj.getHitBox()) && tank != obj) {
            tank.collision(obj);
//            System.out.println("tank collided with object");
        }
    }

    public static void collidesPowerUp(GameObject tank, GameObject obj) {
        if (obj instanceof PowerUp) {
            if (tank.getHitBox().intersects(obj.getHitBox()) && tank != obj) {
                obj.collision(tank);
            }
        }
    }

    public static void collidesBullet(GameObject tank, GameObject obj, ArrayList gameObjects) {
        for(int i = 0; i <  ((Tank) tank).getAmmo().size(); i++) {
            if (((Tank) tank).getAmmo().get(i).getHitBox().intersects(obj.getHitBox()) && tank != obj) {
                ((Tank) tank).getAmmo().get(i).collision(obj);
                if(obj instanceof Wall || obj instanceof Tank){
                    obj.collision(((Tank) tank).getAmmo().get(i));
                }
                if(((Tank) tank).getAmmo().get(i).hasCollided){
                    ((Tank) tank).getAmmo().remove(((Tank) tank).getAmmo().get(i));
                }
            }
        }
    }
}
