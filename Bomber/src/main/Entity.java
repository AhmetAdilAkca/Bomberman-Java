package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(6, 6, 35, 35);
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
        Random rand = new Random();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        actionLockCounter++;

        if (collisionOn) {

            int newDirection = rand.nextInt(4);
            switch (newDirection) {
                case 0:
                    direction = "up";
                    break;
                case 1:
                    direction = "down";
                    break;
                case 2:
                    direction = "left";
                    break;
                case 3:
                    direction = "right";
                    break;
            }
        }

            if (actionLockCounter == 240) {

                int i = rand.nextInt(100);

                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else if (i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }


    }
}
