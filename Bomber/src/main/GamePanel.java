package main;

import monsters.RedBaloon;
import objects.Bomb;
import objects.Door;
import objects.Explosion;
import objects.Skills;
import tiles.WallManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    public static final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;  //768 pixels
    public final int screenHeight = maxScreenRow * tileSize; //576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 31;
    public final int maxWorldRow = 13;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    public WallManager wallM = new WallManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollusionChecker cChecker = new CollusionChecker(this);
    public ObjectSetter oSetter = new ObjectSetter(this,wallM);
    public TimerHandler timerHandler = new TimerHandler();
    public UI ui = new UI(this, timerHandler);
    public Door door = new Door(this);
    public Skills skill = new Skills(this);
    public StartMenu startMenu = new StartMenu(this);



    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public RedBaloon[] redBaloons = new RedBaloon[3];
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public ArrayList<Explosion> explosions = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int startMenuState = 3;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        setLayout(new BorderLayout());
        add(startMenu, BorderLayout.CENTER);
        gameState = startMenuState;


    }
    public void removeMenu(){
        setupGame();
        remove(startMenu);
        gameState = playState;
    }
    //OBJECT CREATOR
    public void setupGame() {

            oSetter.setMonster();
            oSetter.setObject();
//        oSetter.setDoor();
//        oSetter.setSkill();
            timerHandler.startTimer();

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
        for(int i = 0  ;i < redBaloons.length ;i++) {
            Thread t = new Thread(redBaloons[i]);
            t.start();
        }
    }

    @Override //game loop
    public void run() {
        while (gameThread != null) {

            //UPDATE
            update();
            //DRAW
            repaint();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameState == playState || gameState == pauseState) {
            Graphics2D g2d = (Graphics2D) g;

            // TILES
            wallM.draw(g2d);

            //OTHER STUFF
//        door.draw(g2d);
//        skill.draw(g2d);

            //MONSTERS
            for (int i = 0; i < redBaloons.length; i++) {
                redBaloons[i].draw(g2d);
            }
            // PLAYER

            player.draw(g2d);

            //BOMBS
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).draw(g2d);
            }
            //EXPLOSIONS
            for (Explosion explosion : explosions) {
                explosion.draw(g2d);
            }

            //UI
            ui.draw(g2d);
            g2d.dispose();
        }
    }

    public void update() {

        if(gameState == playState) {
            player.update();

            for(int i = 0; i < redBaloons.length; i++) {
                redBaloons[i].update();
            }
            if(keyH.zPressed){
                oSetter.setBomb();
            }
            for(int i = 0 ; i< bombs.size() ;i++) {
                bombs.get(i).update();
            }
            
            // Explosion update'leri
            for(int i = 0; i < explosions.size(); i++) {
                explosions.get(i).update();
                
                // Patlama süresi dolmuşsa listeden çıkar
                if(explosions.get(i).isFinished()) {
                    explosions.remove(i);
                    i--; // Index'i ayarla
                }
            }
            
            // Wall damage update'i
            wallM.update();

        }
        if(gameState == pauseState) {
        //null
        }
    }

}

