package objects;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends SuperObject{
    GamePanel gp;
    private BufferedImage bomb1 , bomb2 , bomb3;
    long placedTime;
    long currentTime;
    boolean exploded = false;

    public Bomb(GamePanel gp) {
        this.gp = gp;
        this.placedTime = System.currentTimeMillis();
        getBombImage();
    }
    public void getBombImage(){
        try {
            bomb1 = ImageIO.read(getClass().getResourceAsStream("/object/bomb1.png"));
            bomb2 = ImageIO.read(getClass().getResourceAsStream("/object/bomb2.png"));
            bomb3 = ImageIO.read(getClass().getResourceAsStream("/object/bomb3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        currentTime = System.currentTimeMillis();
        if (currentTime - placedTime >= 3000) { // 3 saniye geçti mi kontrol et
            exploded = true;
            gp.bombs.remove(this);
        }
        if(isExploded()){
            Explosion explosion = new Explosion(gp);
            explosion.worldX = this.worldX;
            explosion.worldY = this.worldY;
            gp.explosions.add(explosion);
        }
    }
    public boolean isExploded() {
        return exploded;
    }
    public void draw(Graphics2D g2d){

        BufferedImage image = null;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if(currentTime-placedTime <=1000){
                image = bomb1;
            } else if ( currentTime -placedTime >= 1000 && currentTime - placedTime <= 2000) {
                image = bomb2;
            } else if (currentTime - placedTime >=2000 && currentTime - placedTime <= 3000) {
                image = bomb3;
            }
            else if(currentTime-placedTime >3000){
                image = null;
            }

        g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
