package main;

import monsters.RedBaloon;
import objects.Bomb;
import objects.Door;
import objects.Skills;
import tiles.WallManager;

import java.util.Random;

public class ObjectSetter {
    GamePanel gp;
    Random rand;
    WallManager wm;

    public ObjectSetter(GamePanel gp ,WallManager wm) {
        this.gp = gp;
        rand = new Random();
        this.wm = wm;
    }
    public void setObject() {


    }
    public void setMonster(){
        gp.redBaloons[0] = new RedBaloon(gp);
        gp.redBaloons[1] = new RedBaloon(gp);
        gp.redBaloons[2] = new RedBaloon(gp);
        int counter = 0 ;
        while(counter < gp.redBaloons.length){
            int x = rand.nextInt(gp.maxWorldRow);
            int y = rand.nextInt(gp.maxWorldCol);
            gp.redBaloons[counter].collisionOn = true;
            if(wm.mapTileNum[x][y] == 0){
                gp.redBaloons[counter].worldX = y* gp.tileSize;
                gp.redBaloons[counter].worldY = x* gp.tileSize;
                counter++;
            }
        }

    }
    public void setBomb(){
        Bomb bomb = new Bomb(gp);
        bomb.worldX = (gp.player.worldX/ gp.tileSize)*gp.tileSize;
        bomb.worldY = (gp.player.worldY/ gp.tileSize)*gp.tileSize;
        bomb.collision = true;
        gp.bombs.add(bomb);
    }
//    public void setDoor(){
//        gp.door.worldX = wm.doorRow*gp.tileSize;
//        gp.door.worldY = wm.doorCol*gp.tileSize;
//    }
//    public void setSkill(){
//        gp.skill.worldX = wm.skillRow*gp.tileSize;
//        gp.skill.worldY = wm.skillCol;
//    }
}
