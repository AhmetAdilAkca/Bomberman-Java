package main;

import java.awt.*;

public class UI {
    GamePanel gp ;
    Font font;
    TimerHandler timerH;
    Graphics2D g2d;
    public UI(GamePanel gp ,TimerHandler timerH ) {
        this.gp = gp;
        this.timerH = timerH;
        font = new Font("Tahoma", Font.BOLD, 30);

    }

    public void draw(Graphics2D g2d) {

        this.g2d = g2d;

        g2d.setFont(font);
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0,170 , 50);
        g2d.setColor(Color.black);
        g2d.drawString("Time: " +  timerH.getTimeLeft() ,10,32);
        if(gp.gameState == gp.playState){
            timerH.timer.start();
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            timerH.timer.stop();
        }
    }
    public void drawPauseScreen() {

        g2d.setColor(Color.black);
        g2d.setFont(new Font("Tahoma", Font.BOLD, 60));
        String text = "PAUSED";
        int x =gp.screenWidth/2-gp.screenWidth/6;
        int y = gp.screenHeight/2;
        g2d.drawString(text ,x , y);
    }
}
