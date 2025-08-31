package objects;

import main.GamePanel;
import tiles.WallManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Door extends SuperObject{
    GamePanel gp;
    WallManager wm ;
    private BufferedImage door1;

    public Door(GamePanel gp){
        this.gp = gp;
        getDoorImage();

    }
    public void getDoorImage(){
        try{
            door1 = ImageIO.read(getClass().getResourceAsStream("/wall/door.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d){

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        image = door1;

        g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
