package tiles;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class WallManager {
    GamePanel gp;
    public Wall[] walls;
    public int[][] mapTileNum;
    public int[][] wallDamage; // Duvar hasar seviyeleri (0-6)
    public long[][] damageStartTime; // Hasar animasyonu başlama zamanı
    public int doorRow =0 ;
    public int doorCol = 0 ;
    public int skillCol = 0 ;
    public int skillRow = 0 ;


    public WallManager(GamePanel gp) {

        this.gp= gp;
        walls = new Wall[15]; // Daha fazla wall tipi için artırdık
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        wallDamage = new int[gp.maxWorldRow][gp.maxWorldCol];
        damageStartTime = new long[gp.maxWorldRow][gp.maxWorldCol];
        getWallImage();
        loadMap();
    }
    public void getWallImage(){

            try{
                walls[0] = new Wall();
                walls[0].image = ImageIO.read(getClass().getResourceAsStream("/wall/cim.png"));
                if(walls[0].image == null) {
                    System.err.println("Cim.png bulunamadı!");
                }
                walls[0].collision = false;

                walls[1] = new Wall();
                walls[1].image = ImageIO.read(getClass().getResourceAsStream("/wall/unbreakablewall.png"));
                if(walls[1].image == null) {
                    System.err.println("Unbreakablewall.png bulunamadı!");
                }
                walls[1].collision = true;

                walls[2] = new Wall();
                walls[2].image = ImageIO.read(getClass().getResourceAsStream("/wall/breakablewall.png"));
                if(walls[2].image == null) {
                    System.err.println("Breakablewall.png bulunamadı!");
                }
                walls[2].collision = true;

                walls[3] = new Wall();
                walls[3].image = ImageIO.read(getClass().getResourceAsStream("/wall/doorwall.png"));
                if(walls[3].image == null) {
                    System.err.println("Doorwall.png bulunamadı!");
                }
                walls[3].collision = true;

                walls[4] = new Wall();
                walls[4].image = ImageIO.read(getClass().getResourceAsStream("/wall/skillwall.png"));
                if(walls[4].image == null) {
                    System.err.println("Skillwall.png bulunamadı!");
                }
                walls[4].collision = true;
                
                // Duvar hasar sprite'ları
                for(int i = 1; i <= 6; i++) {
                    walls[4 + i] = new Wall();
                    walls[4 + i].image = ImageIO.read(getClass().getResourceAsStream("/wall/wall_damage_" + i + ".png"));
                    if(walls[4 + i].image == null) {
                        System.err.println("wall_damage_" + i + ".png bulunamadı!");
                    } else {
                        System.out.println("wall_damage_" + i + ".png başarıyla yüklendi!");
                    }
                    walls[4 + i].collision = true;
                }
                                    }
            catch (IOException e){
                e.printStackTrace();
            }

    }
    public void loadMap(){
        try{
            String str = "src/tiles/map.txt";
            FileReader file = new FileReader(str);
            BufferedReader read = new BufferedReader(file);

            int row = 0;
            String line;
            while((line = read.readLine()) != null){
                String[] number = line.split(" ");

                for(int j = 0; j < number.length; j++){
                    mapTileNum[row][j] = Integer.parseInt(number[j]);
                }
                row++;
            }
            random();
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        Random rand = new Random();

        int maxNum = 10+rand.nextInt(11);


        for(int i = 0 ; i< mapTileNum.length ; i++){
            for(int j = 0 ; j< mapTileNum[i].length ; j++){

                int worldX = worldCol *gp.tileSize;
                int worldY = worldRow *gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Duvar hasarı varsa hasar sprite'ını göster
                if (wallDamage[i][j] > 0) {
                    int spriteIndex = 4 + wallDamage[i][j];
                    if (walls[spriteIndex] != null && walls[spriteIndex].image != null) {
                        g2.drawImage(walls[spriteIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    } else {
                        // Eğer hasar sprite'ı yüklenmemişse normal duvar göster
                        g2.drawImage(walls[mapTileNum[i][j]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                } else {
                    // Normal duvar sprite'ını göster
                    if (walls[mapTileNum[i][j]] != null && walls[mapTileNum[i][j]].image != null) {
                        g2.drawImage(walls[mapTileNum[i][j]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                }


                worldCol++;
            }
            worldCol = 0;
            worldRow++;
        }

    }
    public void random() {
        Random rand = new Random();
        int wallNumber = rand.nextInt(11) + 50;
        int row = 0;
        int col = 0;
        int a = 0;
        while (a < wallNumber) {
            int x = rand.nextInt(gp.maxWorldRow);
            int y = rand.nextInt(gp.maxWorldCol);
            if(mapTileNum[x][y] == 0 && !(x== 1 && y== 1)  && !(x== 2 && y== 1)  && !(x== 1 && y== 2)){
                a++;
                mapTileNum[x][y] = 2;
                if(doorCol == 0 && doorRow == 0){
                    mapTileNum[x][y] = 3;
                    doorRow = x;
                    doorCol = y;
                }
                else if(skillCol == 0 && skillRow == 0){
                    mapTileNum[x][y] = 4;
                    skillRow = x;
                    skillCol = y;
                }
            }
        }
    }
    
    public void damageWall(int tileY, int tileX) {
        // Sadece kırılabilir duvarları hasar ver
        if (mapTileNum[tileY][tileX] == 2 && wallDamage[tileY][tileX] == 0) {
            wallDamage[tileY][tileX] = 1;
            damageStartTime[tileY][tileX] = System.currentTimeMillis();
        }
    }
    
    public void update() {
        long currentTime = System.currentTimeMillis();
        
        for (int i = 0; i < gp.maxWorldRow; i++) {
            for (int j = 0; j < gp.maxWorldCol; j++) {
                if (wallDamage[i][j] > 0) {
                    long elapsedTime = currentTime - damageStartTime[i][j];
                    
                    // Her 80ms'de bir sonraki hasar seviyesine geç (daha hızlı animasyon)
                    int damageLevel = (int)(elapsedTime / 80) + 1;
                    
                    if (damageLevel <= 6) {
                        wallDamage[i][j] = damageLevel;
                    } else {
                        // Hasar animasyonu tamamlandı, duvarı yok et
                        mapTileNum[i][j] = 0;
                        wallDamage[i][j] = 0;
                    }
                }
            }
        }
    }

}