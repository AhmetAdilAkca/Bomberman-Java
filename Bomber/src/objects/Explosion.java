package objects;

import main.CollusionChecker;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Explosion extends SuperObject {
    GamePanel gp;
    private BufferedImage expl_up , expl_down , expl_right , expl_left,expl_mid;
    
    // Animasyon için tüm aşamaların resimleri
    private BufferedImage[] expl_up_frames = new BufferedImage[4];
    private BufferedImage[] expl_down_frames = new BufferedImage[4];
    private BufferedImage[] expl_right_frames = new BufferedImage[4];
    private BufferedImage[] expl_left_frames = new BufferedImage[4];
    private BufferedImage[] expl_mid_frames = new BufferedImage[4];
    
    CollusionChecker cc;
    private long startTime;
    
    // Animasyon kontrolü
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private int frameDelay = 50; // Her frame arası 50ms (daha hızlı!)
    private int[] animationSequence = {0, 1, 2, 3, 2, 1, 0}; // 1-2-3-4-3-2-1 animasyonu
    private int sequenceIndex = 0;


    // Patlama özellikleri
    public int explosionRange = 2; // Varsayılan patlama menzili
    private boolean effectsApplied = false; // Etkiler sadece bir kez uygulanır
    
    public Explosion(GamePanel gp) {
        this.gp = gp;

        cc = new CollusionChecker(gp);
        startTime = System.currentTimeMillis();
        getExplosionImage();
    }
    
    public Explosion(GamePanel gp, int range) {
        this(gp);
        this.explosionRange = range;
    }
    public void getExplosionImage(){
        try {
            // Her yön için 4 aşamalı animasyon frame'lerini yükle
            for(int i = 1; i <= 4; i++){
                expl_up_frames[i-1] = ImageIO.read(getClass().getResourceAsStream("/object/explosion_up_" + i + ".png"));
                expl_down_frames[i-1] = ImageIO.read(getClass().getResourceAsStream("/object/explosion_down_" + i + ".png"));
                expl_right_frames[i-1] = ImageIO.read(getClass().getResourceAsStream("/object/explosion_right_" + i + ".png"));
                expl_left_frames[i-1] = ImageIO.read(getClass().getResourceAsStream("/object/explosion_left_" + i + ".png"));
                expl_mid_frames[i-1] = ImageIO.read(getClass().getResourceAsStream("/object/explosion_mid_" + i + ".png"));
            }
            
            // Varsayılan olarak da tek frame'leri yükle (eski sistemle uyumluluk için)
            expl_up = ImageIO.read(getClass().getResourceAsStream("/object/explosion_up.png"));
            expl_down = ImageIO.read(getClass().getResourceAsStream("/object/explosion_down.png"));
            expl_right = ImageIO.read(getClass().getResourceAsStream("/object/explosion_r.png")); // explosion_r.png kullan
            expl_left = ImageIO.read(getClass().getResourceAsStream("/object/explosion_left.png"));
            expl_mid = ImageIO.read(getClass().getResourceAsStream("/object/explosion_mid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
        // Patlama başladığında etkiler henüz uygulanmadıysa uygula
        if (!effectsApplied) {
            applyExplosionEffects();
            effectsApplied = true;
        }
        
        // Animasyon update'i
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            sequenceIndex++;
            if (sequenceIndex >= animationSequence.length) {
                sequenceIndex = animationSequence.length - 1; // Son frame'de kal
            }
            currentFrame = animationSequence[sequenceIndex];
            lastFrameTime = currentTime;
        }
    }
    
    private void applyExplosionEffects() {
        // Merkez patlaması
        checkTileEffects(worldX, worldY);
        
        // 4 yöne patlama (menzil kadar)
        explodeInDirection(0, -gp.tileSize, explosionRange); // Yukarı
        explodeInDirection(0, gp.tileSize, explosionRange);  // Aşağı
        explodeInDirection(-gp.tileSize, 0, explosionRange); // Sol
        explodeInDirection(gp.tileSize, 0, explosionRange);  // Sağ
    }
    
    private void explodeInDirection(int deltaX, int deltaY, int range) {
        for (int i = 1; i <= range; i++) {
            int explosionX = worldX + (deltaX * i);
            int explosionY = worldY + (deltaY * i);
            
            // Tile koordinatları
            int tileX = explosionX / gp.tileSize;
            int tileY = explosionY / gp.tileSize;
            
            // Harita sınırları kontrolü
            if (tileX < 0 || tileX >= gp.maxWorldCol || tileY < 0 || tileY >= gp.maxWorldRow) {
                break;
            }
            
            // Kırılamaz duvar varsa dur
            if (gp.wallM.mapTileNum[tileY][tileX] == 1) { // Kırılamaz duvar
                break;
            }
            
            // Kırılabilir duvar varsa kır ve dur
            if (gp.wallM.mapTileNum[tileY][tileX] == 2) { // Kırılabilir duvar
                checkTileEffects(explosionX, explosionY);
                break; // Duvar kırıldıktan sonra patlama durar
            }
            
            // Boş alan - patlama devam eder
            checkTileEffects(explosionX, explosionY);
        }
    }
    
    private void checkTileEffects(int explosionX, int explosionY) {
        int tileX = explosionX / gp.tileSize;
        int tileY = explosionY / gp.tileSize;
        
        // Harita sınırları kontrolü
        if (tileX < 0 || tileX >= gp.maxWorldCol || tileY < 0 || tileY >= gp.maxWorldRow) {
            return;
        }
        
        // Kırılabilir duvarları yok et
        if (gp.wallM.mapTileNum[tileY][tileX] == 2) { // Kırılabilir duvar tile numarası
            gp.wallM.damageWall(tileY, tileX); // Anında yok etmek yerine hasar ver
        }
    }
    
    private BufferedImage getCurrentFrame(BufferedImage[] frames) {
        if (frames != null && currentFrame < frames.length) {
            return frames[currentFrame];
        }
        return null;
    }
    
    public void draw(Graphics2D g2d){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Animasyonun bitmesi kontrolü
        if(elapsedTime > 1000){
            return; // Patlama bitti, çizme
        }
        
        // Merkez patlaması - animasyonlu
        BufferedImage currentMidFrame = getCurrentFrame(expl_mid_frames);
        if(currentMidFrame != null){
            g2d.drawImage(currentMidFrame, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        
        // Her yön için menzil kadar çiz
        drawExplosionInDirection(g2d, screenX, screenY, gp.tileSize, 0, explosionRange, "right");  // Sağ
        drawExplosionInDirection(g2d, screenX, screenY, -gp.tileSize, 0, explosionRange, "left");  // Sol
        drawExplosionInDirection(g2d, screenX, screenY, 0, gp.tileSize, explosionRange, "down");   // Aşağı
        drawExplosionInDirection(g2d, screenX, screenY, 0, -gp.tileSize, explosionRange, "up");    // Yukarı
    }
    
    private void drawExplosionInDirection(Graphics2D g2d, int startX, int startY, int deltaX, int deltaY, int range, String direction) {
        for (int i = 1; i <= range; i++) {
            int drawX = startX + (deltaX * i);
            int drawY = startY + (deltaY * i);
            
            // Bu tile'da patlama olup olmayacağını kontrol et
            int tileX = (this.worldX + (deltaX * i)) / gp.tileSize;
            int tileY = (this.worldY + (deltaY * i)) / gp.tileSize;
            
            // Harita sınırları kontrolü
            if (tileX < 0 || tileX >= gp.maxWorldCol || tileY < 0 || tileY >= gp.maxWorldRow) {
                break;
            }
            
            // Kırılamaz duvar varsa dur
            if (gp.wallM.mapTileNum[tileY][tileX] == 1) {
                break;
            }
            
            // Bu tile'a patlama efekti çiz
            BufferedImage frame = null;
            switch(direction) {
                case "right":
                    frame = getCurrentFrame(expl_right_frames);
                    break;
                case "left":
                    frame = getCurrentFrame(expl_left_frames);
                    break;
                case "down":
                    frame = getCurrentFrame(expl_down_frames);
                    break;
                case "up":
                    frame = getCurrentFrame(expl_up_frames);
                    break;
            }
            
            if (frame != null) {
                g2d.drawImage(frame, drawX, drawY, gp.tileSize, gp.tileSize, null);
            }
            
            // Kırılabilir duvar varsa kır ve dur
            if (gp.wallM.mapTileNum[tileY][tileX] == 2) {
                break;
            }
        }
    }
    
    public boolean isFinished() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        return elapsedTime > 1000;
    }

}
