package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyHandler ){
        super(gp);
        this.keyH = keyHandler;


        this.screenX = gp.screenWidth/2 - (gp.tileSize/2);
        this.screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 12 ;
        solidArea.y = 20 ;
        solidArea.width = 24;
        solidArea.height = 20 ;


        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize;
        worldY = gp.tileSize;
        speed = 4;
        direction ="down";
    }
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanarka1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanarka2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanarka3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanon1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanon2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/bombermanon3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansag1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansag2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansag3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansol1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansol2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/bombermansol3.png"));
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("BOŞ");
        }
    }
    public void update(){
        if(keyH.upPressed ) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // IF COLLUSION IS FALSE ,PLAYER CAN MOVE
        if(!collisionOn) {

            if(keyH.upPressed && direction.equals("up")) {
                worldY -= speed;
                spriteCounter++;
            }
            else if(keyH.downPressed && direction.equals("down")) {
                worldY += speed;
                spriteCounter++;
            }
            else if(keyH.leftPressed && direction.equals("left")) {
                worldX -= speed;
                spriteCounter++;
            }
            else if(keyH.rightPressed && direction.equals("right")) {
                worldX += speed;
                spriteCounter++;
            }
        }

        if(spriteCounter >= 10){
            if(spriteNum==1){
                spriteNum = 2;
            }
            else if(spriteNum==2){
                spriteNum = 3;
            }
            else if(spriteNum==3){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.WHITE);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                else if(spriteNum == 2){
                    image = up2;
                }
                else if(spriteNum == 3){
                    image = up3;
                }
            break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                else if(spriteNum == 2){
                    image = down2;
                }
                else if(spriteNum == 3){
                    image = down3;
                }
            break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                else if(spriteNum == 2){
                    image = left2;
                }
                else if(spriteNum == 3){
                    image = left3;
                }
            break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                else if(spriteNum == 2){
                    image = right2;
                }
                else if(spriteNum == 3){
                    image = right3;
                }
            break;
        }

        g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
