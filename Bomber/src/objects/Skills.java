package objects;

import main.GamePanel;
import tiles.WallManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Skills extends SuperObject{
    GamePanel gp;
    private BufferedImage skill1, skill2, skill3, skill4, skill5, skill6;
    public Skills(GamePanel gp) {
        this.gp = gp;
        getSkillImage();
    }
    public void getSkillImage() {
        try{
            skill1 = ImageIO.read(getClass().getResourceAsStream("/skills/skill1.png"));
            skill2 = ImageIO.read(getClass().getResourceAsStream("/skills/skill2.png"));
            skill3 = ImageIO.read(getClass().getResourceAsStream("/skills/skill3.png"));
            skill4 = ImageIO.read(getClass().getResourceAsStream("/skills/skill4.png"));
            skill5 = ImageIO.read(getClass().getResourceAsStream("/skills/skill5.png"));
            skill6 = ImageIO.read(getClass().getResourceAsStream("/skills/skill6.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        image = skill1;

        g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
