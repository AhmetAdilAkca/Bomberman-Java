package monsters;

import main.Entity;
import main.GamePanel;
import tiles.WallManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class RedBaloon extends Entity implements Runnable {

    WallManager wm;
    Thread baloonThread;
    GamePanel gp;
    public int screenX, screenY;

    public RedBaloon(GamePanel gamePanel) {
        super(gamePanel);
        wm = new WallManager(gamePanel);
        gp = gamePanel;
        direction = "down";
        speed = 1;

        getMonsterImage();
    }


    public void getMonsterImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright3.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonright3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/monster/redbaloonleft3.png"));
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("BOŞ");
        }
    }
    public void update(){

        setAction();

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // IF COLLUSION IS FALSE ,PLAYER CAN MOVE
        if(!collisionOn) {

            if(direction.equals("up")) {
                worldY -= speed;
                spriteCounter++;
            }
            else if( direction.equals("down")) {
                worldY += speed;
                spriteCounter++;
            }
            else if(direction.equals("left")) {
                worldX -= speed;
                spriteCounter++;
            }
            else if(direction.equals("right")) {
                worldX += speed;
                spriteCounter++;
            }
            screenX = worldX - gp.player.worldX + gp.player.screenX;
            screenY = worldY - gp.player.worldY + gp.player.screenY;
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

    @Override
    public void run() {
        while (baloonThread != null) {

            //UPDATE
            gp.update();
            //DRAW
            gp.repaint();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void draw(Graphics2D g2d){
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